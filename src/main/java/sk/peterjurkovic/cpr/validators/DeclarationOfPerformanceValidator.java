package sk.peterjurkovic.cpr.validators;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import sk.peterjurkovic.cpr.entities.DeclarationOfPerformance;
import sk.peterjurkovic.cpr.entities.EssentialCharacteristic;
import sk.peterjurkovic.cpr.web.forms.DeclarationOfPerformanceForm;

@Component
public class DeclarationOfPerformanceValidator {
	
	
	public void validate(BindingResult result, DeclarationOfPerformanceForm form){
		
		DeclarationOfPerformance dop = form.getDeclarationOfPerformance();
		
		if(StringUtils.isBlank(dop.getNumberOfDeclaration())){
			 result.reject("declarationOfPerformance.numberOfDeclaration", "Číslo prohlášení musí být vyplněno.");
		}
		
		if(StringUtils.isBlank(dop.getProductId())){
			 result.reject("declarationOfPerformance.productId", "Identifikátor výrobku musí být vyplněn.");
		}
		
		if(StringUtils.isBlank(dop.getSerialId())){
			 result.reject("declarationOfPerformance.serialId", "Typ, série nebo sériové číslo musí být vyplněno.");
		}
		
		if(StringUtils.isBlank(dop.getIntendedUse())){
			 result.reject("declarationOfPerformance.intendedUse", "Typ, série nebo sériové číslo musí být vyplněno.");
		}
		
		if(StringUtils.isBlank(dop.getManufacturer())){
			 result.reject("declarationOfPerformance.manufacturer", "Jméno, firma nebo registrovaná obchodní známka musí být vyplněno.");
		}
		
		boolean isValid = true;
		for(EssentialCharacteristic ech : form.getCharacteristics()){
			if(StringUtils.isBlank(ech.getValue())){
				isValid = false;
			}
		}
		if(!isValid){
			result.reject("id", "Vlastnosti musí být vyplněny.");
		}
		
	}
	
}
