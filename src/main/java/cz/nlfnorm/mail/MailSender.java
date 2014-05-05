package cz.nlfnorm.mail;

import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.commons.lang.StringUtils;
import org.codemonkey.simplejavamail.Email;
import org.codemonkey.simplejavamail.Mailer;
import org.codemonkey.simplejavamail.TransportStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cz.nlfnorm.constants.Constants;

@Component
public class MailSender {
	
	@Value("#{config.mail_username}")
	private String username;
	
	@Value("#{config.mail_password}")
	private String password;
	
	@Value("#{config.mail_smtp_auth}")
	private String auth;
	
	@Value("#{config.mail_smtp_host}")
	private String host;
	
	@Value("#{config.mail_smtp_port}")
	private String port;
	
	@Value("#{config.mail_use_ssl}")
	private String useSSL;
	
	
	private boolean isHtml  = true;
	
	
	public MailSender(){
		
	}
	
	public MailSender(Boolean isHtml){
		this.isHtml = isHtml;
	}
	
	public void sendMail(String recipientName, String emailAddress, String subject, String  message){
		if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)){
			final Email email = new Email();
			
			email.setFromAddress(Constants.SYSTEM_NAME, username);
			email.setSubject(subject);
			email.addRecipient(recipientName, emailAddress, RecipientType.TO);
			if(isHtml){
				email.setTextHTML(message);
			}else{
				email.setText(message);
			}
			if(useSSL.equals("true")){
				Mailer m = new Mailer(host, Integer.parseInt(port), username, password, TransportStrategy.SMTP_SSL);
				m.sendMail(email);
			}else{
				new Mailer(host, Integer.parseInt(port), username, password).sendMail(email);
			}
		}
	}

	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public boolean isHtml() {
		return isHtml;
	}

	public void setHtml(boolean isHtml) {
		this.isHtml = isHtml;
	}

	public String getUseSSL() {
		return useSSL;
	}

	public void setUseSSL(String userSSL) {
		this.useSSL = userSSL;
	}

	 

	
	
}
