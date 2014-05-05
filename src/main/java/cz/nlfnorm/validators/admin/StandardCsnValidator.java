package cz.nlfnorm.validators.admin;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import cz.nlfnorm.entities.StandardCsn;
import cz.nlfnorm.services.StandardCsnService;


@Component
public class StandardCsnValidator {
	
	
	@Autowired
	private StandardCsnService standardCsnService;
	@Autowired 
	private MessageSource messageSource;
	
	public void validate(BindingResult result, StandardCsn form){
		
		if(StringUtils.isBlank(form.getCsnName())){
			result.reject("csnName", messageSource.getMessage("cpr.csn.name.noempty", null, LocaleContextHolder.getLocale()) );
		}
		
		if(!standardCsnService.isStandardCsnUnique(form)){
			result.reject("csnOnlineId", messageSource.getMessage( "cpr.csn.uniqe", null, LocaleContextHolder.getLocale()) );
		}
			
	}
	
	
}
