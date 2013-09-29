package sk.peterjurkovic.cpr.web.controllers.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import sk.peterjurkovic.cpr.csvimport.CsnCsvImport;
import sk.peterjurkovic.cpr.dto.CsnTerminologyDto;
import sk.peterjurkovic.cpr.dto.CsvImportLogDto;
import sk.peterjurkovic.cpr.dto.FileUploadItemDto;
import sk.peterjurkovic.cpr.dto.PageDto;
import sk.peterjurkovic.cpr.entities.Csn;
import sk.peterjurkovic.cpr.entities.CsnCategory;
import sk.peterjurkovic.cpr.entities.CsnTerminologyLog;
import sk.peterjurkovic.cpr.enums.CsnOrderBy;
import sk.peterjurkovic.cpr.exceptions.ItemNotFoundException;
import sk.peterjurkovic.cpr.parser.NewTerminologyParserImpl;
import sk.peterjurkovic.cpr.parser.NoSectionTerminologyParser;
import sk.peterjurkovic.cpr.parser.TerminologyParser;
import sk.peterjurkovic.cpr.parser.TikaProcessingContext;
import sk.peterjurkovic.cpr.parser.WordDocumentParser;
import sk.peterjurkovic.cpr.services.CsnCategoryService;
import sk.peterjurkovic.cpr.services.CsnService;
import sk.peterjurkovic.cpr.services.CsnTerminologyLogService;
import sk.peterjurkovic.cpr.services.CsnTerminologyService;
import sk.peterjurkovic.cpr.services.FileService;
import sk.peterjurkovic.cpr.utils.RequestUtils;
import sk.peterjurkovic.cpr.validators.admin.CsnValidator;
import sk.peterjurkovic.cpr.web.editors.CsnCategoryEditor;
import sk.peterjurkovic.cpr.web.pagination.PageLink;
import sk.peterjurkovic.cpr.web.pagination.PaginationLinker;

/**
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 * @date 27.07.2013
 */
@Controller
public class CsnController extends SupportAdminController {
	
	// položka menu
	private static final int TAB_INDEX = 1;
	
	private static final String IMPORT_MODEL_ATTR = "uploadForm";
	
	private static final String CSN_MODEL_ATTR = "csn";
	
	private static final String CSN_TERMINOLOGY_LOG_PARAM = "l";
	
	private static final String CSN_TERMINOLOGY_ERROR_PARAM = "E";

	
		
	@Autowired
	private CsnService csnService;
	@Autowired
	private CsnCategoryService csnCategoryService;
	@Autowired
	private CsnTerminologyService csnTerminologyService;
	@Autowired
	private CsnCategoryEditor csnCategoryEditor;
	@Autowired
	private CsnValidator csnValidator;
	@Autowired
	private WordDocumentParser wordDocumentParser;
	@Autowired
	private CsnCsvImport csnCsvImport;
	@Autowired
	private CsnTerminologyLogService csnTerminologyLogService;
	
	@Autowired
	private FileService fileService;
	
