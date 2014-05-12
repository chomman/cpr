package cz.nlfnorm.utils;

import org.joda.time.Days;
import org.joda.time.LocalDate;

public class DateTimeUtils {
	
	public static final int daysLeft(final LocalDate date){
		if(date == null){
			return 0;
		}		
		return Days.daysBetween(new LocalDate(), date).getDays(); 
	}
}
