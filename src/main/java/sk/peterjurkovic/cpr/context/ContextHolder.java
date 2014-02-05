package sk.peterjurkovic.cpr.context;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.SimpleLocaleContext;
import org.springframework.core.NamedThreadLocal;

import sk.peterjurkovic.cpr.enums.SystemLocale;



public class ContextHolder {
	
	private static final ThreadLocal<LocaleContext> localeContextHolder = new NamedThreadLocal<LocaleContext>("Locale context");
	
	 
    public static String getLang() {
        LocaleContext localeContext = localeContextHolder.get();
        if (localeContext == null) {
            return SystemLocale.getDefaultLanguage();
        }
        return localeContext.getLocale().getLanguage();
    }
    
    public static String getLangName(){
    	return SystemLocale.getNameByLang(getLang());
    }
   
    public static boolean isDefaultLang() {
       return SystemLocale.isDefault(getLang());
    }
    
    
    public static Locale getLocale() {
        LocaleContext localeContext = localeContextHolder.get();
        if (localeContext == null) {
            return SystemLocale.getCzechLocale();
        }
        return localeContext.getLocale();
    }
   
    public static void setLocale(Locale locale) {
        LocaleContext localeContext = (locale != null ? new SimpleLocaleContext(locale) : null);
        localeContextHolder.set(localeContext);
    }
    
    
    public static void resetContext() {
        localeContextHolder.remove();
    }
}
