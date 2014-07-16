package cz.nlfnorm.quasar.web.validators;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import cz.nlfnorm.context.ContextHolder;
import cz.nlfnorm.quasar.entities.AuditLog;
import cz.nlfnorm.quasar.services.AuditLogService;
import cz.nlfnorm.quasar.web.forms.AuditLogItemForm;
import cz.nlfnorm.validators.AbstractValidator;

@Component
public class AuditorLogItemValidator extends AbstractValidator{

	@Autowired
	private AuditLogService auditLogService;
	
	@Override
	protected void addExtraValidation(Object objectForm, Errors errors) {
		AuditLogItemForm form = (AuditLogItemForm)objectForm;
		validateAuditLogDate(form, errors);
		validateOrderNumber(form, errors);
		validateCodes(form, errors);
	}
	
	private void validateOrderNumber(AuditLogItemForm form, Errors errors){
		if(form.getItem().getCertificationBody() != null &&
		   form.getItem().getCertificationBody().getName().equalsIgnoreCase("itc") &&
		   StringUtils.isBlank(form.getItem().getOrderNo())){
			errors.reject("error.orderNo");
		}
	}
	
	
	private void validateAuditLogDate(AuditLogItemForm form, Errors errors){
		if(form.getItem().getAuditDate() == null){
			errors.reject("error.auditDate", null, null);
		}
		final AuditLog log = auditLogService.getById(form.getAuditLogId());
		final LocalDate earliestDate = auditLogService.getEarliestPossibleDateForAuditLog(log.getAuditor());
		if(earliestDate != null && earliestDate.isAfter(form.getItem().getAuditDate())){
			final String date = earliestDate.toString("dd.MM.yyyy");
			final String message =  messageSource.getMessage("error.auditLogItem.auditDate.range", null , ContextHolder.getLocale());
			errors.reject( null, null , message.replace("{0}", date));
		}
	}
	
	
	private void validateCodes(AuditLogItemForm form, Errors errors){
		if(StringUtils.isBlank(form.getEacCodes()) && StringUtils.isBlank(form.getNandoCodes())){
			errors.reject("error.auditLogItem.codes");
		}
	}
}
