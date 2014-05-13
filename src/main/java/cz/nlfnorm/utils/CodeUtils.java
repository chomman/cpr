package cz.nlfnorm.utils;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
	
	
	 private static Map<Character, Character> charMap = getCharMap('-');

	    /**
	     * Metoda generuje nazvy souboru bez znaku s diakritikou apod
	     */
	    public static String generateProperFilename(String codeName) {
	        if (codeName == null) {
	            return null;
	        }
	        codeName = StringUtils.lowerCase(codeName);

	        StringBuffer sBuilder = new StringBuffer(codeName);
	        StringBuffer newCodeNameBuffer = new StringBuffer();

	        for (int i = 0; i < sBuilder.length(); i++) {
	            char currentChar = sBuilder.charAt(i);
	            Character currentCharacter = Character.valueOf(currentChar);
	            if ((currentChar > 96 && currentChar < 124) || (currentChar > 47 && currentChar < 58)) {
	                newCodeNameBuffer.append(currentCharacter);
	            } else if (currentChar == '.') {
	                newCodeNameBuffer.append(currentChar);
	            } else if (charMap.containsKey(currentCharacter)) {
	                newCodeNameBuffer.append(charMap.get(currentCharacter));
	            }
	        }
	        if (newCodeNameBuffer.length() == 0) {
	            return "name";
	        }
	        return newCodeNameBuffer.toString();
	    }
	    
	    
	    private static Map<Character, Character> getCharMap(Character dividingSymbol) {

	        Map<Character, Character> charMap = new HashMap<Character, Character>();
	        charMap.put(Character.valueOf('\u00C1'), Character.valueOf('a'));
	        charMap.put(Character.valueOf('\u00E1'), Character.valueOf('a'));
	        charMap.put(Character.valueOf('\u010C'), Character.valueOf('c'));
	        charMap.put(Character.valueOf('\u010D'), Character.valueOf('c'));
	        charMap.put(Character.valueOf('\u010E'), Character.valueOf('d'));
	        charMap.put(Character.valueOf('\u010F'), Character.valueOf('d'));
	        charMap.put(Character.valueOf('\u00C9'), Character.valueOf('e'));
	        charMap.put(Character.valueOf('\u00E9'), Character.valueOf('e'));
	        charMap.put(Character.valueOf('\u011A'), Character.valueOf('e'));
	        charMap.put(Character.valueOf('\u011B'), Character.valueOf('e'));
	        charMap.put(Character.valueOf('\u00CD'), Character.valueOf('i'));
	        charMap.put(Character.valueOf('\u00ED'), Character.valueOf('i'));
	        charMap.put(Character.valueOf('\u0147'), Character.valueOf('n'));
	        charMap.put(Character.valueOf('\u0148'), Character.valueOf('n'));
	        charMap.put(Character.valueOf('\u00D3'), Character.valueOf('o'));
	        charMap.put(Character.valueOf('\u00F3'), Character.valueOf('o'));
	        charMap.put(Character.valueOf('\u0158'), Character.valueOf('r'));
	        charMap.put(Character.valueOf('\u0159'), Character.valueOf('r'));
	        charMap.put(Character.valueOf('\u0160'), Character.valueOf('s'));
	        charMap.put(Character.valueOf('\u0161'), Character.valueOf('s'));
	        charMap.put(Character.valueOf('\u0164'), Character.valueOf('t'));
	        charMap.put(Character.valueOf('\u0165'), Character.valueOf('t'));
	        charMap.put(Character.valueOf('\u00DA'), Character.valueOf('u'));
	        charMap.put(Character.valueOf('\u00FA'), Character.valueOf('u'));
	        charMap.put(Character.valueOf('\u016E'), Character.valueOf('u'));
	        charMap.put(Character.valueOf('\u016F'), Character.valueOf('u'));
	        charMap.put(Character.valueOf('\u00DD'), Character.valueOf('y'));
	        charMap.put(Character.valueOf('\u00FD'), Character.valueOf('y'));
	        charMap.put(Character.valueOf('\u017D'), Character.valueOf('z'));
	        charMap.put(Character.valueOf('\u017E'), Character.valueOf('z'));
	        charMap.put(Character.valueOf(' '), dividingSymbol);
	        charMap.put(Character.valueOf('/'), dividingSymbol);
	        charMap.put(Character.valueOf('\\'), dividingSymbol);
	        charMap.put(Character.valueOf(','), dividingSymbol);
	        charMap.put(Character.valueOf('.'), dividingSymbol);
	        charMap.put(Character.valueOf('!'), dividingSymbol);
	        charMap.put(Character.valueOf('>'), dividingSymbol);
	        charMap.put(Character.valueOf('<'), dividingSymbol);
	        charMap.put(Character.valueOf('?'), dividingSymbol);
	        charMap.put(Character.valueOf(':'), dividingSymbol);
	        charMap.put(Character.valueOf(';'), dividingSymbol);
	        charMap.put(Character.valueOf('+'), dividingSymbol);
	        charMap.put(Character.valueOf('@'), dividingSymbol);
	        charMap.put(Character.valueOf('#'), dividingSymbol);
	        charMap.put(Character.valueOf('$'), dividingSymbol);
	        charMap.put(Character.valueOf('%'), dividingSymbol);
	        charMap.put(Character.valueOf('^'), dividingSymbol);
	        charMap.put(Character.valueOf('&'), dividingSymbol);
	        charMap.put(Character.valueOf('*'), dividingSymbol);
	        charMap.put(Character.valueOf('('), dividingSymbol);
	        charMap.put(Character.valueOf(')'), dividingSymbol);
	        charMap.put(Character.valueOf('"'), dividingSymbol);

	        return charMap;
	    }
	    
	    
	    public static String firstCharacterUp(String value){
	    	if(StringUtils.isBlank(value)){
	    		return "";
	    	}
	    	return Character.toUpperCase(value.charAt(0)) + value.substring(1); 
	    }
	
	
}
