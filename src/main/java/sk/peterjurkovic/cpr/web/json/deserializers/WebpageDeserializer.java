package sk.peterjurkovic.cpr.web.json.deserializers;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.services.WebpageService;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class WebpageDeserializer extends JsonDeserializer<Webpage>{
	
	@Autowired
	private WebpageService webpageService;
	
	public WebpageDeserializer(){
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	
	@Override
	public Webpage deserialize(JsonParser json, DeserializationContext context) throws IOException, JsonProcessingException {
		if(StringUtils.isBlank(json.getText())){
			return null;
		}
		try {
			final Long id = Long.valueOf(json.getText());
			return webpageService.getWebpageById(id);
		} catch (ParseException e) {
			return null;
		}
	}

}
