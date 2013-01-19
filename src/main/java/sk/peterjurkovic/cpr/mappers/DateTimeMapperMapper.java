package sk.peterjurkovic.cpr.mappers;

import java.text.SimpleDateFormat;

import sk.peterjurkovic.cpr.constants.Constants;

import com.fasterxml.jackson.databind.ObjectMapper;


@SuppressWarnings("serial")
public class DateTimeMapperMapper extends ObjectMapper {
	
	public DateTimeMapperMapper(){
		 super.setDateFormat(new SimpleDateFormat(Constants.DATE_TIME_FORMAT));
	}
}
