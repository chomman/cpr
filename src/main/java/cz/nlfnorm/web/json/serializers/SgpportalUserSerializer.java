package cz.nlfnorm.web.json.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import cz.nlfnorm.web.json.dto.SgpportalUser;

public class SgpportalUserSerializer extends JsonSerializer<SgpportalUser> {

	
	@Override
	public void serialize(SgpportalUser value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
			JsonProcessingException {
		jgen.writeStartObject();
        jgen.writeNumberField("userId", value.getUserId());
        jgen.writeStringField("login", value.getLogin());
        jgen.writeStringField("pass", value.getPass());
        jgen.writeStringField("changed", value.getChanged().toString("yyyy-MM-dd HH:mm:ss"));
        jgen.writeEndObject();
	}

}
