package cz.nlfnorm.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class EmailUtils {
	
	private final static String EMAIL_SEPARATOR = ",";
	
	public static List<String> sprintEmails(final String emails){
		List<String> emailList = new ArrayList<String>();
		if(StringUtils.isNotBlank(emails)){
			String[] splitedEmails = emails.split(EMAIL_SEPARATOR);
			if(splitedEmails.length > 0){
				for(int i = 0; i < splitedEmails.length ; i++){
					if(ValidationsUtils.isEmailValid(StringUtils.trim(splitedEmails[i]))){
						emailList.add(StringUtils.trim(splitedEmails[i]));
					}
				}
			}
		}
		return emailList;
	}
	
}
