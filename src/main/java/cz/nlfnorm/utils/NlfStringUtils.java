package cz.nlfnorm.utils;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;

public class NlfStringUtils {
	
	
	public static final String getCroppedText(String text, final int maxLength){
		if(StringUtils.isBlank(text)){
			return "";
		}
		text = Jsoup.parse(text).text();
		return StringUtils.abbreviate(text, maxLength);
	}
	
	 public static String firstCharacterDown(String value){
	    	if(StringUtils.isBlank(value)){
	    		return "";
	    	}
	    	return Character.toLowerCase(value.charAt(0)) + value.substring(1); 
	    }
	
	 public static String insertCharatersAt(int pos, String str, String chars){
		 if(str != null && str.length() > pos && chars != null){
			 return new StringBuilder(str).insert(pos , chars).toString();
		 }
		 return str;
	 }
}
