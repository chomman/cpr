package sk.peterjurkovic.cpr.validators.admin;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import sk.peterjurkovic.cpr.entities.Csn;
import sk.peterjurkovic.cpr.services.CsnService;

/**
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 * @date Aug 6, 2013
 *
 */
@Component
public class CsnValidator {
	
	@Autowired
	private CsnService csnService;
	
	
	public void validate(BindingResult result, Csn form){
		
		if(StringUtils.isBlank(form.getCsnId())){
			result.rejectValue("csnId", "csn.error.csnId.blank");
		}else if(!csnService.isCsnIdUniqe(form.getId(), form.getCsnId())){
			result.rejectValue("csnId", "csn.error.csnId.uniqe" ,new Object[] {form.getCsnId() } , "");
		}else if(form.getCsnId().length() > 50){
			result.rejectValue("csnId", "csn.error.csnId.length");
		}
		
		if(StringUtils.isNotBlank(form.getPublished()) &&  !form.getPublished().matches("\\d{4}")){
			result.rejectValue("published", "csn.error.published");
		}
		
		if(StringUtils.isNotBlank(form.getCatalogId()) &&  !form.getCatalogId().matches("\\d{1,10}")){
			result.rejectValue("csnOnlineId", "csn.error.csnOnlineId");
		}
		
		if(StringUtils.isBlank(form.getCzechName())){
			result.rejectValue("czechName", "csn.error.czechName");
		}
		
		
	}
	
}
