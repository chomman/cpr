package sk.peterjurkovic.cpr.context;

import java.util.Locale;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.SimpleLocaleContext;
import org.springframework.core.NamedThreadLocal;
import sk.peterjurkovic.cpr.resolvers.LocaleResolver;



public class ContextHolder {
	
	private static final ThreadLocal<LocaleContext> localeContextHolder = new NamedThreadLocal<LocaleContext>("Locale context");
	
	 
    public static String getLang() {
        LocaleContext localeContext = localeContextHolder.get();
        if (localeContext == null) {
            return LocaleResolver.CODE_CZ;
        }
        return localeContext.getLocale().getLanguage();
    }
    
   
    public static boolean isDefaultLang() {
        if (getLang().equals(LocaleResolver.CODE_CZ)) {
            return true;
        }
        return false;
    }
    
    
    public static Locale getLocale() {
        LocaleContext localeContext = localeContextHolder.get();
        if (localeContext == null) {
            return new Locale(LocaleResolver.CODE_CZ);
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
