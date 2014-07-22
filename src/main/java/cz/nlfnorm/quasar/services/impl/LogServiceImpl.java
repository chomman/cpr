package cz.nlfnorm.quasar.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.access.AccessDeniedException;

import cz.nlfnorm.context.ContextHolder;
import cz.nlfnorm.entities.EmailTemplate;
import cz.nlfnorm.mail.HtmlMailMessage;
import cz.nlfnorm.mail.NlfnormMailSender;
import cz.nlfnorm.quasar.constants.AuditorFilter;
import cz.nlfnorm.quasar.entities.AbstractLog;
import cz.nlfnorm.quasar.entities.AuditLog;
import cz.nlfnorm.quasar.entities.Comment;
import cz.nlfnorm.quasar.entities.DossierReport;
import cz.nlfnorm.quasar.entities.QuasarSettings;
import cz.nlfnorm.quasar.enums.LogStatus;
import cz.nlfnorm.quasar.security.AccessUtils;
import cz.nlfnorm.quasar.services.QuasarSettingsService;
import cz.nlfnorm.quasar.web.controllers.AuditLogController;
import cz.nlfnorm.quasar.web.controllers.DossierReportController;
import cz.nlfnorm.services.EmailTemplateService;
import cz.nlfnorm.utils.EmailUtils;
import cz.nlfnorm.utils.ParseUtils;
import cz.nlfnorm.utils.UserUtils;

/**
 * Base implementation of log statuses business logic. 
 * 
 * @author Peter Jurkovic
 * @date Jul 15, 2014
 */
public abstract class LogServiceImpl{
	
	
	@Value("#{config['host']}")
	private String host;
	
	@Autowired
	protected EmailTemplateService emailTemplateService;
	@Autowired 
	protected QuasarSettingsService quasarSettingsService;
	@Autowired
	protected NlfnormMailSender nlfnormMailSender;
	@Autowired
	protected MessageSource messageSource;
	
	
	/**
	 * Sets log status to "PENDING" and send notification email to main QUASAR admin
	 * 
	 * @param log - Auditor's log, which status should be changed to PENDING
	 * @param withComment - user's comment (can be empty)
	 * 
	 * @see {@link QuasarSettings}
	 * @see {@link LogStatus}
	 * @throws AccessDeniedException - if is logged user unauthorized
	 * @throws IllegalArgumentException - if given log is NULL
	 */
	public void setPendingStatus(AbstractLog log, String withComment) {
		Validate.notNull(log);
		AccessUtils.validateAuthorizationFor(log);
		log.setStatus(LogStatus.PENDING);
		setComment(log, withComment);
		sendApprovalRequestNotificationEmail(log, withComment);
	}

