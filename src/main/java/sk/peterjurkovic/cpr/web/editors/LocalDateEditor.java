package sk.peterjurkovic.cpr.web.editors;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.utils.ParseUtils;

@Component
public class LocalDateEditor extends PropertyEditorSupport {

	
	private DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.DATE_FORMAT);
	
	
	@Override
	public String getAsText() {
		LocalDate date = (LocalDate)getValue();
        return (date != null ? date.toString(formatter) : "");
	}
	
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if(StringUtils.isNotBlank(text) && text.matches("\\d{2}\\.\\d{4}")){
			text = "01." + text;
		}
		setValue(ParseUtils.parseLocalDateFromStringObject(text));
	}
}
