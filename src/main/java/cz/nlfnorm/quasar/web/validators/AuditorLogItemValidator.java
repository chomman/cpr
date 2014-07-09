package cz.nlfnorm.quasar.web.validators;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import cz.nlfnorm.context.ContextHolder;
import cz.nlfnorm.quasar.services.AuditLogService;
import cz.nlfnorm.quasar.web.forms.AuditLogItemForm;
import cz.nlfnorm.validators.AbstractValidator;

@Component
public class AuditorLogItemValidator extends AbstractValidator{

	@Autowired
	private AuditLogService auditLogService;
	@Autowired
	protected MessageSource messageSource;
	
	@Override
	protected void addExtraValidation(Object objectForm, Errors errors) {
		AuditLogItemForm form = (AuditLogItemForm)objectForm;
		validateAuditLogDate(form.getItem().getAuditDate(), errors);
		validateOrderNumber(form, errors);
		validateCodes(form, errors);
	}
	
	private void validateOrderNumber(AuditLogItemForm form, Errors errors){
		if(form.getItem().getCertificationBody() != null &&
		   form.getItem().getCertificationBody().getName().equalsIgnoreCase("itc") &&
		   StringUtils.isBlank(form.getItem().getOrderNo())){
			errors.reject("error.auditLogItem.orderNo");
		}
	}
	
	
	private void validateAuditLogDate(LocalDate auditLogItemDate, Errors errors){
		if(auditLogItemDate == null){
			errors.reject(messageSource.getMessage("error.auditLogItem.auditDate", null, ContextHolder.getLocale()), null);
		}
		final LocalDate earliestDate = auditLogService.getEarliestPossibleDateForAuditLog();
		if(earliestDate != null && earliestDate.isAfter(auditLogItemDate)){
			errors.reject(messageSource.getMessage("error.auditLogItem.auditDate.range", new Object[]{earliestDate.toString("dd.MM.yyyy")}, ContextHolder.getLocale()), null);
		}
	}
	
	
	private void validateCodes(AuditLogItemForm form, Errors errors){
		if(StringUtils.isBlank(form.getEacCodes()) && StringUtils.isBlank(form.getNandoCodes())){
			errors.reject("error.auditLogItem.codes");
		}
	}
}
