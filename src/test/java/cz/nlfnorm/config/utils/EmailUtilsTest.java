package cz.nlfnorm.config.utils;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import cz.nlfnorm.utils.EmailUtils;

public class EmailUtilsTest {
	
	@Test
	public void splitEmailsTest(){
		
		String emails = "cpk@azet.sk, petojurkovic@gmail.com";
		
		List<String> emailList = EmailUtils.sprintEmails(emails);
		
		Assert.assertEquals(2, emailList.size());
		emailList.remove(0);
		Assert.assertEquals(1, emailList.size());
	}
}
