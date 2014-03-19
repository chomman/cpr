package sk.peterjurkovic.cpr.validators.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import sk.peterjurkovic.cpr.context.ContextHolder;
import sk.peterjurkovic.cpr.entities.Report;

@Component
public class ReportValidator {
	
	@Autowired
	private MessageSource messageSource;
	
	public void validate(BindingResult result, Report report){
		
		if(report.getDateFrom() == null || report.getDateTo() == null){
			result.rejectValue("dateFrom", "error.report.date.empty");
		}else if(report.getDateFrom().isAfter(report.getDateTo())){
			result.rejectValue("dateFrom", "error.report.dateFrom.isAfter");
		}
		
	}
	
	public List<String> validate(Report report){
		List<String> errors = new ArrayList<String>();
		
		if(report.getDateFrom() == null || report.getDateTo() == null){
			errors.add(messageSource.getMessage( "error.report.date.empty", null, ContextHolder.getLocale()) );
		}else if(report.getDateFrom().isAfter(report.getDateTo())){
			errors.add(messageSource.getMessage( "error.report.dateFrom.isAfter", null, ContextHolder.getLocale()) );
		}
		
	return errors;
	}
}
