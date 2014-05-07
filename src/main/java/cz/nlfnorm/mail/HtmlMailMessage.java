package cz.nlfnorm.mail;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;
import org.springframework.mail.MailPreparationException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class HtmlMailMessage extends MimeMessagePreparatorImpl {
	
	private Configuration configuration = null;
	private String htmlContent = "";
	
	
	public HtmlMailMessage(final String from, final String subject, String htmlContent, final Map<String, Object> context){
		super(from, subject, context);
		this.htmlContent = htmlContent;
	}
	
	
	public HtmlMailMessage(String from, String subject, String htmlContent) {
		super(from, subject);
		this.htmlContent = htmlContent;
	}

	
	public HtmlMailMessage(String from, String subject) {
		super(from, subject);
	}


	public String getHtmlContent() {
		return htmlContent;
	}


	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}


	@Override
	public BodyPart createMessage() throws MessagingException {
		Multipart finalHtmlContent = new MimeMultipart("related");
		BodyPart htmlPage = new MimeBodyPart();
		try {
			if(getContext() != null && StringUtils.isNotBlank(htmlContent)){
				 try {
					 StringWriter htmlWriter = new StringWriter();
					 final Template htmlTemplate = getFreemakerTemplate();
					 htmlTemplate.process(getContext(), htmlWriter);
					 htmlContent = htmlWriter.toString();
				 } catch (TemplateException | IOException e) {
					 throw new MailPreparationException("Can't generate HTML subscription mail", e);
			     }
			}
			htmlPage.setDataHandler(createDataHandler(htmlContent.getBytes("UTF-8"), "text/html"));
		} catch (UnsupportedEncodingException e) {
			logger.warn("Email preparation failed." , e);
		}
		finalHtmlContent.addBodyPart(htmlPage);
		BodyPart htmlPart = new MimeBodyPart();
		htmlPart.setContent(finalHtmlContent);
		return htmlPart;
	}
	
	private Template getFreemakerTemplate() throws IOException{
		return new Template("demand", new StringReader(htmlContent), configuration);
	}


	public Configuration getConfiguration() {
		return configuration;
	}


	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	
	
}
