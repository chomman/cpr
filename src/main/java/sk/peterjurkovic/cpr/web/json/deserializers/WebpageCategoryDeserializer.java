package sk.peterjurkovic.cpr.web.json.deserializers;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;

import sk.peterjurkovic.cpr.entities.WebpageCategory;
import sk.peterjurkovic.cpr.services.WebpageCategoryService;

public class WebpageCategoryDeserializer extends StdDeserializer<WebpageCategory>{

	public WebpageCategoryDeserializer() {
		super(WebpageCategory.class);
	}

	@Autowired
	private WebpageCategoryService webpageCategoryService;
	
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