	/**
	 * Sets log status to "REFUSED" and send notification email to given auditor.
	 * (if has given auditor defined other e-mail addresses, the copy of this e-mail will
	 * be forwarded to this addresses too.) 
	 * 
	 * @param log - Auditor's log, which status should be changed to REFUSED
	 * @param withComment - user's comment (can be empty)
	 * 
	 * @see {@link QuasarSettings}
	 * @see {@link LogStatus}
	 * @throws AccessDeniedException - if the logged hasn't rights to refuse
	 * @throws IllegalArgumentException - if given log is NULL
	 */
	public void setRfusedStatus(AbstractLog log, String withComment) {
		Validate.notNull(log);
		AccessUtils.validateAuthorizationFor(log);
		log.setStatus(LogStatus.REFUSED);
		log.newRevision();
		setComment(log, withComment);
		sendStatusChangedEmail(log, withComment);
	}
	
	
	/**
	 * Sets log status to "APPROVED" and send notification email to given auditor.
	 * (if has given auditor defined other e-mail addresses, the copy of this e-mail will
	 * be forwarded to this addresses too.) 
	 * 
	 * @param log - Auditor's log, which status should be changed to APPROVED
	 * @param withComment - user's comment (can be empty) 
	 * 
	 * @see {@link QuasarSettings}
	 * @see {@link LogStatus}
	 * @throws AccessDeniedException - if the logged hasn't rights to approval
	 * @throws IllegalArgumentException - if given log is NULL
	 */
	public void setApprovedStatus(AbstractLog log, String withComment) {
		Validate.notNull(log);
		if(!UserUtils.getLoggedUser().isQuasarAdmin()){
			throw new AccessDeniedException("Access denied: " + UserUtils.getLoggedUser());
		}
		log.setStatus(LogStatus.APPROVED);
		setComment(log, withComment);
		sendStatusChangedEmail(log, withComment);
	}
	
		
	private void setComment(AbstractLog log, final String message){
		if(StringUtils.isNotBlank(message)){
			Comment comment = new Comment(UserUtils.getLoggedUser());
			comment.setComment(message);
			comment.setAbstractLog(log);
			log.getComments().add(comment);
		}
	}
	
	
	private void sendApprovalRequestNotificationEmail(final AbstractLog log, final String message){
		final EmailTemplate emailTemplate = emailTemplateService.getByCode(EmailTemplate.QUASAR_APPROVAL_REQUEST);
		final QuasarSettings settings = quasarSettingsService.getSettings();
		final Map<String, Object> context = prepareContex(log, message);
		HtmlMailMessage htmlMessage = new HtmlMailMessage(log.getAuditor().getEmail(), emailTemplate,  context);
		htmlMessage.addRecipientTo(settings.getNotificationEmail());
		nlfnormMailSender.send(htmlMessage);
	}
	
	
	private void sendStatusChangedEmail(final AbstractLog log, final String message){
		final EmailTemplate emailTemplate = emailTemplateService.getByCode(EmailTemplate.QUASAR_STATUS_CHANGED);
		if(emailTemplate != null){
			final Map<String, Object> context = prepareContex(log, message);
			final QuasarSettings settings = quasarSettingsService.getSettings();
			HtmlMailMessage htmlMessage = new HtmlMailMessage(settings.getNotificationEmail(), emailTemplate,  context);
			htmlMessage.addRecipientTo(log.getAuditor().getEmail());
			htmlMessage.addRecipientTo(EmailUtils.sprintEmails(log.getAuditor().getOtherEmails()));
			nlfnormMailSender.send(htmlMessage);
		}
	}
	
	private Map<String, Object> prepareContex(final AbstractLog log, final String message){
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("auditorName", log.getAuditor().getName());
		context.put("logType", determineLogType(log));
		context.put("logUrl", determineLogUrl(log));
		context.put("comment", message);
		context.put("status", log.getStatus());
		return context;
	}
	

	private String determineLogType(final AbstractLog log){
		if(log instanceof AuditLog){
			return messageSource.getMessage("auditLog.auditLog", null, ContextHolder.getLocale());
		}else if(log instanceof DossierReport){
			return messageSource.getMessage("dossierReport", null, ContextHolder.getLocale());
		}
		throw new IllegalArgumentException("Unknown log instance");
		
	}
	
	private String determineLogUrl(final AbstractLog log){
		if(log instanceof AuditLog){
			return host + AuditLogController.EDIT_MAPPING_URL.replace("{id}", log.getId()+"") ;
		}else if(log instanceof DossierReport){
			return host + DossierReportController.EDIT_MAPPING_URL.replace("{id}", log.getId()+"") ;
		}
		throw new IllegalArgumentException("Unknown log instance");
		
	}
	
	protected Map<String, Object> validateCriteria(final Map<String, Object> criteria){
		if(criteria.size() > 0){
			criteria.put(AuditorFilter.AUDITOR, ParseUtils.parseLongFromStringObject(criteria.get(AuditorFilter.AUDITOR)));
			criteria.put(AuditorFilter.PARNTER, ParseUtils.parseLongFromStringObject(criteria.get(AuditorFilter.PARNTER)));
			criteria.put(AuditorFilter.DATE_FROM, ParseUtils.parseDateTimeFromStringObject(criteria.get(AuditorFilter.DATE_FROM)));
			criteria.put(AuditorFilter.DATE_TO, ParseUtils.parseDateTimeFromStringObject(criteria.get(AuditorFilter.DATE_TO)));
			criteria.put(AuditorFilter.STATUS, ParseUtils.parseIntFromStringObject(criteria.get(AuditorFilter.STATUS)));
		}
		return criteria;
	}

}
