package cz.nlfnorm.mail;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class NlfnormMailSender extends JavaMailSenderImpl {
	
	@Value("#{config['mail.sendEmails']}")
	private boolean sendEmails;
	
	private final Logger logger = Logger.getLogger(getClass());
	
	
	@Override
	public void send(MimeMessage mimeMessage) throws MailException {
		if(sendEmails){
			super.send(mimeMessage);
		}
	}
	
	@Override
	public void send(MimeMessage[] mimeMessages) throws MailException {
		if(sendEmails){
			super.send(mimeMessages);
		}
	}

	@Override
	public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
		if(sendEmails){
			try{
				super.send(mimeMessagePreparator);
			}catch(Exception e){
				String info = ((MimeMessagePreparatorImpl)mimeMessagePreparator).toString();
				logger.warn("Nepodarilo sa odoslat email na adresy: " + info , e);
			}
		}
	}
	
	@Override
	public void send(MimeMessagePreparator[] mimeMessagePreparators) throws MailException {
		if(sendEmails){
			super.send(mimeMessagePreparators);
		}
	}
	
	

	public boolean getSendEmails() {
		return sendEmails;
	}


	public void setSendEmails(boolean sendEmails) {
		this.sendEmails = sendEmails;
	}
	
	
}
