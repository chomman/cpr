package sk.peterjurkovic.cpr.mail;

import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codemonkey.simplejavamail.Email;
import org.codemonkey.simplejavamail.Mailer;
import org.codemonkey.simplejavamail.TransportStrategy;

import sk.peterjurkovic.cpr.constants.Constants;

// @Component
public class MailSender {
	
	//@Value("#{config['mail.username']}")
	private String username = "";
	
	//@Value("#{config['mail.password']}")
	private String password = "";
	
	//@Value("#{config['mail.smtp_auth']}")
	private String auth = "true";
	
	//@Value("#{config['mail.smtp_host']}")
	private String host = "smtp.googlemail.com";
	
	//@Value("#{config['mail.smtp_port']}")
	private String port = "465";
	
	//@Value("#(config['mail.use_ssl'])")
	private String userSSL = "true";
	
	
	private boolean isHtml  = true;
	
	private Logger logger =  Logger.getLogger(getClass());
	public MailSender(){
		
	}
	
	public MailSender(Boolean isHtml){
		this.isHtml = isHtml;
	}
	
	public void sendMail(String recipientName, String emailAddress, String subject, String  message){
		if(StringUtils.isNotBlank(username)){
			final Email email = new Email();
			
			email.setFromAddress(Constants.SYSTEM_NAME, username);
			email.setSubject(subject);
			email.addRecipient(recipientName, emailAddress, RecipientType.TO);
			if(isHtml){
				email.setTextHTML(message);
			}else{
				email.setText(message);
			}
			logger.info("Message: "+ message);
			
			logger.info(userSSL);
			logger.info(username +" ,"+password+" ,"+host);
			if(userSSL.equals("true")){
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

	public String getUserSSL() {
		return userSSL;
	}

	public void setUserSSL(String userSSL) {
		this.userSSL = userSSL;
	}

	 

	
	
}
