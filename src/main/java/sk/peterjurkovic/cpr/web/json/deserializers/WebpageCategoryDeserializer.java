package sk.peterjurkovic.cpr.web.json.deserializers;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import sk.peterjurkovic.cpr.entities.WebpageCategory;
import sk.peterjurkovic.cpr.services.WebpageCategoryService;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


public class WebpageCategoryDeserializer extends JsonDeserializer<WebpageCategory>{

	@Autowired
	private WebpageCategoryService webpageCategoryService;
	
	public WebpageCategoryDeserializer(){
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	@Override
	public WebpageCategory deserialize(JsonParser json, DeserializationContext ctx) throws IOException, JsonProcessingException {
		if(StringUtils.isBlank(json.getText())){
			return null;
		}
		try {
			final Long id = Long.valueOf(json.getText());
			return webpageCategoryService.getWebpageCategoryById(id);
			
		} catch (ParseException e) {
            return null;
        }
	}

}
