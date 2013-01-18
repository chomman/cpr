package sk.peterjurkovic.cpr.validators.admin;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import sk.peterjurkovic.cpr.entities.Article;

@Component
public class ArticleValidator {

	
	
	public void validate(BindingResult result, Article form){
		
		if(StringUtils.isBlank(form.getTitle())){
			result.reject("title", "Titulek aktuality musí být vyplněn");
		}
	}
}
