package sk.peterjurkovic.cpr.validators.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sk.peterjurkovic.cpr.dto.WebpageDto;
import sk.peterjurkovic.cpr.resolvers.LocaleResolver;
import sk.peterjurkovic.cpr.services.WebpageService;

@Component
public class WebpageValidator {
	
	@Autowired
	private WebpageService webpageService;
	
	
	public List<String> validate(WebpageDto webpage){
		List<String> errors = new ArrayList<String>();
		
		if(!LocaleResolver.isAvailable(webpage.getLocale())){
			errors.add("Nastala neočekávaná chyba, obnovte stránku a operaci opakujte.");
		}
		
		if(webpage.getLocale().equals(LocaleResolver.CODE_CZ) && StringUtils.isBlank(webpage.getName())){
			errors.add("Název sekce  stránky musí být vyplněn");
		}else if(webpage.getName().length() > 100){
			errors.add("Max. délka názevu sekce může být 100 znaků");
		}
		
		if(webpage.getLocale().equals(LocaleResolver.CODE_CZ) &&  StringUtils.isBlank(webpage.getTitle())){
			errors.add("Titulek sekce musí být vyplněn");
		}else if(webpage.getTitle().length() > 150){
			errors.add("Max. délka titulku sekce může být 150 znaků");
		}
		
		if(StringUtils.isNotBlank(webpage.getDescription()) && webpage.getDescription().length() > 255){
			errors.add("Max. délka popisku sekce může být 255 znaků");
		}
		
		if(!webpageService.isWebpageUrlUniqe(webpage.getCode(), webpage.getId())){
			errors.add("Webová sekce s URL " + webpage.getCode() + " se již v systému nachází");
		}
		
		return errors;
	}
}
