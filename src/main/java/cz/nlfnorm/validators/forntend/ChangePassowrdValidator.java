package cz.nlfnorm.validators.forntend;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import cz.nlfnorm.context.ContextHolder;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.validators.AbstractValidator;
import cz.nlfnorm.validators.PasswordValidator;
import cz.nlfnorm.web.forms.portal.ChangePasswordForm;
import cz.nlfnorm.web.forms.portal.ResetPassowrdForm;

@Component
public class ChangePassowrdValidator extends AbstractValidator{

	@Autowired
	protected UserService userService;
	@Autowired
	protected MessageSource messageSource;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	@Override
	protected void addExtraValidation(Object objectForm, Errors errors) {
		
			if(objectForm instanceof ChangePasswordForm){		
				
				final String newPass = ((ResetPassowrdForm) objectForm).getNewPassword();
				final String confirmPass = ((ResetPassowrdForm) objectForm).getConfirmPassword();
				final Long id = ((ResetPassowrdForm) objectForm).getUserId();
				if(id != null){
					final User user = userService.getUserById(id);
					validateOldPasMatches(user.getPassword(), newPass, errors);
					validateLoginSimularity(user.getEmail(), newPass, errors);
				}
				validatePasswordStrength(newPass, errors);
				validatePasswordsMatches(newPass, confirmPass, errors);
						
			}

		}
	
	
	
	protected void validateOldPasMatches(final String currentPass, final String newPass, Errors errors) {
		if(!passwordEncoder.matches(newPass, currentPass)){
			errors.reject("user.currentPassword", 
					messageSource.getMessage("error.password.old", null, ContextHolder.getLocale()) );
		}
	}
	
	
	
	protected void validateLoginSimularity(final String login, final String password, Errors errors) {
		if(login.endsWith(password)){
			errors.reject("user.newPassword", 
					messageSource.getMessage("error.password.sameAsLogin", null, ContextHolder.getLocale()) );
		}
	}
	
	
	
	protected void validatePasswordsMatches(final String newPass, final String confirmPass, Errors errors){
		if(StringUtils.isBlank(confirmPass) || StringUtils.isBlank(newPass) || !newPass.equals(confirmPass) ){
			errors.reject("user.newPassword", 
					messageSource.getMessage("error.password.notMatch", null, ContextHolder.getLocale()) );
		}
	}
	
	
	
	@SuppressWarnings("static-access")
	protected void validatePasswordStrength(final String password, Errors errors){
		PasswordValidator validator = PasswordValidator.buildValidator(false,false, true, 6, 20);
		if(!validator.validatePassword(password)){
			errors.reject("user.newPassword", 
					messageSource.getMessage("error.password.missNumber", null, ContextHolder.getLocale()) );
		}
	}
	
}
