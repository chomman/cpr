package sk.peterjurkovic.cpr.web.json.deserializers;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;

import sk.peterjurkovic.cpr.entities.WebpageContent;
import sk.peterjurkovic.cpr.services.WebpageContentService;

public class WebpageContentDeserializer extends StdDeserializer<WebpageContent>{
	
	public WebpageContentDeserializer() {
		super(WebpageContent.class);
	}

	@Autowired
	private WebpageContentService webpageContentService;
	
	@Override
	public WebpageContent deserialize(JsonParser json, DeserializationContext ctx) throws IOException, JsonProcessingException {
		if(StringUtils.isBlank(json.getText())){
			return null;
		}
		try {
			final Long id = Long.valueOf(json.getText());
			return webpageContentService.getWebpageContentById(id);
			
		} catch (ParseException e) {
            return null;
        }
	}
	
}
