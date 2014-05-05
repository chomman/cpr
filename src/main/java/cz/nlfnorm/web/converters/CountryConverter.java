package cz.nlfnorm.web.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import cz.nlfnorm.entities.Country;
import cz.nlfnorm.services.CountryService;


/**
 * Prekonvertuje ID krajiny vo formate String na object Country
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 */
@Component("countryConverter")
public class CountryConverter implements Converter<String, Country> {
	
	@Autowired
	private CountryService countryService;
	
	@Override
	public Country convert(String countryIdString) {	
		Long id;
        try {
            id = Long.valueOf(countryIdString);
        } catch (NumberFormatException nfe) {
            return null;
        }

		return countryService.getCountryById(id);
	}

}
