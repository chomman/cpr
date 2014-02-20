package sk.peterjurkovic.cpr.utils;

import org.joda.time.LocalDate;

public class CommonUtils {
	
	public static boolean isObjectNew(LocalDate date){
		if(date == null){
			return false;
		}
		LocalDate thresholdDate = new LocalDate().withDayOfMonth(1);
		if(thresholdDate.isAfter(date)){
			return false;
		}
		return true;
	}
	
	
}
