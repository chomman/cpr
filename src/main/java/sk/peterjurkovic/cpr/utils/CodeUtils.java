package sk.peterjurkovic.cpr.utils;

import java.text.Normalizer;
import java.text.Normalizer.Form;

public class CodeUtils {

	
	public static String toSeoUrl(String string) {
	    return Normalizer.normalize(string.toLowerCase(), Form.NFD)
	        .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
	        .replaceAll("[^\\p{Alnum}]+", "-");
	}
	
	
}
