package sk.peterjurkovic.cpr.web.json.mappers;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;

import sk.peterjurkovic.cpr.entities.WebpageContent;
import sk.peterjurkovic.cpr.web.json.deserializers.WebpageContentDeserializer;

public class WebpageContentMapper extends ObjectMapper {
	
	public WebpageContentMapper(){
		super();
		SimpleModule module = new SimpleModule("WebpageCategoryMapper", new Version(2, 0, 0, null));
        module.addDeserializer(WebpageContent.class, new WebpageContentDeserializer());
        registerModule(module);
	}
	
}
