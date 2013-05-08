package sk.peterjurkovic.cpr.utils;

import java.text.Normalizer;
import java.text.Normalizer.Form;
/**
 * Utilita pre pracu s kodom
 * 
 * @author peto
 *
 */
public class CodeUtils {

	/**
	 * Z daneho nazvu extrahuje vsetky specialne znaky a znaky s diakritikou
	 * 
	 * @param nazov
	 * @return SEO url
	 */
	public static String toSeoUrl(String string) {
	    return Normalizer.normalize(string.toLowerCase(), Form.NFD)
	        .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
	        .replaceAll("[^\\p{Alnum}]+", "-");
	}
	
	
}
