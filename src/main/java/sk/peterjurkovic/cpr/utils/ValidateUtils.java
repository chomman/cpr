package sk.peterjurkovic.cpr.utils;

import org.apache.commons.validator.UrlValidator;


public class ValidateUtils {

	
	public static boolean isValidHttpUrl(String url) {
	    String[] schemes = {"http", "https"};
	    UrlValidator urlValidator = new UrlValidator(schemes);
	    if (urlValidator.isValid(url)) {
	        return true;
	    }

	    return false;
	}
}
