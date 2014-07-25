package cz.nlfnorm.quasar.web.validators;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import cz.nlfnorm.quasar.entities.TrainingLog;
import cz.nlfnorm.validators.AbstractValidator;

@Component
public class TrainingLogValidator extends AbstractValidator{

	@Override
	protected void addExtraValidation(Object objectForm, Errors errors) {
		final TrainingLog log = (TrainingLog) objectForm;
		validateDate(log, errors);
		validateSubject(log, errors);
	}

	public void validateDate(TrainingLog log, Errors errors){
		if(log.getDate() == null){
			errors.reject("error.trainingLog.date");
		}
	}
	
	public void validateSubject(TrainingLog log, Errors errors){
		if(StringUtils.isBlank(log.getSubject())){
			errors.reject("error.trainingLog.subject");
		}
	}
	
	
}
