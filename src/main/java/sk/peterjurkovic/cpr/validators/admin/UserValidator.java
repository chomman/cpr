package sk.peterjurkovic.cpr.validators.admin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.web.forms.admin.UserForm;

@Component
public class UserValidator {

	@Autowired
	private UserService userService;
	
	private Pattern pattern;
	private Matcher matcher;
 
	private static final String EMAIL_PATTERN = 
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
 
	
	public UserValidator(){
		pattern = Pattern.compile(EMAIL_PATTERN);
	}
	
	public void validate(BindingResult result, UserForm form){
		
		if(!validate(form.getUser().getEmail())){
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
				result.rejectValue("confifmPassword", "Zadaná hesla se neshodují");
			}
			
			if(StringUtils.isBlank(form.getPassword().trim()) || form.getPassword().length() < 5){
				result.rejectValue("password", "error.user.password");
			}
		}
		
		
		
		if(!userService.isUserNameUniqe(form.getUser().getId(), form.getUser().getEmail())){
			result.rejectValue("user.email", "error.user.email");
		}
	}
	
	
	
	public boolean validate(final String hex) {
		matcher = pattern.matcher(hex);
		return matcher.matches();
 
	}
	
}
