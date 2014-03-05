package sk.peterjurkovic.cpr.validators.admin;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import sk.peterjurkovic.cpr.entities.Report;

@Component
public class ReportValidator {
	
	public void validate(BindingResult result, Report report){
		
		if(report.getDateFrom() == null || report.getDateTo() == null){
			result.rejectValue("dateFrom", "error.report.date.empty");
		}else if(report.getDateFrom().isAfter(report.getDateTo())){
			result.rejectValue("dateFrom", "error.report.dateFrom.isAfter");
		}
		
	}
}
