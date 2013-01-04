package sk.peterjurkovic.cpr.web.editors;

import java.beans.PropertyEditorSupport;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sk.peterjurkovic.cpr.services.CountryService;

@Component
public class CountryEditor extends PropertyEditorSupport {
	
	@Autowired
	private  CountryService countryService;
	 
	private Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		
		logger.info("Convertin " + text);
		
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
