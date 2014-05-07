package cz.nlfnorm.mail;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class NlfnormMailSender extends JavaMailSenderImpl {
	
	private boolean sendEmails = true;
	
	
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
			super.send(mimeMessagePreparator);
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
