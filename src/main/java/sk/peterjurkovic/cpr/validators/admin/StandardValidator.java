package sk.peterjurkovic.cpr.validators.admin;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.exceptions.CollisionException;

@Component
public class StandardValidator {
		
	
	public void validate(BindingResult result, Standard form){
		
		if(form.getStartValidity() != null && form.getStopValidity() != null){
			
			 if(form.getStartValidity().isAfter(form.getStopValidity().getMillis())){
				 result.reject("startValidity", "Začátek platnosti nemůže být po skončení platnosti.");
			 }
		}
		
		if(form.getStartConcurrentValidity() != null && form.getStopConcurrentValidity() != null){
			 if(form.getStartConcurrentValidity().isAfter( form.getStopConcurrentValidity().getMillis())){
				 result.reject("startConcurrentValidity", "Začátek souběžné platnosti nemůže být po skončení souběžné platnosti.");
			 }
		}
			
	}
	
	public void validateCollision(Standard form, Standard persitedStandard) throws CollisionException{
		if(form.getTimestamp() != null && persitedStandard.getChanged().toDateTime().isAfter(form.getTimestamp())){
			throw new CollisionException("Při aktualizaci dat nastala kolize s jiným uživatelem.");
		}
	}


}	
