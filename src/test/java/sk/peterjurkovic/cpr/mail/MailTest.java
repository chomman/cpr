package sk.peterjurkovic.cpr.mail;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import sk.peterjurkovic.cpr.dao.AbstractTest;

public class MailTest extends AbstractTest {

	@Autowired
	private MailSender mailSender;
	
	
	@Test
	public void test(){
		mailSender.sendMail("Test", "petojurkovic@gmail.com", "Test", "Test msg");
	}
	
}
