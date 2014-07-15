package cz.nlfnorm.mail;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.mail.javamail.MimeMessagePreparator;

import cz.nlfnorm.dto.ByteFileDto;
import cz.nlfnorm.utils.ValidationsUtils;

public abstract class MimeMessagePreparatorImpl implements MimeMessagePreparator {
	
	protected static final Logger logger = Logger.getLogger(MimeMessagePreparatorImpl.class);
	
	private List<String> to = new ArrayList<String>();
	private List<String> cc = new ArrayList<String>();
	private List<String> bcc = new ArrayList<String>();
	private Map<String, Object> context = new HashMap<String, Object>();
	private String from = "";
	private String subject = "";
	private List<String> fileNames = new ArrayList<String>();
	private List<ByteFileDto> files = new ArrayList<ByteFileDto>();
	
	
	
	public MimeMessagePreparatorImpl(final String from, final String subject, final Map<String, Object> context){
		this(from, subject);
		this.context = context;
	}
	
	public MimeMessagePreparatorImpl(final String from, final String subject){
		this.from = from;
		this.subject = subject;
	}
	
	
	@Override
	public void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMultipart mpRoot = new MimeMultipart("mixed");
        Multipart mp = new MimeMultipart("alternative");
        MimeBodyPart contentPartRoot = new MimeBodyPart();
        contentPartRoot.setContent(mp);
        mpRoot.addBodyPart(contentPartRoot);
        
        if(to == null || to.size() == 0){
        	logger.warn("E-mail was not send, recipientTo is empty.");
        	return;
        }
        
        for (String emailTo : to) {
        	mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
        }

        for (String emailCc : cc) {
        	mimeMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(emailCc));
        }

        for (String emailBcc : bcc) {
        	mimeMessage.addRecipient(Message.RecipientType.BCC, new InternetAddress(emailBcc));
        }
        
        // adding from and subject
        mimeMessage.setFrom(new InternetAddress(getFrom()));
        mimeMessage.setSubject(getSubject());
        mp.addBodyPart(createMessage());

        // Create an "ATTACHMENT" - we must put it to mpRoot(mixed content)
        for (final String filename : getFileNames()) {
        	if(StringUtils.isNotBlank(filename)){
        		File file = new File(filename);
        		if(file.exists()){
        			mpRoot.addBodyPart(createAttachment(file));
        		}
        	}
        }
        
        for (final ByteFileDto file : files) {
    		mpRoot.addBodyPart(createAttachment(file));
        }
      
        mimeMessage.setContent(mpRoot);
        logger.debug("Message is prepared to send");
    }

	public abstract BodyPart createMessage() throws MessagingException;
	
	
	private BodyPart createAttachment(final File file) throws Exception {
		BodyPart attachBodypart = new MimeBodyPart();
		FileDataSource fds = new FileDataSource(file);
		DataHandler dh = new DataHandler(fds);
		attachBodypart.setFileName(file.getName());
		attachBodypart.setDisposition(Part.ATTACHMENT);
		attachBodypart.setDescription("Attached file: " + file.getName());
		attachBodypart.setDataHandler(dh);
		logger.info("ATTACHMENT ADDED ; filename: " + file.getName());
		return attachBodypart;
	}
	 
	private BodyPart createAttachment(final ByteFileDto file) throws Exception {
		BodyPart attachBodypart = new MimeBodyPart();
		DataHandler dh = createDataHandler(file.getData(), file.getContentType());
		attachBodypart.setFileName(file.getName());
		attachBodypart.setDisposition(Part.ATTACHMENT);
		attachBodypart.setDescription("Attached file: " + file.getName());
		attachBodypart.setDataHandler(dh);
		logger.info("ATTACHMENT ADDED ; filename: " + file.getName());
		return attachBodypart;
	}
	
	 protected DataHandler createDataHandler(final byte[] stringBytes, final String contentType) {
        return new DataHandler(new DataSource() {

            public InputStream getInputStream() throws IOException {
                return new BufferedInputStream(new ByteArrayInputStream(stringBytes));
            }
            public OutputStream getOutputStream() throws IOException {
                throw new IOException("Read-only data");
            }
            public String getContentType() {
                return contentType;
            }
            public String getName() {
                return "main";
            }
        });
    }
	 
	private boolean isValidEmailAddres(final String email){
		if(ValidationsUtils.isEmailValid(email)){
			return true;
		}
		return false;
	}
	
	public Map<String, Object> getContext() {
		return context;
	}

	public void setContext(Map<String, Object> context) {
		this.context = context;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<String> getFileNames() {
		return fileNames;
	}

	public void setFileNames(List<String> fileNames) {
		this.fileNames = fileNames;
	}
	
	public void addRecipientTo(final String emailAddress){
		addEmail(to, emailAddress);
	}
	
	public void addRecipientTo(final List<String> emailAddresses){
		if(emailAddresses != null){
			for(String email : emailAddresses){
				addEmail(to, email);
			}
		}
	}
	
	public void addRecipientCc(final String emailAddress){
		addEmail(cc, emailAddress);
	}
	
	public void addRecipientBcc(final String emailAddress){
		addEmail(bcc, emailAddress);
	}
	
	public void addRecipientBcc(final List<String> emailAddresses){
		if(emailAddresses != null){
			for(String email : emailAddresses){
				addEmail(bcc, email);
			}
		}
	}
	
	public void addAttachment(final String fileLocation) {
        this.fileNames.add(fileLocation);
    }
	
	public void addAttachment(final ByteFileDto file) {
		if(file != null){
			this.files.add(file);
		}
    }
	
	private void addEmail(List<String> list, String email){
		if(isValidEmailAddres(email)){
			list.add(email);
		}else{
			logger.warn("Invalid email address: " + email);
		}
	}

	@Override
	public String toString() {
		String emails = "";
		for(String e : to){
			emails += e+",";
		}
		return "[to=" + emails + ", subject=" + subject+ "]";
	}
	
	
}
