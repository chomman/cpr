package cz.nlfnorm.validators.forntend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import cz.nlfnorm.context.ContextHolder;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.validators.AbstractValidator;
import cz.nlfnorm.web.forms.portal.PortalUserForm;

@Component
public class PortalUserValidator extends AbstractValidator {

	@Autowired
	private UserService userService;
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected void addExtraValidation(Object objectForm, Errors errors) {
		if(objectForm instanceof  PortalUserForm){
					
			PortalUserForm form = (PortalUserForm)objectForm;
			
			if(!form.getPassword().equals(form.getConfirmPassword())){
				errors.reject("user.password", 
						messageSource.getMessage("error.password.notMatch", null, ContextHolder.getLocale()) );
			}
			
			if(!userService.isUserNameUniqe(0l, form.getEmail())){
				errors.reject("user.password", 
						messageSource.getMessage("error.email.uniqe", null, ContextHolder.getLocale()));
			}
		}
	}
	
	
	

	

}
