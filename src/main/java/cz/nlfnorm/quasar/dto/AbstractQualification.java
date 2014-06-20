package cz.nlfnorm.quasar.dto;

import org.apache.commons.lang.Validate;

import cz.nlfnorm.quasar.entities.Auditor;

public abstract class AbstractQualification {

	private Auditor auditor;
    private boolean hasQualification;
	
    public AbstractQualification(Auditor auditor){
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
	
	public boolean getHasQualification() {
		return hasQualification;
	}
	public void setHasQualification(boolean hasQualification) {
		this.hasQualification = hasQualification;
	}

	
	
	
	
}
