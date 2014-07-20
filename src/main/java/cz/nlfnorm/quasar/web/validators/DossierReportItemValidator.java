package cz.nlfnorm.quasar.web.validators;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import cz.nlfnorm.context.ContextHolder;
import cz.nlfnorm.quasar.entities.DossierReport;
import cz.nlfnorm.quasar.services.DossierReportService;
import cz.nlfnorm.quasar.web.forms.DossierReportItemForm;
import cz.nlfnorm.validators.AbstractValidator;

@Component
public class DossierReportItemValidator extends AbstractValidator{

	@Autowired
	private DossierReportService dossierReportService;
	
	@Override
	protected void addExtraValidation(Object objectForm, Errors errors) {
		DossierReportItemForm form = (DossierReportItemForm)objectForm;
		validateAuditLogDate(form, errors);
	}
	

	private void validateAuditLogDate(DossierReportItemForm form, Errors errors){
		if(form.getItem().getAuditDate() == null){
			errors.reject("error.auditDate", null, null);
		}
		final DossierReport report = dossierReportService.getById( form.getLogId() );
		final LocalDate earliestDate = dossierReportService.getEarliestPossibleDateForLog(report.getAuditor());
		if(earliestDate != null && earliestDate.isAfter(form.getItem().getAuditDate())){
			final String date = earliestDate.toString("dd.MM.yyyy");
			final String message =  messageSource.getMessage("error.dossierReportItem.auditDate.range", null , ContextHolder.getLocale());
			errors.reject( null, null , message.replace("{0}", date));
		}
	}

}
