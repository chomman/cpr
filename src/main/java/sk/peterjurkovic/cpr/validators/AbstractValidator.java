package sk.peterjurkovic.cpr.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import sk.peterjurkovic.cpr.context.ContextHolder;

public abstract class AbstractValidator implements org.springframework.validation.Validator, ApplicationContextAware,
ConstraintValidatorFactory {

	@Autowired
	private Validator validator;
	@Autowired
	private MessageSource messageSource;
	
	private ApplicationContext applicationContext;
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
		Map<String, T> beansByNames = applicationContext.getBeansOfType(key);
		if (beansByNames.isEmpty()) {
		    try {
		        return key.newInstance();
		    } catch (InstantiationException e) {
		        throw new RuntimeException("Could not instantiate constraint validator class '" + key.getName() + "'", e);
		    } catch (IllegalAccessException e) {
		        throw new RuntimeException("Could not instantiate constraint validator class '" + key.getName() + "'", e);
		    }
		}
		if (beansByNames.size() > 1) {
		    throw new RuntimeException("Only one bean of type '" + key.getName() + "' is allowed in the application context");
		}
		return (T) beansByNames.values().iterator().next();
	}
	
	public boolean supports(Class<?> c) {
		return true;
	}
	
	public void validate(Object objectForm, Errors errors) {
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(objectForm);
		for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
		    String propertyPath = constraintViolation.getPropertyPath().toString();
		    String message = constraintViolation.getMessage();
		    errors.rejectValue(propertyPath, "", message);
		}
		addExtraValidation(objectForm, errors);
	}
	
	protected abstract void addExtraValidation(Object objectForm, Errors errors);
	
	
	
	public List<String> getErrorMessages(List<ObjectError> errorList){
		List<String> errorMessages = new ArrayList<String>();
		if(CollectionUtils.isEmpty(errorList)){
			return errorMessages;
		}
		for(ObjectError errror : errorList){
			String message = messageSource.getMessage(errror.getDefaultMessage(), null, ContextHolder.getLocale());
			if(StringUtils.isNotBlank(message)){
				errorMessages.add(message);
			}else{
				errorMessages.add( errror.getDefaultMessage() );
			}
		}
		return errorMessages;
	}
}