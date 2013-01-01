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
import org.springframework.web.bind.annotation.SessionAttributes;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.controllers.SupportController;
import sk.peterjurkovic.cpr.entities.StandardGroup;
import sk.peterjurkovic.cpr.services.StandardGroupService;

@Controller
@SessionAttributes("standardGroup")
public class CprGroupController extends SupportController {
	
	
	public static final int CPR_TAB_INDEX = 2;
	
	@Autowired
	private StandardGroupService standardGroupService;
	
	
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
        return "/"+ Constants.ADMIN_PREFIX +"/cpr-groups";
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
			return "/"+ Constants.ADMIN_PREFIX +"/cpr-groups";
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
	public String showEditForm(@PathVariable Long standardGroupId,  ModelMap map) {
						
		StandardGroup form = null;
	
		// vytvorenie novej polozky
		if(standardGroupId < 1){
			form = new StandardGroup();
			form.setId(standardGroupId);
		}else{
			// editacia polozky
			form = standardGroupService.getStandardGroupByid(standardGroupId);
			if(form == null){
				map.put("notFoundError", true);
				return "/"+ Constants.ADMIN_PREFIX +"/cpr-groups-edit";
			}
		}
		prepareModel(form, map, standardGroupId);
        return "/"+ Constants.ADMIN_PREFIX +"/cpr-groups-edit";
	}
	
	
	/**
	 * Metoda kontroleru, ktora ulozi odoslany formular.
	 * 
	 * @param standardGroupId ID skupiny. V prípade ak je hodnota 0, jedna sa o pridanie novej polozky, 
	 * 			inak sa jedna o etidatciu
	 * @param map Model 
	 * @return String view
	 */
	@RequestMapping( value = "/admin/cpr/groups/edit/{standardGroupId}", method = RequestMethod.POST)
	public String processSubmit(@PathVariable Long standardGroupId,  @Valid  StandardGroup form, BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			prepareModel(form, model, standardGroupId);
        }else{
        	createOrUpdate(form);
        	model.put("successCreate", true);
        }
		
        return "/"+ Constants.ADMIN_PREFIX +"/cpr-groups-edit";
	}
	
	
	private void prepareModel(StandardGroup form, ModelMap map, Long standardGroupId){
		Map<String, Object> model = new HashMap<String, Object>();
		map.addAttribute("standardGroup", form);
		model.put("standardGroupId", standardGroupId);
		model.put("tab", CPR_TAB_INDEX);
		map.put("model", model); 
	}
	
	
	private StandardGroup createOrUpdate(StandardGroup form){
		StandardGroup standardGroup = null;
			
		if(form.getId() == 0){
			standardGroup = new StandardGroup();
		}else{
			standardGroup = standardGroupService.getStandardGroupByid(form.getId());
		}
		
		standardGroup.setCode(form.getCode());
		standardGroup.setGroupName(form.getGroupName());
		standardGroup.setCommissionDecisionUrl(form.getCommissionDecisionUrl());
		standardGroup.setUrlTitle(form.getUrlTitle());
		
		standardGroupService.saveOrdUpdateStandardGroup(standardGroup);
		return standardGroup;
	}
}
