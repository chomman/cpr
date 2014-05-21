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
import cz.nlfnorm.web.forms.portal.ChangePasswordForm;
import cz.nlfnorm.web.forms.portal.ResetPassowrdForm;

@Component
public class ChangePassowrdValidator extends AbstractValidator{

	@Autowired
	private UserService userService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Override
	protected void addExtraValidation(Object objectForm, Errors errors) {
		
			objectForm = (ResetPassowrdForm)objectForm;
			if(objectForm instanceof ChangePasswordForm){
				final User user = userService.getUserById(((ChangePasswordForm) objectForm).getUserId());
				
				if(!passwordEncoder.matches(((ChangePasswordForm) objectForm).getCurrentPassword(), user.getPassword())){
					errors.reject("user.currentPassword", 
							messageSource.getMessage("error.password.old", null, ContextHolder.getLocale()) );
				}
			}
			final String newPass = ((ResetPassowrdForm) objectForm).getNewPassword();
			final String confirmPass = ((ResetPassowrdForm) objectForm).getConfirmPassword();
			
			
			if(StringUtils.isBlank(confirmPass) || StringUtils.isBlank(newPass) || !newPass.equals(confirmPass) ){
				errors.reject("user.newPassword", 
						messageSource.getMessage("error.password.notMatch", null, ContextHolder.getLocale()) );
			}
		}
	
}
