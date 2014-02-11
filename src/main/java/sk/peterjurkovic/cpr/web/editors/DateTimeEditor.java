package sk.peterjurkovic.cpr.web.editors;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import sk.peterjurkovic.cpr.constants.Constants;

@Component
public class DateTimeEditor extends PropertyEditorSupport {
	public DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.DATE_TIME_FORMAT);
		
	@Override
	public String getAsText() {
		DateTime date = (DateTime)getValue();
        return (date != null ? date.toString(formatter) : "");
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if(StringUtils.isNotBlank(text)){
			try{
				DateTime datetime = formatter.parseDateTime(text);
				setValue(datetime);
			}catch(IllegalArgumentException ex){
				setValue(null);
			}
		}
		setValue(null);
	} 
}
