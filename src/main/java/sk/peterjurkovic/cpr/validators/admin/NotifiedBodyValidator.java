package sk.peterjurkovic.cpr.validators.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import sk.peterjurkovic.cpr.entities.NotifiedBody;
import sk.peterjurkovic.cpr.services.NotifiedBodyService;

@Component
public class NotifiedBodyValidator {
	
	@Autowired
	private NotifiedBodyService notifiedBodyService;
	
	
	public void validate(BindingResult result, NotifiedBody form){
		
		if(! notifiedBodyService.isNotifiedBodyNameUniqe(form.getName(), form.getId())){
			result.rejectValue("name", "error.uniqe");
		}
		
	}
	
}
