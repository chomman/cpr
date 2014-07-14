package cz.nlfnorm.quasar.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;

import cz.nlfnorm.entities.EmailTemplate;
import cz.nlfnorm.mail.HtmlMailMessage;
import cz.nlfnorm.mail.NlfnormMailSender;
import cz.nlfnorm.quasar.entities.AbstractLog;
import cz.nlfnorm.quasar.entities.AuditLog;
import cz.nlfnorm.quasar.entities.Comment;
import cz.nlfnorm.quasar.entities.QuasarSettings;
import cz.nlfnorm.quasar.enums.LogStatus;
import cz.nlfnorm.quasar.security.AccessUtils;
import cz.nlfnorm.quasar.services.LogService;
import cz.nlfnorm.quasar.services.QuasarSettingsService;
import cz.nlfnorm.quasar.web.controllers.AuditLogController;
import cz.nlfnorm.quasar.web.forms.ChangeLogStatusForm;
import cz.nlfnorm.services.EmailTemplateService;
import cz.nlfnorm.utils.UserUtils;

public class LogServiceImpl implements LogService {
	
	
	@Value("#{config['host']}")
	private String host;
	
	@Autowired
	protected EmailTemplateService emailTemplateService;
	@Autowired 
	protected QuasarSettingsService quasarSettingsService;
	@Autowired
	protected NlfnormMailSender nlfnormMailSender;
	
	@Override
	public void setPendingStatus(AbstractLog log) {
		AccessUtils.validateAuthorizationFor(log);
		log.setStatus(LogStatus.PENDING);
	}

	@Override
	public void setRfusedStatus(AbstractLog log) {
		AccessUtils.validateAuthorizationFor(log);
		log.setStatus(LogStatus.REFUSED);
		
	}

	@Override
	public void setApprovedStatus(AbstractLog log) {
		if(!UserUtils.getLoggedUser().isQuasarAdmin()){
			throw new AccessDeniedException("Access denied: " + UserUtils.getLoggedUser());
		}
		log.setStatus(LogStatus.APPROVED);
	}

	@Override
	public void setLogStatus(final LogStatus newStatus, final AbstractLog log, final String withComment) {
		Validate.notNull(log);
		Validate.notNull(newStatus);
		if(!newStatus.equals(log.getStatus())){
			if(newStatus.equals(LogStatus.PENDING)){
				setPendingStatus(log);
			}else if(newStatus.equals(LogStatus.REFUSED)){
				setRfusedStatus(log);
			}else if(newStatus.equals(LogStatus.APPROVED)){
				setApprovedStatus(log);
			}else{
				throw new IllegalArgumentException("Unknown log status: " + newStatus);
			}
			setComment(log, withComment);
		}
	}
	
	private void setComment(AbstractLog log, final String message){
		if(StringUtils.isNotBlank(message)){
			Comment comment = new Comment(UserUtils.getLoggedUser());
			comment.setComment(message);
			log.getComments().add(comment);
		}
	}
	
	private void setPendingStatusNotificationEmail(AbstractLog log, String message){
		final EmailTemplate emailTemplate = emailTemplateService.getByCode(EmailTemplate.QUASAR_APPROVAL_REQUEST);
		if(emailTemplate != null){
			final QuasarSettings settings = quasarSettingsService.getSettings();
			final Map<String, Object> context = prepareStatusContext(log, message);
			HtmlMailMessage htmlMessage = new HtmlMailMessage(log.getAuditor().getName(), emailTemplate,  context);
			htmlMessage.addRecipientTo(settings.getNotificationEmail());
			nlfnormMailSender.send(htmlMessage);
		}
	}
	
	private Map<String, Object> prepareStatusContext(final AbstractLog log, final String message){
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("auditorName", log.getAuditor().getName());
		context.put("logType", determineLogType(log));
		context.put("logUrl", determineLogUrl(log));
		context.put("comment", message);
		return context;
	}
	

	private String determineLogType(final AbstractLog log){
		if(log instanceof AuditLog){
			return "Audit log";
		}
		throw new IllegalArgumentException("Unknown log instance");
		
	}
	
	private String determineLogUrl(final AbstractLog log){
		if(log instanceof AuditLog){
			return host + AuditLogController.PROFILE_EDIT_MAPPING_URL + log.getId();
		}
		throw new IllegalArgumentException("Unknown log instance");
		
	}

}
