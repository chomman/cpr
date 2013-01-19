package sk.peterjurkovic.cpr.web.editors;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import sk.peterjurkovic.cpr.constants.Constants;

@Component
public class DateTimeEditor extends PropertyEditorSupport {
	private DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.DATE_TIME_FORMAT);
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Override
	public String getAsText() {
		DateTime date = (DateTime)getValue();
		if(date != null)
			logger.info("getAsText.. "+ date.toString());
        return (date != null ? date.toString(formatter) : "");
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if(StringUtils.isNotBlank(text)){
			try{
				logger.info("setAsText.. "+ text);
				DateTime datetime = formatter.parseDateTime(text);
				setValue(datetime);
			}catch(IllegalArgumentException ex){
				setValue(null);
			}
		}
		setValue(null);
	} 
}
