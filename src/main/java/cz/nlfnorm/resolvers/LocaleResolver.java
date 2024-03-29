package cz.nlfnorm.resolvers;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.context.ContextHolder;
import cz.nlfnorm.enums.SystemLocale;
import cz.nlfnorm.utils.RequestUtils;

public class LocaleResolver implements org.springframework.web.servlet.LocaleResolver {
	
	
	public static final String CODE_EN = "en";
	public static final String CODE_CZ = "cs";


	public Locale resolve(HttpServletRequest request) {
	    final String lang = RequestUtils.getPartOfURLOnPosition(request, 1);
	    if (StringUtils.isNotBlank(lang)) {
	    	if(lang.equals(SystemLocale.EN.getCode())){
	    		return SystemLocale.getEnglishLocale();
	    	}
	    	if(lang.endsWith(Constants.ADMIN_PREFIX)){
	    		final String adminPart = RequestUtils.getPartOfURLOnPosition(request, 2);
	    		if(StringUtils.isNotBlank(adminPart) && adminPart.endsWith(Constants.QUASAR_PREFIX)){
	    			return SystemLocale.getEnglishLocale();
	    		}
	    	}
	    }
	    return SystemLocale.getCzechLocale();
	}
	
	public static String getDefaultLang() {
	    return SystemLocale.getDefaultLanguage();
	}
	
	public static Locale getDefaultLocale() {
	    return SystemLocale.getCzechLocale();
	}
	
	
	public static List<String> getLanguageCodes() {
	   return SystemLocale.getAllCodes();
	}
	
	
	public static List<Locale> getAvailableLocales() {
	    return SystemLocale.getAllLocales();
	}
	
	
	public static boolean isAvailable(String localeCode) {
	    return SystemLocale.isAvaiable(localeCode);
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
