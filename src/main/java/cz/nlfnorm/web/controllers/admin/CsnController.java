package cz.nlfnorm.web.controllers.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.joda.time.LocalDate;
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

import cz.nlfnorm.csvimport.CsnCsvImport;
import cz.nlfnorm.dto.CsvImportLogDto;
import cz.nlfnorm.dto.FileUploadItemDto;
import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.Csn;
import cz.nlfnorm.entities.CsnCategory;
import cz.nlfnorm.entities.CsnTerminologyLog;
import cz.nlfnorm.enums.CsnOrderBy;
import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.parser.WordDocumentParser;
import cz.nlfnorm.services.CsnCategoryService;
import cz.nlfnorm.services.CsnService;
import cz.nlfnorm.services.CsnTerminologyLogService;
import cz.nlfnorm.services.CsnTerminologyService;
import cz.nlfnorm.services.FileService;
import cz.nlfnorm.utils.RequestUtils;
import cz.nlfnorm.validators.admin.CsnValidator;
import cz.nlfnorm.web.editors.CsnCategoryEditor;
import cz.nlfnorm.web.editors.LocalDateEditor;
import cz.nlfnorm.web.pagination.PageLink;
import cz.nlfnorm.web.pagination.PaginationLinker;

/**
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 * @date 27.07.2013
 */
@Controller
public class CsnController extends AdminSupportController {
	
	// položka menu
	private static final int TAB_INDEX = 1;
	
	private static final String IMPORT_MODEL_ATTR = "uploadForm";
	
	private static final String CSN_MODEL_ATTR = "csn";
	
	private static final String CSN_TERMINOLOGY_LOG_PARAM = "l";
	
	private static final String CSN_TERMINOLOGY_ERROR_PARAM = "E";
	
	private static final String CSN_SUCCES_CREATE_PARAM = "sc";
	
	private static final String CSN_SUCCES_UPDATE_PARAM = "su";
	
	private static final String CSN_EDIT_MAPPING_URL = "/admin/csn/edit/";

	
		
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
	private LocalDateEditor localDateEditor;
	
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
		binder.registerCustomEditor(LocalDate.class, this.localDateEditor);
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
	
	
	@RequestMapping( value = CSN_EDIT_MAPPING_URL + "{idCsn}", method = RequestMethod.GET)
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
			}else if(request.getParameter(CSN_SUCCES_CREATE_PARAM) != null){
				modelMap.put("successCreate", true);
			}else if(request.getParameter(CSN_SUCCES_UPDATE_PARAM) != null){
				modelMap.put("successUpdate", true);
			}
			
			
		}
		prepareModel(form, modelMap, idCsn, RequestUtils.getLangParameter(request));
		return getEditFormView();
	}
	
	
	
	
	@RequestMapping( value = CSN_EDIT_MAPPING_URL + "{idCsn}", method = RequestMethod.POST)
	public String processSubmit(@PathVariable Long idCsn, @Valid Csn form, BindingResult result, ModelMap modelMap, HttpServletRequest request) throws ItemNotFoundException{
		
		csnValidator.validate(result, form);
		if(!result.hasErrors()){
			Long id = createOrUpdate(form);
			if(idCsn == 0){
				return "redirect:"+ CSN_EDIT_MAPPING_URL +id + "?" + CSN_SUCCES_CREATE_PARAM + "=1";
			}
			return "redirect:"+CSN_EDIT_MAPPING_URL+id + "?" + CSN_SUCCES_UPDATE_PARAM + "=1";
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
		CsnTerminologyLog log = csnTerminologyService.processImport(file, csn, request.getContextPath());
		if(log == null){
			return "redirect:/admin/csn/edit/"+idCsn + "?"+CSN_TERMINOLOGY_ERROR_PARAM+"=1";
		}else{
			return "redirect:/admin/csn/edit/"+idCsn+"?"+CSN_TERMINOLOGY_LOG_PARAM+"=" + log.getId();
		}
	 }
	
	
	
	@RequestMapping("/admin/csn/delete/{id}")
	public String deleteCsn(@PathVariable Long id, ModelMap modelMap, HttpServletRequest request) throws ItemNotFoundException{
		
		Csn csn = csnService.getById(id);
		if(csn == null){
			createItemNotFoundError("ČSN with ID: " + id + " was not found.");
		}
		csnTerminologyLogService.removeCsnLogs(csn);
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
	private Long createOrUpdate(Csn form) throws ItemNotFoundException{
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
		csn.setCatalogId(form.getCatalogId());
		csnService.saveOrUpdate(csn);
		return csn.getId();
	}
	
	
	
	private void prepareModel(Csn form, ModelMap modelMap, Long id, String lang){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("tab", 2);
		model.put("csnCategories", csnCategoryService.getSubRootCategories());
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
