package sk.peterjurkovic.cpr.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import sk.peterjurkovic.cpr.context.ContextHolder;
import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.entities.WebpageContent;
import sk.peterjurkovic.cpr.enums.SystemLocale;
import sk.peterjurkovic.cpr.enums.WebpageType;

public class WebpageUtils {
	
	
	public static String getLocalizedValue(final String fieldName, final Webpage webpage){
		return getValueInLocale(fieldName, webpage, ContextHolder.getLocale());
	}
	
	
	public static String getValueInLocale(final String fieldName, final Webpage webpage, final Locale locale) throws  IllegalArgumentException{
		Validate.notNull(webpage);
		Validate.notNull(webpage.getLocalized());
		Validate.notEmpty(fieldName);
		
		WebpageContent webpageContent = webpage.getWebpageContentInLang(locale.getLanguage());
		
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
	
	
	public static String getUrlFor(final Webpage webpage, final String contextPath){
		Validate.notNull(webpage);
		Validate.notNull(webpage.getWebpageType());
		WebpageType type = webpage.getWebpageType();
		
		if(!type.equals(WebpageType.REDIRECT)){
			StringBuilder url = new StringBuilder();
			if(webpage.isHomepage()){
				url.append("/");
			}else if(webpage.getParent() == null){
				// {webpageCode}
				url.append(webpage.getCode());
			}else{
				// {topLevelParentCode}/{wepageId}/{webpageCode}
				url.append(getParentWebpageCode(webpage) )
				   .append("/")
				   .append(webpage.getId())
				   .append("/")
				   .append(webpage.getCode());
			}
			return contextPath + "/" + RequestUtils.buildUrl(url.toString());
		}
		
		if(webpage.getRedirectWebpage() != null){
			return getUrlFor(webpage.getRedirectWebpage(), contextPath);
		}else if(StringUtils.isNotBlank(webpage.getRedirectUrl())){
			return webpage.getRedirectUrl();
		}
		
		return "";
		
	}
	
		
	private static String getParentWebpageCode(Webpage webpage){
		if(webpage.getParent() != null){
			return getParentWebpageCode(webpage.getParent());
		}
		return webpage.getCode();
	}
	
	public static List<Webpage> getBreadcrumbFor(final Webpage webpage){
		Validate.notNull(webpage);
		List<Webpage> items = new LinkedList<Webpage>();
		buildBredcrumb( items , webpage);
		return items;
	}
	
	private static void buildBredcrumb(List<Webpage> breadcrumb, Webpage webpage) {
		if(webpage.getParent() != null){
			buildBredcrumb(breadcrumb, webpage.getParent());
		}else{
			if(webpage.getIsPublished()){
				breadcrumb.add(0, webpage);
			}
		}
	}
	
}
