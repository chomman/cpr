package sk.peterjurkovic.cpr.web.controllers.admin;

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
import org.springframework.web.bind.annotation.SessionAttributes;

import sk.peterjurkovic.cpr.entities.StandardGroup;
import sk.peterjurkovic.cpr.exceptions.ItemNotFoundException;
import sk.peterjurkovic.cpr.services.StandardGroupService;
import sk.peterjurkovic.cpr.utils.CodeUtils;

@Controller
@SessionAttributes("standardGroup")
public class CprGroupController extends SupportAdminController {
	
	
	public static final int CPR_TAB_INDEX = 2;
	
	@Autowired
	private StandardGroupService standardGroupService;
	
	
	public CprGroupController(){
		setTableItemsView("cpr-groups");
		setEditFormView("cpr-groups-edit");
	}
	
	
	/**
	 * Metoda kontroleru, ktora zobrazi skupiny výrobku
	 * 
	 * @param modelMap model
	 * @return String view, ktore bude interpretovane
	 */
	@RequestMapping("/admin/cpr/groups")
    public String showCprGroupsPage(ModelMap modelMap) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		List<StandardGroup> groups = standardGroupService.getAllStandardGroups();
		model.put("groups", groups);
		model.put("tab", CPR_TAB_INDEX);
		
		
		modelMap.put("model", model);
        return getTableItemsView();
    }
	
	
	
	
	/**
	 * Metoda kontroleru, ktora odstrani skupinu vyrobku. V pripade ak skupina obsahuje 1 a viac vyrobkov,
	 * zobrazi sa chybove upozornenie a nedovoli odstranit polozku
	 * 
	 * @param Long standardGroupId, id skupiny
	 * @param map Model 
	 * @return String view
	 */
	@RequestMapping( value = "/admin/cpr/groups/delete/{standardGroupId}", method = RequestMethod.GET)
	public String deleteGroup(@PathVariable Long standardGroupId,  ModelMap modelMap) {
						
		StandardGroup standardGroup = standardGroupService.getStandardGroupByid(standardGroupId);
		if(standardGroup == null){
			modelMap.put("notFoundError", true);
			return getTableItemsView();
		}
		
		if(standardGroupService.getCountOfStandardsInGroup(standardGroup) > 0){
			modelMap.put("isNotEmptyError", true);
		}else{
			standardGroupService.deleteStandardGroup(standardGroup);
			modelMap.put("successDelete", true);
		}
		
        return showCprGroupsPage(modelMap);
	}
	
	
	
	/**
	 * Metoda kontroleru, ktora zobrazi formulár pre pridanie, resp. editovanie skupiny vyrobkov.
	 * Ak je ID skupiny 0, tak sa jedna o pridanie novej polozky.
	 * 
	 * @param standardGroupId ID skupiny. V prípade ak je 0, jedna sa o pridanie novej skupiny
	 * @param modelMap model, data
	 * @return String view obsahujuce formular
	 */
	@RequestMapping( value = "/admin/cpr/groups/edit/{standardGroupId}", method = RequestMethod.GET)
	public String showForm(@PathVariable Long standardGroupId,  ModelMap model) {
						
		StandardGroup form = null;
	
		// vytvorenie novej polozky
		if(standardGroupId == 0){
			form = createEmptyForm();
		}else{
			// editacia polozky
			form = standardGroupService.getStandardGroupByid(standardGroupId);
			if(form == null){
				model.put("notFoundError", true);
				return getEditFormView();
			}
		}
		prepareModel(form, model, standardGroupId);
        return getEditFormView();
	}
	
	
	/**
	 * Metoda kontroleru, ktora ulozi odoslany formular.
	 * 
	 * @param standardGroupId ID skupiny. V prípade ak je hodnota 0, jedna sa o pridanie novej polozky, 
	 * 			inak sa jedna o etidatciu
	 * @param map Model 
	 * @return String view
	 * @throws ItemNotFoundException 
	 */
	@RequestMapping( value = "/admin/cpr/groups/edit/{standardGroupId}", method = RequestMethod.POST)
	public String processSubmit(@PathVariable Long standardGroupId,  @Valid  StandardGroup form, BindingResult result, ModelMap model) throws ItemNotFoundException {

		if (result.hasErrors()) {
			prepareModel(form, model, standardGroupId);
        }else{
        	if(standardGroupService.isStandardGroupNameUniqe(form.getGroupName(), form.getId())){
	        	createOrUpdate(form);
	        	model.put("successCreate", true);
	        	if(standardGroupId == 0){
	        		form = createEmptyForm();
	        		prepareModel(form, model, standardGroupId);
	        	}
        	}else{
        		result.rejectValue("groupName", "error.uniqe");
        		prepareModel(form, model, standardGroupId);
        	}
        }
		
        return getEditFormView();
	}
	
	
	private void prepareModel(StandardGroup form, ModelMap map, Long standardGroupId){
		Map<String, Object> model = new HashMap<String, Object>();
		map.addAttribute("standardGroup", form);
		model.put("standardGroupId", standardGroupId);
		model.put("tab", CPR_TAB_INDEX);
		map.put("model", model); 
	}
	
	
	private StandardGroup createOrUpdate(StandardGroup form) throws ItemNotFoundException{
		StandardGroup standardGroup = null;
			
		if(form.getId() == null || form.getId() == 0){
			standardGroup = new StandardGroup();
		}else{
			standardGroup = standardGroupService.getStandardGroupByid(form.getId());
			if(standardGroup == null){
				createItemNotFoundError("Norma s ID: "+ form.getId() + " se v systému nenachází");
			}
		}
		
		standardGroup.setCode(CodeUtils.toSeoUrl(form.getGroupName()));
		standardGroup.setGroupName(form.getGroupName());
		standardGroup.setDescription(form.getDescription());
		standardGroup.setGroupCode(form.getGroupCode());
		standardGroup.setCommissionDecisionFileUrl(form.getCommissionDecisionFileUrl());
		standardGroup.setUrlTitle(form.getUrlTitle());
		standardGroup.setEnabled(form.getEnabled());
		standardGroupService.saveOrdUpdateStandardGroup(standardGroup);
		return standardGroup;
	}
	
	private StandardGroup createEmptyForm(){
		StandardGroup form = new StandardGroup();
		form.setId(0L);
		return form;
	}
}
