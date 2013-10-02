package sk.peterjurkovic.cpr.utils;

import org.apache.commons.lang.math.NumberUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
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
    		if(value instanceof Integer){
    			return (Integer)value;
    		}
			return  NumberUtils.toInt( (String)value, 0);
		}
    	return 0;
    }
    
    public static Long parseLongFromStringObject(Object value){
    	if(value != null){
    		if(value instanceof Long){
    			return (Long)value;
    		}
			return  NumberUtils.toLong((String)value, 0);
		}
    	return 0L;
    }
    
    public static DateTime parseDateTimeFromStringObject(Object stringDateTime){
    	if(stringDateTime != null){
    		if(stringDateTime instanceof DateTime){
    			return (DateTime)stringDateTime;
    		}
    		if(stringDateTime instanceof String){
				try{
					DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.yyyy");
					return formatter.parseDateTime((String)stringDateTime);
				}catch(IllegalArgumentException ex){
					return null;
				}
    		}
		}
    	return null;
    }    
    
    public static LocalDate parseLocalDateFromStringObject(Object stringDateTime){
    	if(stringDateTime != null){
    		if(stringDateTime instanceof LocalDate){
    			return (LocalDate)stringDateTime;
    		}
    		if(stringDateTime instanceof String){
				try{
					DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.yyyy");
					return formatter.parseLocalDate((String)stringDateTime);
				}catch(IllegalArgumentException ex){
					return null;
				}
    		}
		}
    	return null;
    }    
    
    public static Boolean parseStringToBoolean(Object value){
    	if(value != null){
    		if(value instanceof Boolean){
    			return (Boolean)value;
    		}
    		String booleanValue = (String) value;
    		if(booleanValue.equals("1")){
    			return Boolean.TRUE;
    		}else if(booleanValue.equals("0")){
    			return Boolean.FALSE;
    		}
    	}
		return null;
    }
    
}
