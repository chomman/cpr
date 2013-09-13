package sk.peterjurkovic.cpr.validators.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import sk.peterjurkovic.cpr.entities.Article;

@Component
public class ArticleValidator {

	
	
	public List<String> validate(Article form, Article persistedArticle){
		List<String> errors = new ArrayList<String>();
		
		if(StringUtils.isBlank(form.getTitle())){
			errors.add("Titulek aktuality musí být vyplněn");
		}
		
		if(form.getTimestamp() != null && persistedArticle.getChanged().toDateTime().isAfter(form.getTimestamp())){
			errors.add("Při aktualizaci dat nastala kolize s jiným uživatelem.");
		}
		
		if(form.getPublishedSince() != null && form.getPublishedUntil() != null){
			 if(form.getPublishedSince().toDateTime().isAfter( form.getPublishedUntil().toDateTime().getMillis())){
				 errors.add("Začátek nemůže být po skončení publikování.");
			 }
		}
		
		return errors;
	}
}
