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
	
}
