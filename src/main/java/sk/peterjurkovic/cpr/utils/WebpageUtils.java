package sk.peterjurkovic.cpr.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.Validate;

import sk.peterjurkovic.cpr.context.ContextHolder;
import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.entities.WebpageContent;
import sk.peterjurkovic.cpr.enums.SystemLocale;

public class WebpageUtils {
	
	
	public static String getLocalizedValue(final String fieldName, final Webpage webpage){
		return getValueInLocale(fieldName, webpage, ContextHolder.getLocale());
	}
	
	
	public static String getValueInLocale(final String fieldName, final Webpage webpage, final Locale locale) throws  IllegalArgumentException{
		Validate.notNull(webpage);
		Validate.notNull(webpage.getLocalized());
		Validate.notEmpty(fieldName);
		
		WebpageContent webpageContent = getWebpageContent(webpage, locale);
		
		Method method;
		Object value = null;
		try {
			method = webpageContent.getClass().getMethod( "get" + CodeUtils.firstCharacterUp(fieldName)  );
			value = method.invoke(webpageContent);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new IllegalArgumentException("Method: " + 
					CodeUtils.firstCharacterUp(fieldName) + " was not found in Webpage ID: " + 
					webpage.getId());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new IllegalArgumentException(e.getMessage(), e.getCause());
		}
		
		if(value == null){
			return "";
		}
		
		if(!(value instanceof String)){
			throw new IllegalArgumentException("Returned value of get" + 
						CodeUtils.firstCharacterUp(fieldName) + " in NOT type of String. Webpage ID: " + 
						webpage.getId());
		}
		return (String) value;
	}
	
	
	
	private static WebpageContent getWebpageContent(Webpage webpage, Locale locale){
		WebpageContent webpageContent = webpage.getLocalized().get(locale);
		if(webpageContent == null){
			webpageContent = webpage.getDefaultWebpageContent();
		}
		
		if(webpageContent == null){
			throw new IllegalArgumentException(
						String.format("Webpage [id=%s] has not defined webpageContent", webpage.getId())
					);
		}
		
		return webpageContent;
	}
	
	public static List<String> getNotUsedLocales(final Webpage webpage) {
		Validate.notNull(webpage);
		Validate.notNull(webpage.getLocalized());
		List<String> notUsedLocales = new ArrayList<String>();
		Set<String> usedLocales = getUsedLocaleCodes(webpage);
		List<String> avaiableLocales = SystemLocale.getAllCodes();
		for (String locale : avaiableLocales) {
		  if(!usedLocales.contains(locale)){
			  notUsedLocales.add(locale);
		  }
		}
		return notUsedLocales;
	}
	
	public static Set<String> getUsedLocaleCodes(final Webpage webpage) {
		Validate.notNull(webpage);
		Validate.notNull(webpage.getLocalized());
		Set<String> locales = new HashSet<String>();
		for (Map.Entry<String,WebpageContent> entry : webpage.getLocalized().entrySet()) {
			locales.add(entry.getKey());
		}
		return locales;
	}
	
}
