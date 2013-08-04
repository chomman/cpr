package sk.peterjurkovic.cpr.web.controllers.admin;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sk.peterjurkovic.cpr.entities.CsnCategory;
import sk.peterjurkovic.cpr.exceptions.ItemNotFoundException;
import sk.peterjurkovic.cpr.services.CsnCategoryService;

/**
 * Kontroler obsluhujúci požiadavky týkajúc sa odború ČSN0
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 * @date 02.08.2013
 */
@Controller
public class CsnCategoryController extends SupportAdminController {
	
	// položka menu
	public static final int TAB_INDEX = 3;
	
	@Autowired
	private CsnCategoryService categoryService;
	
	
	
	public CsnCategoryController(){
		setTableItemsView("csn/csn-category-list");
		setEditFormView("csn/csn-category-edit");
	}
	
	
	@RequestMapping("/admin/csn/categories")
	public String showCsnCategories(ModelMap modelMap){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("tab", TAB_INDEX);
		model.put("csnCategories", categoryService.getAll());
		modelMap.put("model", model);
		return getTableItemsView();
	}
	
	
	
	@RequestMapping(value = "/admin/csn/category/edit/{id}", method = RequestMethod.GET)
	public String showform(@PathVariable Long id, ModelMap modelMap) throws ItemNotFoundException{
		
		CsnCategory form = null;
		
		if(id == 0){
			form = cerateEmpay();
		}else{
			form = categoryService.getById(id);
			if(form == null){
				createItemNotFoundError("ČSN category with ID: " + id + " was not found.");
			}
		}
		prepareModel(form, modelMap, id);
		return getEditFormView();
	}
	
	@RequestMapping(value = "/admin/csn/category/edit/{id}", method = RequestMethod.POST)
	public String processSubmit(@PathVariable Long id,@Valid  CsnCategory form, BindingResult result, ModelMap modelMap) throws ItemNotFoundException{
		if(!result.hasErrors()){
			createOrUpdate(form);
        	modelMap.put("successCreate", true);
		}
		prepareModel(form, modelMap, id);
		return getEditFormView();
	}
	
	@RequestMapping("/admin/csn/category/delete/{id}")
	public String processSubmit(@PathVariable Long id, ModelMap modelMap) throws ItemNotFoundException{
		
		CsnCategory category = categoryService.getById(id);
		
		if(category == null){
			createItemNotFoundError("ČSN category with ID: " + id + " was not found.");
		}
		
		if(categoryService.isCsnCategoryEmpty(category)){
			categoryService.deleteCategory(category);
			modelMap.put("successDelete", true);
		}else{
			modelMap.put("errorNotEmpty", true);
		}
		
		return showCsnCategories(modelMap);
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
			category = categoryService.getById(form.getId());
			if(category == null){
				createItemNotFoundError("ČSN category with ID: " + form.getId() + " was not found.");
			}
		}
		category.setName(form.getName());
		category.setCode(categoryService.getUniqeCsnCategoryCode(form.getName()));
		categoryService.saveOrUpdate(category);
		
	}
}
