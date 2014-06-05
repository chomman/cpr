package cz.nlfnorm.config.mail;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import junit.framework.Assert;

import org.apache.commons.lang.Validate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import cz.nlfnorm.config.TestConfig;
import cz.nlfnorm.mail.HtmlMailMessage;
import cz.nlfnorm.mail.NlfnormMailSender;

public class MailTest extends TestConfig {


	@Autowired
	private NlfnormMailSender nlfnormMailSender;
	
	@Value("${mail.developer}")
	private String developerEmail;
	
	@Test
	public void htmlMailMessageTest(){
		Map<String, Object> context = new HashMap<String, Object>();
		String replaceVal = "SUCCESS!";
		context.put("test", replaceVal);
		String htmlMessage =  "test ${test}";
		HtmlMailMessage message = new HtmlMailMessage("test", " subject", htmlMessage, context);
		try {
			Assert.assertNotNull(message.createMessage());
		} catch (MessagingException e) {
		
			e.printStackTrace();
		}
		Assert.assertEquals(htmlMessage.replace("${test}", replaceVal), message.getHtmlContent());
		
	}
	
	
	@Test
	public void sendEmailTest(){
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("firstName", "Peter");
		context.put("lastName", "Jurkovic");
		String htmlMessage =  "Hello ${firstName} ${lastName} ! this si a test email";
		HtmlMailMessage message = new HtmlMailMessage("info@nlfnorm.cz", " jUnit email test", htmlMessage, context);
		Validate.notNull(message);
		message.addRecipientTo(developerEmail);
		nlfnormMailSender.send(message);
	}
	
}
