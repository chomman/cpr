package cz.nlfnorm.utils;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import cz.nlfnorm.constants.Constants;

public class DateTimeUtils {
	
	private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern(Constants.DATE_FORMAT);
	
	public static final int daysLeft(final LocalDate date){
		if(date == null){
			return 0;
		}		
		return Days.daysBetween(new LocalDate(), date).getDays(); 
	}
	
	
	/**
	 * Returns formated LocalDateTime in format dd.MM.yyyy {@link Constants.DATE_FORMAT }
	 * 
	 * @param dateTime
	 * @return string date in format dd.MM.yyyy
	 */
	public static final String getFormatedLocalDate(final LocalDateTime dateTime){
		if(dateTime == null){
			return "";
		}
		return getFormatedLocalDate(dateTime.toLocalDate());
	}
	
	
	/**
	 * Returns formated LocalDate in format dd.MM.yyyy {@link Constants.DATE_FORMAT }
	 * 
	 * @param dateTime
	 * @return string date in format dd.MM.yyyy
	 */
	public static final String getFormatedLocalDate(final LocalDate date){
		if(date == null){
			return null;
		}
		return date.toString(DATE_FORMATTER);
	}
}
