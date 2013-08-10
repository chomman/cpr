package sk.peterjurkovic.cpr.validators.admin;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import sk.peterjurkovic.cpr.entities.CsnTerminology;
import sk.peterjurkovic.cpr.services.CsnTerminologyService;

/**
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Component
public class CsnTerminologyValidator {

	@Autowired
	private CsnTerminologyService csnTerminologyService;
	
	
	public void validate(BindingResult result, CsnTerminology form){
		
		if(StringUtils.isBlank(form.getTitle())){
			result.rejectValue("title", "terminology.error.title.blank");
		}else if(!csnTerminologyService.isTitleUniqe(form.getCsn().getId(), form.getId(), form.getTitle())){
			result.rejectValue("title", "terminology.error.title.uniqe" ,new Object[] {form.getTitle(), form.getCsn().getCsnId() } , "");
		}else if(form.getTitle().length() > 255){
			result.rejectValue("title", "terminology.error.title.length");
		}
		
		if(StringUtils.isNotBlank(form.getSection()) && form.getSection().length() > 25){
			result.rejectValue("title", "terminology.error.section.length");
		}
		
		if(form.getLanguage() == null){
			result.rejectValue("language", "terminology.error.language");
		}
		
		
		
	}
}
