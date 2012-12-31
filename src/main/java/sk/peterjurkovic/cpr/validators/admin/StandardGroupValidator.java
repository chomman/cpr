package sk.peterjurkovic.cpr.validators.admin;


import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import sk.peterjurkovic.cpr.entities.StandardGroup;
import sk.peterjurkovic.cpr.forms.admin.StandardGroupForm;
import sk.peterjurkovic.cpr.services.StandardGroupService;
import sk.peterjurkovic.cpr.utils.ValidateUtils;


/**
 * Validator pre formular editacia/pridavania skupin norem. 
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Component
public class StandardGroupValidator implements Validator{

	
	@Autowired
	private StandardGroupService standardGroupService;
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return StandardGroupForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		StandardGroupForm form = (StandardGroupForm)object;
		
		if(StringUtils.isBlank(form.getGroupName())){
			errors.rejectValue("groupName", "error.cpr.groups.name");
		}
		
		if(StringUtils.isNotBlank(form.getCommissionDecisionFileUrl()) && 
				!ValidateUtils.isValidHttpUrl(form.getCommissionDecisionFileUrl())){
			errors.rejectValue("commissionDecisionFileUrl", "error.cpr.groups.url");
		}
		
		if(form.getId() > 0 && form.getTimestamp() != null){
			DateTime formTimestamp = new DateTime(form.getTimestamp());
			StandardGroup standardGroup =  standardGroupService.getStandardGroupByid(form.getId());
			if(standardGroup != null && standardGroup.getChanged() != null && formTimestamp.isBefore(standardGroup.getChanged())){
				errors.rejectValue("commissionDecisionFileUrl", "error.cpr.groups.url");
			}
		}

		
	}
	

}
