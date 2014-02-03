package sk.peterjurkovic.cpr.web.json.mappers;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;

import sk.peterjurkovic.cpr.entities.WebpageCategory;
import sk.peterjurkovic.cpr.web.json.deserializers.WebpageCategoryDeserializer;

public class WebpageCategoryMapper extends ObjectMapper{
	
	public WebpageCategoryMapper(){
		super();
		SimpleModule module = new SimpleModule("WebpageCategoryMapper", new Version(2, 0, 0, null));
        module.addDeserializer(WebpageCategory.class, new WebpageCategoryDeserializer());
        registerModule(module);
	}
	
}
