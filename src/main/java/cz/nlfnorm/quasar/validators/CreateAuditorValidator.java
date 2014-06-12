package cz.nlfnorm.quasar.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import cz.nlfnorm.quasar.forms.AuditorForm;
import cz.nlfnorm.validators.forntend.ChangePassowrdValidator;

@Component
public class CreateAuditorValidator extends ChangePassowrdValidator {
	
	
	@Override
	protected void addExtraValidation(Object objectForm, Errors errors) {
		
		if(objectForm instanceof AuditorForm){
			final AuditorForm form = (AuditorForm)objectForm;
			final Long userId  = form.getAuditor().getId() == null ? 0l : form.getAuditor().getId();
			if(!userService.isUserNameUniqe(userId , form.getAuditor().getEmail())){
				errors.reject("user.email", "error.user.email");
			}
		}
		
		super.addExtraValidation(objectForm, errors);
	}
}
