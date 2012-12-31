package sk.peterjurkovic.cpr.controllers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.controllers.SupportController;
import sk.peterjurkovic.cpr.entities.StandardGroup;
import sk.peterjurkovic.cpr.forms.admin.StandardGroupForm;
import sk.peterjurkovic.cpr.services.StandardGroupService;
import sk.peterjurkovic.cpr.validators.admin.StandardGroupValidator;

@Controller
@SessionAttributes("standardGroup")
public class CprGroupController extends SupportController {
	
	
	public static final int CPR_TAB_INDEX = 2;
	
	@Autowired
	private StandardGroupService standardGroupService;
	
	@Autowired
	private StandardGroupValidator validator;
	
	@RequestMapping("/admin/cpr/groups")
    public String showCprGroupsPage(HttpServletRequest request, ModelMap modelMap) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		List<StandardGroup> groups = standardGroupService.getAllStandardGroups();
		model.put("groups", groups);
		model.put("tab", CPR_TAB_INDEX);
		
		
		modelMap.put("model", model);
        return "/"+ Constants.ADMIN_PREFIX +"/cpr-groups";
    }
	
	
	
	

	@RequestMapping( value = "/admin/cpr/groups/edit/{standardGroupId}", method = RequestMethod.GET)
	public String showEditForm(@PathVariable Long standardGroupId, HttpServletRequest request, ModelMap map) {
						
		StandardGroupForm form = null;
	
		// vytvorenie novej polozky
		if(standardGroupId < 1){
			form = new StandardGroupForm();
		}else{
			// editacia polozky
			StandardGroup standardGroup = standardGroupService.getStandardGroupByid(standardGroupId);
			if(standardGroup == null){
				map.put("notFoundError", true);
				return "/"+ Constants.ADMIN_PREFIX +"/cpr-groups-edit";
			}
			form = createFormFromStandardGroup(standardGroup, standardGroupId);
		}
		prepareModel(form, map, standardGroupId);
        return "/"+ Constants.ADMIN_PREFIX +"/cpr-groups-edit";
	}
	
	
	
	@RequestMapping( value = "/admin/cpr/groups/edit/{standardGroupId}", method = RequestMethod.POST)
	public String processSubmit(@PathVariable Long standardGroupId, HttpServletRequest request, @ModelAttribute("companyInfo") StandardGroupForm form, Errors errors,
            ModelMap model, SessionStatus status) {

		validator.validate(form, errors);
		if (errors.hasErrors()) {
			prepareModel(form, model, standardGroupId);
        }else{
        	createOrUpdate(form);
        	model.put("success", true);
        }
        return "/"+ Constants.ADMIN_PREFIX +"/cpr-groups-edit";
	}
	
	
	
	private StandardGroupForm createFormFromStandardGroup(StandardGroup standardGroup, Long standardGroupId){
		StandardGroupForm form = new StandardGroupForm();
		form.setCommissionDecisionFileUrl(standardGroup.getCommissionDecisionUrl());
		form.setGroupName(standardGroup.getGroupName());
		form.setCode(standardGroup.getCode());
		form.setId(standardGroupId);
		DateTime timestamp = standardGroup.getCreated();
		if(timestamp != null){
			form.setTimestamp(timestamp.toString());
		}
		
		return form;
	}
	
	
	private void prepareModel(StandardGroupForm form, ModelMap map, Long standardGroupId){
		Map<String, Object> model = new HashMap<String, Object>();
		map.put("standardGroup", form);
		model.put("standardGroupId", standardGroupId);
		model.put("tab", CPR_TAB_INDEX);
		map.put("model", model); 
	}
	
	
	private StandardGroup createOrUpdate(StandardGroupForm form){
		StandardGroup standardGroup = null;
			
		if(form.getId() == 0){
			standardGroup = new StandardGroup();
		}else{
			standardGroup = standardGroupService.getStandardGroupByid(form.getId());
		}
		
		standardGroup.setCode(form.getCode());
		standardGroup.setGroupName(form.getGroupName());
		standardGroup.setCommissionDecisionUrl(form.getCommissionDecisionFileUrl());
		
		
		standardGroupService.saveOrdUpdateStandardGroup(standardGroup);
		return standardGroup;
	}
}
