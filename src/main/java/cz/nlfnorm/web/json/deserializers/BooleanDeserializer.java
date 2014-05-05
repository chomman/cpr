package cz.nlfnorm.web.json.deserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class BooleanDeserializer extends JsonDeserializer<Boolean>{
	
	public Boolean deserialize(JsonParser json, DeserializationContext ctx) throws IOException, JsonProcessingException {
		return json.getBooleanValue();
	
	}
}
