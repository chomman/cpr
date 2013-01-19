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
		
		if(form.getTimestamp() != null && persistedArticle.getChanged().isAfter(form.getTimestamp())){
			errors.add("Nastala synchronizační chyba. Položka byla upravena jiným uživatelům těsně před Vaší změnou.");
		}
		
		return errors;
	}
}
