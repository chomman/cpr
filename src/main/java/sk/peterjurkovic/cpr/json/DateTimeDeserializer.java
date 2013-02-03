package sk.peterjurkovic.cpr.json;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
	private Logger logger = Logger.getLogger(getClass());
	
	public DateTimeDeserializer(){
		super(DateTime.class);
	}

	@Override
	public DateTime deserialize(JsonParser json, DeserializationContext context) throws IOException, JsonProcessingException {
		 	try {
		 		if(StringUtils.isBlank(json.getText())){
		 			return null;
		 		}
		 		logger.info("TIME: " + json.getText());
	            DateTime datetime =  formatter.parseDateTime(json.getText());
	            logger.info(datetime.getZone());
	            return datetime;
	        } catch (ParseException e) {
	            return null;
	        }
	}
}
