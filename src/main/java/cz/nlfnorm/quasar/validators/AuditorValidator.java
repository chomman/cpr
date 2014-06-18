package cz.nlfnorm.quasar.validators;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.forms.AuditorForm;
import cz.nlfnorm.quasar.services.AuditorService;
import cz.nlfnorm.utils.ValidationsUtils;
import cz.nlfnorm.validators.forntend.ChangePassowrdValidator;

@Component
public class AuditorValidator extends ChangePassowrdValidator {
	
	@Autowired
	private AuditorService auditorService;
	
	@Override
	protected void addExtraValidation(Object objectForm, Errors errors) {
		if(objectForm instanceof AuditorForm){
			final Auditor auditor = ((AuditorForm)objectForm).getAuditor();
			processExtraValidation(auditor, errors);
			validateLoginSimularity(auditor.getEmail(), ((AuditorForm)objectForm).getNewPassword(), errors );
			super.addExtraValidation(objectForm, errors);
		}
		
		if(objectForm instanceof Auditor){
			final Auditor auditor = ((Auditor)objectForm);
			processExtraValidation(auditor, errors);
		}	
	}
	
	
	
	private void validateNames(final Auditor auditor, Errors errors){
		if(StringUtils.isBlank(auditor.getFirstName())){
			errors.reject("auditor.firstName", "error.user.firstName");
		}		
		if(StringUtils.isBlank(auditor.getLastName())){
			errors.reject("auditor.lastName", "error.user.lastName");
		}
	}
	
	private void validateEmail(final Auditor auditor, Errors errors){
		if(!ValidationsUtils.isEmailValid(auditor.getEmail())){
			errors.reject("error.email");
		}
		if(!userService.isUserNameUniqe(getIdOrZero(auditor) , auditor.getEmail())){
			errors.reject("error.email.uniqe");
		}
	}
	
	private void validateItcId(final Auditor auditor, Errors errors){
		if(!auditorService.isItcIdUniqe(auditor.getItcId(), getIdOrZero(auditor))){
			errors.reject("error.auditor.itcId.uniqe");
		}
	}
	
	private void processExtraValidation(Auditor auditor, Errors errors){
		validateNames(auditor, errors);
		validateEmail(auditor, errors);
		validateItcId(auditor, errors);
	}
	
	private Long getIdOrZero(final Auditor auditor){
		return auditor.getId() == null ? 0l : auditor.getId();
	}
}
