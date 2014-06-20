package cz.nlfnorm.quasar.dto;

import org.apache.commons.lang.Validate;

import cz.nlfnorm.quasar.entities.Auditor;

public abstract class AuditorFunction {

	private Auditor auditor;
	
	private boolean formalAndLegalReqirements;
	private boolean generalRequirements;
	private boolean trainingAuditingExperience;
	private boolean activitiesInRecentYear;
  	
	
	public boolean getAreRequrementRetained(){
		return formalAndLegalReqirements &&
			   generalRequirements &&
			   trainingAuditingExperience &&
			   activitiesInRecentYear;
	}
	
    public AuditorFunction(Auditor auditor){
    	Validate.notNull(auditor);
    	this.auditor = auditor;
    }
    
	public Auditor getAuditor() {
		return auditor;
	}
	public void setAuditor(Auditor auditor) {
		this.auditor = auditor;
	}

	public boolean getAreFormalAndLegalReqiurementValid() {
		return auditor.getAreFormalAndLegalReqiurementValid();
	}

	public boolean isGeneralRequirements() {
		return generalRequirements;
	}

	public void setGeneralRequirements(boolean generalRequirements) {
		this.generalRequirements = generalRequirements;
	}

	public boolean isTrainingAuditingExperience() {
		return trainingAuditingExperience;
	}

	public void setTrainingAuditingExperience(boolean trainingAuditingExperience) {
		this.trainingAuditingExperience = trainingAuditingExperience;
	}

	public boolean isActivitiesInRecentYear() {
		return activitiesInRecentYear;
	}

	public void setActivitiesInRecentYear(boolean activitiesInRecentYear) {
		this.activitiesInRecentYear = activitiesInRecentYear;
	}

	public boolean isFormalAndLegalReqirements() {
		return formalAndLegalReqirements;
	}

	public void setFormalAndLegalReqirements(boolean formalAndLegalReqirements) {
		this.formalAndLegalReqirements = formalAndLegalReqirements;
	}
	
}
