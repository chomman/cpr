package sk.peterjurkovic.cpr.web.editors;

import java.beans.PropertyEditorSupport;
import org.springframework.stereotype.Component;
import sk.peterjurkovic.cpr.utils.ParseUtils;


/**
 * Prekonvertuje textovy datumn na objekt DateTime
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Component
public class DateTimeEditor extends PropertyEditorSupport {
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		setValue(ParseUtils.parseDateTimeFromStringObject(text));
	} 
}
