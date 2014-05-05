package cz.nlfnorm.web.json.deserializers;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.expression.ParseException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import cz.nlfnorm.constants.Constants;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
	
	private DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.DATE_TIME_FORMAT);

	@Override
	public LocalDateTime deserialize(JsonParser json, DeserializationContext context) throws IOException, JsonProcessingException {
		 	try {
		 		if(StringUtils.isBlank(json.getText())){
		 			return null;
		 		}
		 		LocalDateTime datetime =  formatter.parseLocalDateTime(json.getText());
	            return datetime;
	        } catch (ParseException e) {
	            return null;
	        }
	}
}
