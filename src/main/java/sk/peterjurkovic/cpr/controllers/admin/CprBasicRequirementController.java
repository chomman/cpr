package sk.peterjurkovic.cpr.controllers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sk.peterjurkovic.cpr.entities.BasicRequirement;
import sk.peterjurkovic.cpr.exceptions.ItemNotFoundException;
import sk.peterjurkovic.cpr.services.BasicRequirementService;
import sk.peterjurkovic.cpr.utils.CodeUtils;


@Controller
public class CprBasicRequirementController extends SupportAdminController {
	
	public static final int CPR_TAB_INDEX = 6;
	
	@Autowired
	private BasicRequirementService basicRequirementService;
	
	
	public CprBasicRequirementController(){
		setTableItemsView("cpr-basicrequirement");
		setEditFormView("cpr-basicrequirement-edit");
	}
	
	
	/**
	 * Zobrazi zakladne pozadavky
	 * 
	 * @param ModelMap model
	 * @return String view
	 */
	@RequestMapping("/admin/cpr/basicrequirements")
	public String showBasicRequirements(ModelMap modelMap) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<BasicRequirement> basicRequirements = basicRequirementService.getAllBasicRequirements();
		model.put("basicRequirements", basicRequirements);
		model.put("tab", CPR_TAB_INDEX);
		modelMap.put("model", model);
        return getTableItemsView();
	}
	
	
	
	/**
	 * Odsrani zakladny poziadavku na zaklade danoe ID.
	 * 
	 * @param Long ID zakladneho poziadavku
	 * @param ModelMap model
	 * @return String view
	 */
	@RequestMapping( value = "/admin/cpr/basicrequirements/delete/{basicRequirementId}", method = RequestMethod.GET)
	public String processDelete(@PathVariable Long basicRequirementId, ModelMap modelMap) {
		BasicRequirement basicRequirement = basicRequirementService.getBasicRequirementById(basicRequirementId);
		if(basicRequirement == null){
			modelMap.put("notFoundError", true);
			return getTableItemsView();
		}
		basicRequirementService.deleteBasicRequirement(basicRequirement);
		modelMap.put("successDelete", true);
		return showBasicRequirements(modelMap);
	}
	
	
	
	
	/**
	 * Zobrazi zobrazi view s webovym formularom pre editaciu, resp. pridanie noveho zakladne poziadavku podla.
	 * Ak je  basicRequirementId 0, tak sa jedna o pridanie novej polozky.
	 * 
	 * @param basicRequirementId
	 * @param model
	 * @return
	 */
	@RequestMapping( value = "/admin/cpr/basicrequirements/edit/{basicRequirementId}", method = RequestMethod.GET)
	public String showForm(@PathVariable Long basicRequirementId,  ModelMap model){
		
		BasicRequirement form = null;
		
		if(basicRequirementId == 0){
			form = createEmptyForm();
		}else{
			form = basicRequirementService.getBasicRequirementById(basicRequirementId);
			if(form == null){
				model.put("notFoundError", true);
				return getEditFormView();
			}
		}
		prepareModel(form, model, basicRequirementId);
		return getEditFormView();
	}
	
	
	/**
	 * Metoda, ktora ulozi odoslany formular obsahujuci Zakladny poziadavok. 
	 * V priade ak je ID nula, alebo NULL, jedna sa o vytvorenie novej polozky, inak o editaciu.
	 * 
	 * @param Long ID zakladneho poziadavku 
	 * @param BasicRequirement formular
	 * @param result
	 * @param model
	 * @return String view
	 * @throws ItemNotFoundException 
	 */
	@RequestMapping( value = "/admin/cpr/basicrequirements/edit/{basicRequirementId}", method = RequestMethod.POST)
	public String processSubmit(@PathVariable Long basicRequirementId,  @Valid  BasicRequirement form, BindingResult result, ModelMap model) throws ItemNotFoundException {

		if (result.hasErrors()) {
			prepareModel(form, model, basicRequirementId);
        }else{
        	if(basicRequirementService.isBasicRequirementNameUniqe(form.getName(), form.getId())){
	        	createOrUpdate(form);
	        	model.put("successCreate", true);
	        	if(basicRequirementId == 0){
	        		form = createEmptyForm();
	        		prepareModel(form, model, basicRequirementId);
	        	}
        	}else{
        		result.rejectValue("name", "error.uniqe");
        		prepareModel(form, model, basicRequirementId);
        	}
        }
		
        return getEditFormView();
	}
	
	
	private void createOrUpdate(BasicRequirement form) throws ItemNotFoundException{
		BasicRequirement basicRequirement = null;
			
		if(form.getId() == null || form.getId() == 0){
			basicRequirement = new BasicRequirement();
		}else{
			basicRequirement = basicRequirementService.getBasicRequirementById(form.getId()); 
			if(basicRequirement == null){
				createItemNotFoundError("Základní požadavek s ID: " + form.getId() + "se v systému nenachází");
			}
		}
		basicRequirement.setCode(CodeUtils.toSeoUrl(form.getName()));
		basicRequirement.setName(form.getName());
		basicRequirement.setDescription(form.getDescription());
		basicRequirement.setEnabled(form.getEnabled());
		basicRequirementService.createBasicRequirement(basicRequirement);
	}

	
	private void prepareModel(BasicRequirement form, ModelMap map, Long basicRequirementId){
		Map<String, Object> model = new HashMap<String, Object>();
		map.addAttribute("basicRequirement", form);
		model.put("basicRequirementId", basicRequirementId);
		model.put("tab", CPR_TAB_INDEX);
		map.put("model", model); 
	}
	
	
	
	
	private BasicRequirement createEmptyForm(){
		BasicRequirement form = new BasicRequirement();
		form.setId(0L);
		return form;
	}
}
