package sk.peterjurkovic.cpr.web.json.mappers;

import org.joda.time.LocalDate;

import sk.peterjurkovic.cpr.web.json.deserializers.DateSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module.Feature;

public class HibernateAwareObjectMapper extends ObjectMapper {
	
	public HibernateAwareObjectMapper() {
		registerModule(new Hibernate4Module());
		SimpleModule module = new SimpleModule("LocalDate");
	    module.addSerializer(LocalDate.class, new DateSerializer());
	    registerModule(module);
	}
}
