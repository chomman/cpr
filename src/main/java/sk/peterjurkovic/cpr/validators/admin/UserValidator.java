package sk.peterjurkovic.cpr.validators.admin;

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
	
	
	public void validate(BindingResult result, UserForm form){
		
		if(StringUtils.isBlank(form.user.getFirstName())){
			result.rejectValue("user.firstName", "Jméno musí být vyplněno");
		}
		
		
		if(StringUtils.isBlank(form.user.getLastName())){
			result.rejectValue("user.firstName", "Příjmení musí být vyplněno");
		}
		
		if(StringUtils.isBlank(form.getPassword().trim()) || form.getPassword().length() < 5){
			result.rejectValue("password", "Heslo musí mít minimálně 5 znaků");
		}
		
		if(!form.getPassword().equals(form.getConfifmPassword())){
			result.rejectValue("confifmPassword", "Zadaná hesla se neshodují");
		}
		
		if(!userService.isUserNameUniqe(form.getUser().getId(), form.getUser().getEmail())){
			result.rejectValue("user.email", "Uživatel s emailovou adresou: "+form.getUser().getEmail() 
							+" se je již v systému registrován");
		}
	}
	
}
