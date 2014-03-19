package sk.peterjurkovic.cpr.web.json.deserializers;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.expression.ParseException;

import sk.peterjurkovic.cpr.constants.Constants;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
	
	private DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.DATE_FORMAT);
	
	@Override
	public LocalDate deserialize(JsonParser json, DeserializationContext context) throws IOException, JsonProcessingException {
		try {
	 		if(StringUtils.isBlank(json.getText())){
	 			return null;
	 		}
            return formatter.parseLocalDate(json.getText());
        } catch (ParseException e) {
            return null;
        }
	}

}
