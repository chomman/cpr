package cz.nlfnorm.web.editors;

import java.beans.PropertyEditorSupport;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import cz.nlfnorm.constants.Constants;

@Component
public class LocalDateTimeEditor  extends PropertyEditorSupport {

	private DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.DATE_TIME_FORMAT);
	
	
	@Override
	public String getAsText() {
		LocalDateTime dateTime = (LocalDateTime)getValue();
        return (dateTime != null ? dateTime.toString(formatter) : "");
	}
		
}
