package cz.nlfnorm.web.json.deserializers;

import java.io.IOException;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import cz.nlfnorm.constants.Constants;

public class DateSerializer extends JsonSerializer<LocalDate> {
	
	private DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.DATE_FORMAT);
	
	@Override
	public void serialize(LocalDate object, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException, JsonProcessingException {
			if(object == null || !(object instanceof LocalDate)){
				jsonGenerator.writeString("");
			}else{
				LocalDate date = (LocalDate)object;
		        jsonGenerator.writeString(date.toString(formatter));
			}
	}


}
