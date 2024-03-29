package cz.nlfnorm.web.json.mappers;

import org.joda.time.LocalDate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

import cz.nlfnorm.web.json.deserializers.DateSerializer;
import cz.nlfnorm.web.json.deserializers.LocalDateDeserializer;

public class HibernateAwareObjectMapper extends ObjectMapper {
	
	public HibernateAwareObjectMapper() {
		registerModule(new Hibernate4Module());
		SimpleModule module = new SimpleModule("LocalDate");
		module.addSerializer(LocalDate.class, new DateSerializer());
		module.addDeserializer(LocalDate.class, new LocalDateDeserializer());
	    registerModule(module);
	}
}
