package sk.peterjurkovic.cpr.utils;

import org.apache.commons.lang.math.NumberUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ParseUtils {
	
	
	/**
     * Parsuje z stringoveho retazca numericku hodnotu.
     * @param Object stringValue
     * @return int hodnota
     */
    public static int parseIntFromStringObject(Object value){
    	if(value != null){
			return  NumberUtils.toInt( (String)value, 0);
		}
    	return 0;
    }
    
    public static Long parseLongFromStringObject(Object value){
    	if(value != null){
			return  NumberUtils.toLong((String)value, 0);
		}
    	return 0L;
    }
    
    public static DateTime parseDateTimeFromStringObject(Object stringDateTime){
    	if(stringDateTime != null){
			try{
				DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.yyyy");
				return formatter.parseDateTime((String)stringDateTime);
			}catch(IllegalArgumentException ex){
				return null;
			}
		}
    	return null;
    }    
    
}
