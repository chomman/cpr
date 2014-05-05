package cz.nlfnorm.web.editors;

import java.beans.PropertyEditorSupport;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.utils.ParseUtils;


/**
 * Prekonvertuje textovy datumn na objekt DateTime
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Component
public class DateEditor extends PropertyEditorSupport {
	
	
	private DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.DATE_FORMAT);
	
	
	@Override
	public String getAsText() {
		DateTime date = (DateTime)getValue();
        return (date != null ? date.toString(formatter) : "");
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		setValue(ParseUtils.parseDateTimeFromStringObject(text));
	} 
}
