package cz.nlfnorm.quasar.web.controllers;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.nlfnorm.context.ContextHolder;
import cz.nlfnorm.quasar.services.AuditLogService;
import cz.nlfnorm.quasar.web.forms.ChangeLogStatusForm;

@Controller
public class ChangeLogStatusController extends QuasarSupportController {
	
	public static final int ACTION_AUDIT_LOG = 1;
	public static final int ACTION_DOCUMENTATION_LOG = 2;
	public static final int ACTION_TRAINING_LOG = 3;
	
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private AuditLogService auditLogService;
	
	@RequestMapping( value = "/admin/quasar/change-log-status", method = RequestMethod.POST)
	public String proccessChangeLogStatus(
			@ModelAttribute("changeLogStatusForm") ChangeLogStatusForm form, 
			BindingResult result){
		cleanComment(form);
		switch (form.getAction()) {
			case ACTION_AUDIT_LOG:
				auditLogService.changeStatus(
						auditLogService.getById(form.getLogId()),
					    form.getStatus(),
					    form.getComment());
				return getAuditLogUrl(form.getLogId());
			case ACTION_DOCUMENTATION_LOG:
						
				// TODO
			case ACTION_TRAINING_LOG:
				
				// TODO 
			default:
			throw new IllegalArgumentException("Unsupported action: " + form.getAction()) ;
		}
	}
	
	private void cleanComment(final ChangeLogStatusForm form){
		if(StringUtils.isNotBlank(form.getComment())){
			final String message = messageSource.getMessage("writeComment", null, ContextHolder.getLocale());
			form.setComment( StringUtils.replace(form.getComment(), message, ""));
		}
	}
	
	private String getAuditLogUrl(final long id){
		return successUpdateRedirect(AuditLogController.EDIT_MAPPING_URL.replace("{id}", id+""));
	}

}