	public CsnController(){
		setTableItemsView("csn/csn-list");
		setEditFormView("csn/csn-edit");
		setViewName("csn/csn-csvimport");
	}
	
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(CsnCategory.class, this.csnCategoryEditor);
    }
	
	
	@RequestMapping(value = "/admin/csn")
	public String showCsn(ModelMap modelMap, HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		int currentPage = RequestUtils.getPageNumber(request);
		Map<String, Object> params = RequestUtils.getRequestParameterMap(request);
		PageDto page = csnService.getCsnPage(currentPage, params);
		if(page.getCount() > 0){
			model.put("paginationLinks", getPaginationItems(request,params, currentPage, page.getCount()));
			model.put("csns", page.getItems() );
		}
		model.put("tab", TAB_INDEX);
		model.put("params", params);
		model.put("order", CsnOrderBy.getAll());
		modelMap.put("model", model);
		return getTableItemsView();
	}
	
	
	@RequestMapping( value = "/admin/csn/edit/{idCsn}", method = RequestMethod.GET)
	public String showCsnForm(@PathVariable Long idCsn, ModelMap modelMap, HttpServletRequest request) throws ItemNotFoundException{
		Csn form = null;
		if(idCsn == 0){
			form = createEmptyForm();
		}else{
			form = csnService.getById(idCsn);
			if(form == null){
				createItemNotFoundError("ČSN with ID: " + idCsn + " was not found.");
			}
			if(request.getParameter(CSN_TERMINOLOGY_ERROR_PARAM) != null){
				modelMap.put("importFaild", true);
			}else if(request.getParameter(CSN_TERMINOLOGY_LOG_PARAM) != null){
				Long id = Long.valueOf(request.getParameter(CSN_TERMINOLOGY_LOG_PARAM));
				CsnTerminologyLog log = csnTerminologyLogService.getById(id);
				if(log != null){
					modelMap.put("log", log);
				}
			}
			
		}
		prepareModel(form, modelMap, idCsn, RequestUtils.getLangParameter(request));
		return getEditFormView();
	}
	
	
	
	
	@RequestMapping( value = "/admin/csn/edit/{idCsn}", method = RequestMethod.POST)
	public String processSubmit(@PathVariable Long idCsn, @Valid Csn form, BindingResult result, ModelMap modelMap, HttpServletRequest request) throws ItemNotFoundException{
		
		csnValidator.validate(result, form);
		if(!result.hasErrors()){
			createOrUpdate(form);
			modelMap.put("successCreate", true);
		}
		prepareModel(form, modelMap, idCsn, RequestUtils.getLangParameter(request));
		return getEditFormView();
	}
	
	
	
	
	@RequestMapping( value = "/admin/csn/import/{idCsn}", method = RequestMethod.POST)
	public String processImport(
			@ModelAttribute(IMPORT_MODEL_ATTR) FileUploadItemDto uploadForm,
			BindingResult result,
			@PathVariable Long idCsn, 
			ModelMap modelMap,
			HttpServletRequest request
			) throws ItemNotFoundException{
		
		Csn csn = csnService.getById(idCsn);
		if(uploadForm == null){
			createItemNotFoundError("ČSN with ID: " + idCsn + " was not found.");
		}
		
		MultipartFile file = uploadForm.getFileData();
		
		if(file != null && StringUtils.isNotBlank(file.getOriginalFilename())){
			TikaProcessingContext tikaProcessingContext = new TikaProcessingContext();
			CsnTerminologyLog log = tikaProcessingContext.getLog();
			log.setFileName(file.getOriginalFilename());
			long start = System.currentTimeMillis();
			try{
				
				
				tikaProcessingContext.setCsnId(csn.getId());
				tikaProcessingContext.setContextPath(request.getContextPath());
				log.setCsn(csn);
				String docAsHtml = wordDocumentParser.parse(file.getInputStream(), tikaProcessingContext);
				if(StringUtils.isNotBlank(docAsHtml)){
					tikaProcessingContext.logInfo("Začátek čtení termínů");
					TerminologyParser terminologyParser = new NewTerminologyParserImpl();
					CsnTerminologyDto terminologies = terminologyParser.parse(docAsHtml, tikaProcessingContext);
					
					if(terminologies != null){
						tikaProcessingContext.logInfo(String.format("Čtení dokončeno. Počet termínů CZ/EN: %d / %d", 
									terminologies.getCzechTerminologies().size(), 
									terminologies.getEnglishTerminologies().size()));
					}
					
					if(terminologies == null || terminologies.areEmpty()){
						tikaProcessingContext.logInfo("Nenašel sa žýdný termín, Začátek čtení termínů bez čísel sekcí.");
						terminologyParser = new NoSectionTerminologyParser();
						terminologies = terminologyParser.parse(docAsHtml, tikaProcessingContext);
					}
					
					if(terminologies != null){
						
						terminologies.setCsn(csn);
						log.setCzCount(terminologies.getCzechTerminologies().size());
						log.setEnCount(terminologies.getEnglishTerminologies().size());
						
						csnTerminologyService.saveTerminologies(terminologies);
						csnService.saveOrUpdate(csn);
						log.setDuration(System.currentTimeMillis() - start);
						log.setSuccess(true);
						csnTerminologyLogService.createWithUser(log);
						modelMap.put("log", log);
					}
				}
			} catch (Exception  e) {
				log.logError(String.format("dokument %1$s se nepodařilo importovat, duvod: %2$s",  file.getOriginalFilename(), e.getMessage()));
				modelMap.put("hasErrors", true );
				return "redirect:/admin/csn/edit/"+idCsn + "?"+CSN_TERMINOLOGY_ERROR_PARAM+"=1";
			}
			log.updateImportStatus();
			csnTerminologyLogService.createWithUser(log);
			return "redirect:/admin/csn/edit/"+idCsn+"?"+CSN_TERMINOLOGY_LOG_PARAM+"=" + log.getId();
		}
		prepareModel(csn, modelMap, idCsn, RequestUtils.getLangParameter(request));
		return "redirect:/admin/csn/edit/"+idCsn;
	 }
	
	
	
	@RequestMapping("/admin/csn/delete/{id}")
	public String deleteCsn(@PathVariable Long id, ModelMap modelMap, HttpServletRequest request) throws ItemNotFoundException{
		
		Csn csn = csnService.getById(id);
		if(csn == null){
			createItemNotFoundError("ČSN with ID: " + id + " was not found.");
		}
		
		csnService.deleteCsn(csn);
		modelMap.put("successDelete", true);
		return showCsn(modelMap, request);
	}
	
	
	@RequestMapping(value = "/admin/csn/{id}/terminology/delete/all",  method = RequestMethod.GET)
	public String deleteAllTerminology(@PathVariable Long id, HttpServletRequest request, ModelMap modelMap) throws ItemNotFoundException{
		Csn csn = csnService.getById(id);
		if(csn == null){
			createItemNotFoundError("ČSN with ID: " + id + " was not found.");
		}
		csnTerminologyService.deleteAll(csn.getTerminologies());
		return showCsnForm(id, modelMap, request);
	}
	
	
	@RequestMapping(value = "/admin/csn/csvimport", method = RequestMethod.GET)
	public String showImportPage(){
		return getViewName();
	}
	
	@RequestMapping(value = "/admin/csn/csvimport", method = RequestMethod.POST)
	public String processCsvImport(@ModelAttribute(IMPORT_MODEL_ATTR) FileUploadItemDto uploadForm, 
								BindingResult bindingResult,
								HttpServletRequest request,
								ModelMap modelMap){
		MultipartFile file = uploadForm.getFileData();
		Map<String, Object> model = new HashMap<String, Object>();
		CsvImportLogDto log = null;
		if(file != null && StringUtils.isNotBlank(file.getOriginalFilename())){
			if(FilenameUtils.isExtension(file.getOriginalFilename(), "csv")){
				 try {
					 log = csnCsvImport.processImport(file.getInputStream());
					 //csnService.removeAll(); 
				} catch (IOException e) {
					modelMap.put("importFailed", true);
					logger.warn("CSV subor: " +file.getOriginalFilename() + " sa nepodarilo importovat. Duvod: " + e.getMessage());
				}
			
			}else{
				modelMap.put("importFailed", true);
			}
		}
		model.put("success", false);
		if(log != null){
			model.put("success", true);
			model.put("log", log);
		}
		modelMap.put("model", model);
		return getViewName();
	}
	
	/**
	 * Aktualizuje, alebo vytvori novu ČSN
	 * 
	 * @param form
	 * @throws ItemNotFoundException
	 */
	private void createOrUpdate(Csn form) throws ItemNotFoundException{
		Validate.notNull(form);

		Csn csn = null;
		
		if(form.getId() == null || form.getId() == 0){
			csn = new Csn();
		}else{
			csn = csnService.getById(form.getId());
			if(csn == null){
				createItemNotFoundError("ČSN with ID: " + form.getId() + " was not found.");
			}
		}
		csn.setCsnId(form.getCsnId());
		csn.setCzechName(form.getCzechName());
		csn.setEnglishName(form.getEnglishName());
		csn.setClassificationSymbol(form.getClassificationSymbol());
		csn.setIcs(form.getIcs());
		csn.setCsnCategory(form.getCsnCategory());
		csn.setPublished(form.getPublished());
		csn.setCsnOnlineId(form.getCsnOnlineId());
		csnService.saveOrUpdate(csn);
	}
	
	
	
	private void prepareModel(Csn form, ModelMap modelMap, Long id, String lang){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("tab", 2);
		model.put("csnCategories", csnCategoryService.getAll());
		if(form.getId() != null && form.getId() != 0l){
			if(StringUtils.isNotBlank(lang)){
				model.put("terminologies", csnService.getTerminologyByCsnAndLang(form, lang));
				model.put("lang", lang);
			}
		}
		
		modelMap.put("model", model);
		modelMap.put("id", id);
		modelMap.addAttribute(CSN_MODEL_ATTR, form);
		modelMap.addAttribute(IMPORT_MODEL_ATTR, new FileUploadItemDto());
	}
	
	
	
	
	private Csn createEmptyForm(){
		Csn form = new Csn();
		form.setId(0l);
		return form;
	}
	
	private  List<PageLink> getPaginationItems(HttpServletRequest request, Map<String, Object> params, int currentPage, int count){
		PaginationLinker paginger = new PaginationLinker(request, params);
		paginger.setUrl("/admin/csn");
		paginger.setCurrentPage(currentPage);
		paginger.setRowCount(count);
		return paginger.getPageLinks(); 
	}
	
	
	
}
