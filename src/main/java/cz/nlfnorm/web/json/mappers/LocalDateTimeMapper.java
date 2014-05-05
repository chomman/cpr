package cz.nlfnorm.web.json.mappers;

import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import cz.nlfnorm.web.json.deserializers.LocalDateTimeDeserializer;

@SuppressWarnings("serial")
public class LocalDateTimeMapper extends ObjectMapper {
	
	public LocalDateTimeMapper(){
		super();
		SimpleModule module = new SimpleModule("LocalDateTimeMapper");
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        registerModule(module);
	}
}
