package cz.nlfnorm.quasar.web.controllers;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.nlfnorm.entities.EmailTemplate;
import cz.nlfnorm.mail.HtmlMailMessage;
import cz.nlfnorm.mail.NlfnormMailSender;
import cz.nlfnorm.quasar.entities.AbstractLog;
import cz.nlfnorm.quasar.entities.AuditLog;
import cz.nlfnorm.quasar.entities.Comment;
import cz.nlfnorm.quasar.entities.QuasarSettings;
import cz.nlfnorm.quasar.services.AuditLogService;
import cz.nlfnorm.quasar.services.QuasarSettingsService;
import cz.nlfnorm.quasar.web.forms.ChangeLogStatusForm;
import cz.nlfnorm.services.EmailTemplateService;
import cz.nlfnorm.utils.UserUtils;

public class ChangeLogStatusController extends QuasarSupportController {
	
	public static final int ACTION_AUDIT_LOG = 1;
	public static final int ACTION_DOCUMENTATION_LOG = 2;
	public static final int ACTION_TRAINING_LOG = 3;
	
	@Autowired
	private AuditLogService auditLogService;
	@Autowired
	private EmailTemplateService emailTemplateService;
	@Autowired 
	private QuasarSettingsService quasarSettingsService;
	@Autowired
	private NlfnormMailSender nlfnormMailSender;

	@RequestMapping( value = "/admin/quasar/change-log-status", method = RequestMethod.POST)
	public String proccessChangeLogStatus(
			@ModelAttribute("changeLogStatusForm") ChangeLogStatusForm form, 
			BindingResult result){
		
		switch (form.getAction()) {
		case ACTION_AUDIT_LOG:
			return changeAuditLogStatus(form);
		case ACTION_DOCUMENTATION_LOG:
					
			// TODO
			//return getAuditLogUrlFor(id);
		case ACTION_TRAINING_LOG:
			
			// TODO 
			//return getAuditLogUrlFor(id);
		default:
			throw new IllegalArgumentException("Unsupported action: " + form.getAction()) ;
		}
	}
	
	private String getAuditLogUrlFor(final Long auditLogId ){
		return successUpdateRedirect(getProfileAuditLogUrl( auditLogId ));
	}
	
	private String changeAuditLogStatus(final ChangeLogStatusForm form){
		final AuditLog log = auditLogService.getById(form.getLogId());
		setStatusAndSendEmails(log, form);
		auditLogService.updateAndSetChanged(log);
		return getAuditLogUrlFor(log.getId());
	}
	
	private void setStatusAndSendEmails(final AbstractLog log, final ChangeLogStatusForm form){
		Validate.notNull(log, "Not found: " + log.toString());
		//log.setStatus(form.getStatus());
		if(StringUtils.isNotBlank(form.getComment())){
			Comment comment = new Comment(UserUtils.getLoggedUser());
			comment.setComment(form.getComment());
			log.getComments().add(comment);
		}
		sendApprovalRequestEmail(log, form);
	}
	
	
	private void sendApprovalRequestEmail(AbstractLog log, final ChangeLogStatusForm form){
		final EmailTemplate emailTemplate = emailTemplateService.getByCode(EmailTemplate.QUASAR_APPROVAL_REQUEST);
		if(emailTemplate != null){
			final QuasarSettings settings = quasarSettingsService.getSettings();
			final Map<String, Object> context = prepareApprovalEmailContext(log, form);
			HtmlMailMessage htmlMessage = new HtmlMailMessage(log.getAuditor().getName(), emailTemplate,  context);
			htmlMessage.addRecipientTo(settings.getNotificationEmail());
			nlfnormMailSender.send(htmlMessage);
		}else{
			logger.error("Email template was not found. CODE: " + EmailTemplate.QUASAR_APPROVAL_REQUEST);
		}
	}
	
	
	private Map<String, Object> prepareApprovalEmailContext(final AbstractLog log, final ChangeLogStatusForm form){
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("auditorName", log.getAuditor().getName());
		context.put("logType", determineLogType(form));
		context.put("logUrl", determineLogUrl(form));
		context.put("comment", form.getComment());
		return context;
	}
	
	
	private String determineLogType(final ChangeLogStatusForm form){
		switch (form.getAction()) {
		case ACTION_AUDIT_LOG:
			return "Audit log";
		case ACTION_DOCUMENTATION_LOG:
			return "Documentation log";	
		case ACTION_TRAINING_LOG:
			return "Training log";
		default:
			throw new IllegalArgumentException("Unsupported action: " + form.getAction()) ;
		}
	}
	
	private String determineLogUrl(final ChangeLogStatusForm form){
		switch (form.getAction()) {
		case ACTION_AUDIT_LOG:
			return getProfileAuditLogUrl(form.getLogId());
		case ACTION_DOCUMENTATION_LOG:
				
		case ACTION_TRAINING_LOG:
			
		default:
			throw new IllegalArgumentException("Unsupported action: " + form.getAction()) ;
		}
	}
	
	private String getProfileAuditLogUrl(final long id){
		return AuditLogController.PROFILE_EDIT_MAPPING_URL.replace("{0}", id+"");
	}
}
