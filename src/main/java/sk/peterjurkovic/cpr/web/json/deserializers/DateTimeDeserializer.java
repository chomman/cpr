package sk.peterjurkovic.cpr.web.json.deserializers;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.expression.ParseException;

import sk.peterjurkovic.cpr.constants.Constants;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class DateTimeDeserializer extends JsonDeserializer<DateTime> {
	
	private DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.DATE_TIME_FORMAT);

	@Override
	public DateTime deserialize(JsonParser json, DeserializationContext context) throws IOException, JsonProcessingException {
		 	try {
		 		if(StringUtils.isBlank(json.getText())){
		 			return null;
		 		}
	            DateTime datetime =  formatter.parseDateTime(json.getText());
	            return datetime;
	        } catch (ParseException e) {
	            return null;
	        }
	}
}
