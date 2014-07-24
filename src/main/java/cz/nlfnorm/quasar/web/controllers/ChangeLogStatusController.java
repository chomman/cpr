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
import cz.nlfnorm.quasar.services.DossierReportService;
import cz.nlfnorm.quasar.services.TrainingLogService;
import cz.nlfnorm.quasar.web.forms.ChangeLogStatusForm;

@Controller
public class ChangeLogStatusController extends QuasarSupportController {
	
	public static final int ACTION_AUDIT_LOG = 1;
	public static final int ACTION_DOSSIER_REPORT = 2;
	public static final int ACTION_TRAINING_LOG = 3;
	
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private AuditLogService auditLogService;
	@Autowired
	private DossierReportService dossierReportService;
	@Autowired
	private TrainingLogService trainingLogService;
	
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
			case ACTION_DOSSIER_REPORT:
				dossierReportService.changeStatus(
						dossierReportService.getById(form.getLogId()),
						form.getStatus(),
						form.getComment());		
				return getDossierReportUrl(form.getLogId());
			case ACTION_TRAINING_LOG:
				trainingLogService.changeStatus(
						trainingLogService.getById(form.getLogId()),
						form.getStatus(),
						form.getComment());
				return getTrainingLogUrl(form.getLogId());
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
		return getUrl(AuditLogController.EDIT_MAPPING_URL, id);
	}
	
	private String getDossierReportUrl(final long id){
		return getUrl(DossierReportController.EDIT_MAPPING_URL, id);
	}
	
	private String getTrainingLogUrl(final long id){
		return getUrl(TrainingLogController.EDIT_MAPPING_URL, id);
	}

	private String getUrl(final String url, final long id){
		return successUpdateRedirect(url.replace("{id}", id+""));
	}
}
