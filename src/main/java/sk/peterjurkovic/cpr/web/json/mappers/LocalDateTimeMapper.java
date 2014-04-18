package sk.peterjurkovic.cpr.web.json.mappers;

import org.joda.time.LocalDateTime;

import sk.peterjurkovic.cpr.web.json.deserializers.LocalDateTimeDeserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

@SuppressWarnings("serial")
public class LocalDateTimeMapper extends ObjectMapper {
	
	public LocalDateTimeMapper(){
		super();
		SimpleModule module = new SimpleModule("LocalDateTimeMapper");
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        registerModule(module);
	}
}
