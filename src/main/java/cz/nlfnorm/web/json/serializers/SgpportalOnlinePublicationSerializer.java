package cz.nlfnorm.web.json.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import cz.nlfnorm.web.json.dto.SgpportalOnlinePublication;

public class SgpportalOnlinePublicationSerializer extends JsonSerializer<SgpportalOnlinePublication>{

	@Override
	public void serialize(SgpportalOnlinePublication value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
			JsonProcessingException {
			jgen.writeStartObject();
	        jgen.writeNumberField("userId", value.getUserId());
	        jgen.writeStringField("serviceCode", value.getCode());
	        jgen.writeStringField("validity", value.getValidity().toString("yyyy-MM-dd"));
	        jgen.writeStringField("changed", value.getChanged().toString("yyyy-MM-dd HH:mm:ss"));
	        jgen.writeEndObject();
	}

}
