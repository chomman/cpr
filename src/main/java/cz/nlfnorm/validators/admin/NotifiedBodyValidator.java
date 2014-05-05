package cz.nlfnorm.validators.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import cz.nlfnorm.entities.NotifiedBody;
import cz.nlfnorm.services.NotifiedBodyService;

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
