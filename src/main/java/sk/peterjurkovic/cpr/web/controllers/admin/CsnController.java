package sk.peterjurkovic.cpr.web.controllers.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sk.peterjurkovic.cpr.entities.Csn;
import sk.peterjurkovic.cpr.entities.CsnCategory;
import sk.peterjurkovic.cpr.exceptions.ItemNotFoundException;
import sk.peterjurkovic.cpr.services.CsnCategoryService;
import sk.peterjurkovic.cpr.services.CsnService;
import sk.peterjurkovic.cpr.services.CsnTerminologyService;
import sk.peterjurkovic.cpr.validators.admin.CsnValidator;
import sk.peterjurkovic.cpr.web.editors.CsnCategoryEditor;

/**
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 * @date 27.07.2013
 */
@Controller
public class CsnController extends SupportAdminController {
	
	// položka menu
	public static final int TAB_INDEX = 1;
	
		
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
	
	public CsnController(){
		setTableItemsView("csn/csn-list");
		setEditFormView("csn/csn-edit");
	}
	
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(CsnCategory.class, this.csnCategoryEditor);
    }
	
	
	@RequestMapping("/admin/csn")
	public String showCsn(ModelMap modelMap, HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("tab", TAB_INDEX);
		model.put("csns", csnService.getAll() );
		modelMap.put("model", model);
		return getTableItemsView();
	}
	
	
	@RequestMapping( value = "/admin/csn/edit/{idCsn}", method = RequestMethod.GET)
	public String showCsnForm(@PathVariable Long idCsn, ModelMap modelMap) throws ItemNotFoundException{
		Csn form = null;
		if(idCsn == 0){
			form = createEmptyForm();
		}else{
			form = csnService.getById(idCsn);
			if(form == null){
				createItemNotFoundError("ČSN with ID: " + idCsn + " was not found.");
			}
		}
		prepareModel(form, modelMap, idCsn);
		return getEditFormView();
	}
	
	
	@RequestMapping( value = "/admin/csn/edit/{idCsn}", method = RequestMethod.POST)
	public String processSubmit(@PathVariable Long idCsn, @Valid Csn form, BindingResult result, ModelMap modelMap) throws ItemNotFoundException{
		
		csnValidator.validate(result, form);
		if(!result.hasErrors()){
			createOrUpdate(form);
			modelMap.put("successCreate", true);
		}
		prepareModel(form, modelMap, idCsn);
		return getEditFormView();
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
	
	
	
	private void prepareModel(Csn form, ModelMap modelMap, Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("tab", 2);
		model.put("csnCategories", csnCategoryService.getAll());
		modelMap.put("model", model);
		modelMap.put("id", id);
		modelMap.addAttribute("csn", form);
	}
	
	
	
	
	private Csn createEmptyForm(){
		Csn form = new Csn();
		form.setId(0l);
		return form;
	}
}
