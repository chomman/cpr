package sk.peterjurkovic.cpr.validators.admin;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import sk.peterjurkovic.cpr.entities.Standard;

@Component
public class StandardValidator {
		
	
	public void validate(BindingResult result, Standard form){
		
		if(form.getStartValidity() != null && form.getStopValidity() != null){
			
			 if(form.getStartValidity().isAfter(form.getStopValidity())){
				 result.reject("startValidity", "Začátek platnosti nemůže být po skončení platnosti.");
			 }
		}
			
	}
	


}	
