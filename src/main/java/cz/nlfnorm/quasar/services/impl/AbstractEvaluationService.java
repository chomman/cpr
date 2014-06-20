package cz.nlfnorm.quasar.services.impl;

import org.apache.commons.lang.Validate;

import cz.nlfnorm.quasar.dto.AuditorFunction;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.AuditorEducation;
import cz.nlfnorm.quasar.entities.EducationLevel;


public abstract class AbstractEvaluationService {
	
	private final static int WORK_EXPERIENCE_YEARS_THREASHOLD = 4;
	
	public void evaluateFormalAndLegalRequirements(AuditorFunction auditor){
		auditor.setFormalAndLegalReqirements(areFormalAndLegalRequirementsValid(auditor.getAuditor()));
	}
	
	abstract void evaluateGeneralRequirements(AuditorFunction auditor);
	
	abstract void evaluateTrainingAuditingExperience(AuditorFunction auditor);
	
	abstract void evaluateActivitiesInTheMostRecentYear(AuditorFunction auditor);
	
	/**
	 * Returns TRUE, if auditor has:
	 * 
	 * <ol>
	 * 	<li>ECR card signed</li>
	 *  <li>Confidentiality signed</li>
	 *  <li>CV delivered</li>
	 * <ol>
	 * 
	 * @param auditor 
	 * @return TRUE is are form and legal requiremets valid
	 * @throws IllegalArgumentException if given auditor is NULL
	 */
	protected boolean areFormalAndLegalRequirementsValid(final Auditor auditor){
		return auditor.getAreFormalAndLegalReqiurementValid();
	}
	
	
	
	/**
	 * Minimum education is Bachelor's degree
	 * 
	 * @param auditorFucntion
	 * @return TURE, if auditor's education is Bachelor's or higher.
	 * @throws IllegalArgumentException if given auditor is NULL
	 */
	protected boolean hasSufficientEducationForActiveMd(final AuditorFunction auditorFucntion){
		validate(auditorFucntion);
		return hasSufficientEducation(auditorFucntion.getAuditor().getForActiveMedicalDevice());
	}
	
	/**
	 * Minimum education is Bachelor's degree
	 * 
	 * @param auditor
	 * @return TURE, if auditor's education is Bachelor's or higher.
	 * @throws IllegalArgumentException if given auditor is NULL
	 */
	protected boolean hasSufficientEducationForNonActiveMd(final AuditorFunction auditorFucntion){
		validate(auditorFucntion);
		return hasSufficientEducation(auditorFucntion.getAuditor().getForNonActiveMedicalDevice());
	}
	
	/**
	 * Returns auditor's total working experience for NON ACTIVE medical devices
	 * 
	 * @param auditor
	 * @return total work experience for NON ACTIVE MD in years
	 */
	protected boolean hasSufficientWorkExperienceForActiveMd(final AuditorFunction auditorFucntion){
		validate(auditorFucntion);
		int workExperience =  auditorFucntion.getAuditor().getTotalWorkExperienceInMedicalDevices();
		workExperience += getSupersededWorkExperience(auditorFucntion.getAuditor().getForNonActiveMedicalDevice());
		return workExperience >= WORK_EXPERIENCE_YEARS_THREASHOLD;
	}
	
	
	/**
	 * Returns auditor's total working experience for active medical devices
	 * 
	 * @param auditor
	 * @return total work experience for ACTIVE MD in years
	 */
	protected boolean hasSufficientWorkExperienceForNonActiveMd(final AuditorFunction auditorFucntion){
		validate(auditorFucntion);
		int workExperience =  auditorFucntion.getAuditor().getTotalWorkExperienceInMedicalDevices();
		workExperience += getSupersededWorkExperience(auditorFucntion.getAuditor().getForActiveMedicalDevice());
		return workExperience >= WORK_EXPERIENCE_YEARS_THREASHOLD;
	}
	
	/**
	 * Returns auditor's total working experience for In vitro diagnostic medical devices
	 * 
	 * @param auditor
	 * @return total work experience for In vitro diagnostic medical devices in years
	 */
	protected boolean hasSufficientWorkExperienceForVid(final AuditorFunction auditorFucntion){
		return hasSufficientEducationForActiveMd(auditorFucntion) ||
			   hasSufficientEducationForNonActiveMd(auditorFucntion);
	}
	
	private int getSupersededWorkExperience(final AuditorEducation education){
		if(!isEducationLevelSet(education)){
			return 0;
		}
		return education.getEductionLevel().getYeasSubstitution();
	}
	
	
	private boolean hasSufficientEducation(final AuditorEducation education){
		if(isEducationLevelSet(education) && education.getEductionLevel().getId() < EducationLevel.BACHALOR ){
			return false;
		}
		return true;
	}
	
	private boolean isEducationLevelSet(final AuditorEducation education){
		return education != null && education.getEductionLevel() != null; 
	}
	
	private void validate(AuditorFunction auditorFunction){
		Validate.notNull(auditorFunction);
		Validate.notNull(auditorFunction.getAuditor());
	}
}
