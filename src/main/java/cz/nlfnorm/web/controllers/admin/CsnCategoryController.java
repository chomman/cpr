package cz.nlfnorm.web.controllers.admin;

import java.io.IOException;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cz.nlfnorm.csvimport.CsnCategoryCsvImport;
import cz.nlfnorm.dto.FileUploadItemDto;
import cz.nlfnorm.entities.CsnCategory;
import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.services.CsnCategoryService;

/**
 * Kontroler obsluhujúci požiadavky týkajúc sa odború ČSN0
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 * @date 02.08.2013
 */
@Controller
public class CsnCategoryController extends AdminSupportController {
	
	// položka menu
	public static final int TAB_INDEX = 3;
	private static final String IMPORT_MODEL_ATTR = "uploadForm";
	
	
	@Autowired
	private CsnCategoryService csnCategoryService;
	
	@Autowired
	private CsnCategoryCsvImport csnCategoryCsvImport;
	
	public CsnCategoryController(){
		setTableItemsView("csn/csn-category-list");
		setEditFormView("csn/csn-category-edit");
		setViewName("csn/csn-category-import");
	}
	
	
	@RequestMapping("/admin/csn/categories")
	public String showCsnCategories(ModelMap modelMap, @RequestParam(required = false, value = "code") String categoryCode){
		Map<String, Object> model = new HashMap<String, Object>();
		
		if(StringUtils.isBlank(categoryCode)){
			model.put("csnCategories", csnCategoryService.getSubRootCategories());
		}else{
			CsnCategory c = csnCategoryService.findBySearchCode(categoryCode);
			model.put("categoryNode", c);
		}		
		model.put("tab", TAB_INDEX);
		modelMap.put("model", model);
		return getTableItemsView();
	}
	
	
	
	@RequestMapping(value = "/admin/csn/category/edit/{id}", method = RequestMethod.GET)
	public String showform(@PathVariable Long id, ModelMap modelMap) throws ItemNotFoundException{
		
		CsnCategory form = null;
		
		if(id == 0){
			form = cerateEmpay();
		}else{
			form = csnCategoryService.getById(id);
			if(form == null){
				createItemNotFoundError("ČSN category with ID: " + id + " was not found.");
			}
		}
		prepareModel(form, modelMap, id);
		return getEditFormView();
	}
	
	
	
	@RequestMapping(value = "/admin/csn/category/edit/{categoryId}", method = RequestMethod.POST)
	public String processSubmit(@PathVariable Long categoryId, @Valid  CsnCategory form, BindingResult result, ModelMap modelMap) throws ItemNotFoundException{
		if(!result.hasErrors()){
			createOrUpdate(form);
        	modelMap.put("successCreate", true);
		}
		prepareModel(form, modelMap, categoryId);
		return getEditFormView();
	}
	
	@RequestMapping(value = "/admin/csn/category/import", method = RequestMethod.GET)
	public String showImportPage(){
		return getViewName();
	}
	
	@RequestMapping(value = "/admin/csn/category/import", method = RequestMethod.POST)
	public String processImport(@ModelAttribute(IMPORT_MODEL_ATTR) FileUploadItemDto uploadForm, 
								BindingResult bindingResult,
								HttpServletRequest request,
								ModelMap modelMap){
		MultipartFile file = uploadForm.getFileData();
		Map<String, Object> model = new HashMap<String, Object>();
		if(file != null && StringUtils.isNotBlank(file.getOriginalFilename())){
			if(FilenameUtils.isExtension(file.getOriginalFilename(), "csv")){
				try {
					int count = csnCategoryCsvImport.processImport(file.getInputStream());
				
					model.put("count", count);
					modelMap.put("model", model);
					modelMap.put("successCreate", true);
				} catch (IOException e) {
					modelMap.put("importFailed", true);
					logger.warn("CSV subor: " +file.getOriginalFilename() + " sa nepodarilo importovat. Duvod: " + e.getMessage());
				}
			}else{
				modelMap.put("importFailed", true);
			}
		}
		return getViewName();
	}
	
	
	
	@RequestMapping("/admin/csn/category/delete/{id}")
	public String processSubmit(@PathVariable Long id, ModelMap modelMap) throws ItemNotFoundException{
		
		CsnCategory category = csnCategoryService.getById(id);
		
		if(category == null){
			createItemNotFoundError("ČSN category with ID: " + id + " was not found.");
		}
		
		if(csnCategoryService.isCsnCategoryEmpty(category)){
			csnCategoryService.deleteCategory(category);
			modelMap.put("successDelete", true);
		}else{
			modelMap.put("errorNotEmpty", true);
		}
		
		return showCsnCategories(modelMap, null);
	}
	
	
	
	
	
	private void prepareModel(CsnCategory form, ModelMap modelMap, Long id){
		Map<String, Object> model = new HashMap<String, Object>();;
		model.put("tab", TAB_INDEX);
		modelMap.put("model", model);
		modelMap.put("id", id);
		modelMap.addAttribute("csnCategory", form);
	}
	
	
	private CsnCategory cerateEmpay(){
		CsnCategory category = new CsnCategory();
		category.setId(0l);
		return category;
	}
	
	
	private void createOrUpdate(CsnCategory form) throws ItemNotFoundException{
		Validate.notNull(form);
		Validate.notNull(form.getName());
		CsnCategory category = null;
		
		if(form.getId() == null || form.getId() == 0){
			category = new CsnCategory();
		}else{
			category = csnCategoryService.getById(form.getId());
			if(category == null){
				createItemNotFoundError("ČSN category with ID: " + form.getId() + " was not found.");
			}
		}
		category.setName(form.getName());
		category.setCode(csnCategoryService.getUniqeCsnCategoryCode(form.getName()));
		csnCategoryService.saveOrUpdate(category);
		
	}
	
	
	
}
