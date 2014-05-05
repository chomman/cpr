package cz.nlfnorm.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class ValidationsUtils {
	
	private final static Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	
 
	public static boolean isEmailValid(final String email){
		if(StringUtils.isBlank(email)){
			return false;
		}
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
 
	
	
}
