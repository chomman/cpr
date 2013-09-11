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

import sk.peterjurkovic.cpr.dto.CsnTerminologyDto;
import sk.peterjurkovic.cpr.dto.FileUploadItemDto;
import sk.peterjurkovic.cpr.dto.PageDto;
import sk.peterjurkovic.cpr.entities.Csn;
import sk.peterjurkovic.cpr.entities.CsnCategory;
import sk.peterjurkovic.cpr.enums.CsnOrderBy;
import sk.peterjurkovic.cpr.exceptions.ItemNotFoundException;
import sk.peterjurkovic.cpr.parser.TerminologyParser;
import sk.peterjurkovic.cpr.parser.TerminologyParserImpl;
import sk.peterjurkovic.cpr.parser.TikaProcessContext;
import sk.peterjurkovic.cpr.parser.WordDocumentParser;
import sk.peterjurkovic.cpr.services.CsnCategoryService;
import sk.peterjurkovic.cpr.services.CsnService;
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
	public static final int TAB_INDEX = 1;
	
	private static final String IMPORT_MODEL_ATTR = "uploadForm";
	
	private static final String CSN_MODEL_ATTR = "csn";
	
	private static final String DEFAULT_LANG_CODE = "cz";
	
		
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
			if(request.getParameter("e") != null){
				modelMap.put("importFaild", true);
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
		logger.info(String.format("ZACIATOK IMPORTU:  %s", file.getOriginalFilename() ));
		if(file != null && StringUtils.isNotBlank(file.getOriginalFilename())){
			long start = System.currentTimeMillis();
			try{
				
				TikaProcessContext tikaProcessContext = new TikaProcessContext();
				tikaProcessContext.setCsnId(csn.getId());
				tikaProcessContext.setContextPath(request.getContextPath());
				String docAsHtml = wordDocumentParser.parse(file.getInputStream(), tikaProcessContext);
				TerminologyParser terminologyParser = new TerminologyParserImpl();
				CsnTerminologyDto terminologies = terminologyParser.parse(docAsHtml, tikaProcessContext);
				terminologies.setCsn(csn);
				csn.setHtmlContent(docAsHtml);
				csnTerminologyService.saveTerminologies(terminologies);
				csnService.saveOrUpdate(csn);
				
			} catch (Exception  e) {
				logger.error(String.format("Dokument %1$s sa nepodarilo importovat dovod: %2$s",  file.getOriginalFilename(), e.getMessage()));
				modelMap.put("hasErrors", true );
				return "redirect:/admin/csn/edit/"+idCsn + "?e=1";
			}
			long end = System.currentTimeMillis() - start;
			logger.info(String.format("KONIEC IMPORTU, proces trval %s ms", end));
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
		if(file != null && StringUtils.isNotBlank(file.getOriginalFilename())){
			if(FilenameUtils.isExtension(file.getOriginalFilename(), "csv")){
				/*
				 * try {
					
				
					model.put("count", 1);
					modelMap.put("model", model);
					modelMap.put("successCreate", true);
				} catch (IOException e) {
					modelMap.put("importFailed", true);
					logger.warn("CSV subor: " +file.getOriginalFilename() + " sa nepodarilo importovat. Duvod: " + e.getMessage());
				}
				 * */
			}else{
				modelMap.put("importFailed", true);
			}
		}
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
