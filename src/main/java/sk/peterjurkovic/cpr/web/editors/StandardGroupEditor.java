package sk.peterjurkovic.cpr.web.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sk.peterjurkovic.cpr.services.StandardGroupService;

/**
 * Prekonvertuje ID skupinny vyrobkov v textovej podobe na objekt
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Component
public class StandardGroupEditor extends PropertyEditorSupport {

	@Autowired
	private StandardGroupService standardGroupService;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Long id = null;
		try {
            id = Long.parseLong(text);
        } catch (NumberFormatException nfe) {
            setValue(null);
            return;
        }
		setValue(standardGroupService.getStandardGroupByid(id));
	} 
}
