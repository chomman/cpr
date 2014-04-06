package sk.peterjurkovic.cpr.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public enum SystemLocale {
	
	CZ("cs", "Czech"),
	EN("en", "English");
	
	private String code;
	private String langName;
	
	private SystemLocale(String code, String name){
		this.code = code;
		this.langName = name;
	}
	
	public String getCode() {
		return code;
	}
	public String getLangName() {
		return langName;
	}
	
	public static Locale getDefault(){
		return getCzechLocale();
	}
	
	public static boolean isDefault(String code){
		if(code.equals(CZ.getCode())){
			return true;
		}
		return false;
	}
	
	public static String getDefaultLanguage(){
		return CZ.getCode();
	}
	
	public static Locale getCzechLocale(){
		return new Locale(SystemLocale.CZ.getCode());
	}
	
	public static Locale getEnglishLocale(){
		return new Locale(SystemLocale.EN.getCode());
	}
	
	public static List<SystemLocale> getAll() {
        return Arrays.asList(values());
    }
	
	public static String getNameByLang(String lang){
		for(SystemLocale sl : getAll()){
			if(lang.equals(sl.getCode())){
				return sl.getLangName();
			}
		}
		return "";
	}
	
	public static List<String> getAllCodes(){
		List<String> codes = new ArrayList<String>();
	    codes.add(CZ.getCode());
	    codes.add(EN.getCode());
	    return codes;
	}
	
	public static boolean isAvaiable(String localeCode){
		if (EN.getCode().equals(localeCode) || CZ.getCode().equals(localeCode)) {
			return true;
		}
		return false;
	}
	
	public static boolean isNotAvaiable(String localeCode){
		return !isAvaiable(localeCode);
	}
	
	public static List<Locale> getAllLocales() {
		 List<Locale> locales = new ArrayList<Locale>();
		    locales.add(new Locale(CZ.getCode()));
		    locales.add(new Locale(EN.getCode()));
	    return locales;
    }
}
