package sk.peterjurkovic.cpr.resolvers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import sk.peterjurkovic.cpr.context.ContextHolder;
import sk.peterjurkovic.cpr.utils.RequestUtils;

public class LocaleResolver implements org.springframework.web.servlet.LocaleResolver {
	
	public static final String CODE_EN = "en";
	public static final String CODE_CZ = "cs";


	public Locale resolve(HttpServletRequest request) {
	    String lang = RequestUtils.getPartOfURLOnPosition(request, 1);
	    if (StringUtils.isNotBlank(lang) && lang.equals(CODE_EN)) {
	        return new Locale(CODE_EN);
	    }
	    return new Locale(CODE_CZ);
	}
	
	public static String getDefaultLang() {
	    return CODE_CZ;
	}
	
	public static Locale getDefaultLocale() {
	    return new Locale(CODE_CZ);
	}
	
	
	public static List<String> getLanguageCodes() {
	    List<String> codes = new ArrayList<String>();
	    codes.add(CODE_CZ);
	    codes.add(CODE_EN);
	    return codes;
	}
	
	
	public static List<Locale> getAvailableLocales() {
	    List<Locale> locales = new ArrayList<Locale>();
	    locales.add(new Locale(CODE_CZ));
	    locales.add(new Locale(CODE_EN));
	    return locales;
	}
	
	
	public static boolean isAvailable(String localeCode) {
	    if (CODE_CZ.equals(localeCode) || CODE_EN.equals(localeCode)) {
	        return true;
	    }
	    return false;
	}

  
	@Override
	public Locale resolveLocale(HttpServletRequest request) {
	    return ContextHolder.getLocale();
	}

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		// TODO Auto-generated method stub
		
	}

	  
}
