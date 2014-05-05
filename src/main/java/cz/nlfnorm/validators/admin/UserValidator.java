package cz.nlfnorm.validators.admin;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import cz.nlfnorm.services.UserService;
import cz.nlfnorm.utils.ValidationsUtils;
import cz.nlfnorm.web.forms.admin.UserForm;

@Component
public class UserValidator {

	@Autowired
	private UserService userService;
	
	
	public void validate(BindingResult result, UserForm form){
		
		if(!ValidationsUtils.isEmailValid(form.getUser().getEmail())){
			result.rejectValue("user.email", "error.user.email.invalid");
		}
		
		if(StringUtils.isBlank(form.user.getFirstName())){
			result.rejectValue("user.firstName", "error.user.firstName");
		}
		
		
		if(StringUtils.isBlank(form.user.getLastName())){
			result.rejectValue("user.lastName", "error.user.lastName");
		}
		
		
		
		if(form.getUser().getId() == null || 
		   form.getUser().getId() == 0 || 
		   StringUtils.isNotBlank(form.getPassword().trim())){
			
			if(!form.getPassword().equals(form.getConfifmPassword())){
				result.rejectValue("confifmPassword", "error.user.confifmPassword");
			}
			
			if(StringUtils.isBlank(form.getPassword().trim()) || form.getPassword().length() < 5){
				result.rejectValue("password", "error.user.password");
			}
		}
		
		if(form.getSelectedAuthorities().size() == 0){
			result.rejectValue("roles", "error.user.role.size");
		}
		
		
		
		if(!userService.isUserNameUniqe(form.getUser().getId(), form.getUser().getEmail())){
			result.rejectValue("user.email", "error.user.email");
		}
	}
	
	
	
	
}
