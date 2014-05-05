package cz.nlfnorm.web.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.nlfnorm.services.CountryService;

/**
 * Prekonvertuje itentifikator krajiny v textovej podobne do objektu.
 * 
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Component
public class CountryEditor extends PropertyEditorSupport {
	
	@Autowired
	private  CountryService countryService;
	 
	/**
	 * @param String id krajiny
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		
		Long id = null;
		try {
            id = Long.parseLong(text);
        } catch (NumberFormatException nfe) {
            setValue(null);
            return;
        }
        setValue(countryService.getCountryById(id));

	}
	
}
