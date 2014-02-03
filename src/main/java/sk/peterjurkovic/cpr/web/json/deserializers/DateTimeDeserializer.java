package sk.peterjurkovic.cpr.web.json.deserializers;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdDeserializer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.expression.ParseException;

import sk.peterjurkovic.cpr.constants.Constants;

public class DateTimeDeserializer extends StdDeserializer<DateTime> {
	
	private DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.DATE_TIME_FORMAT);
	
	public DateTimeDeserializer(){
		super(DateTime.class);
	}

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
