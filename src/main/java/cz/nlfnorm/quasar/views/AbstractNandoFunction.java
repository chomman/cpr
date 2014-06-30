package cz.nlfnorm.quasar.views;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class AbstractNandoFunction extends AbstractFunction {
	
	private boolean generalRequirementsActiveMd;
	private boolean generalRequirementsNonActiveMd;
	private boolean generalRequirementsIvd;
	
	private boolean trainingActiveMd;
	private boolean trainingNonActiveMd;
	private boolean trainingIvd;
	
	@Column(name="gen_req_active_md")
	public boolean isGeneralRequirementsActiveMd() {
		return generalRequirementsActiveMd;
	}

	public void setGeneralRequirementsActiveMd(boolean generalRequirementsActiveMd) {
		this.generalRequirementsActiveMd = generalRequirementsActiveMd;
	}
	
	@Column(name="gen_req_non_active_md")
	public boolean isGeneralRequirementsNonActiveMd() {
		return generalRequirementsNonActiveMd;
	}

	public void setGeneralRequirementsNonActiveMd(
			boolean generalRequirementsNonActiveMd) {
		this.generalRequirementsNonActiveMd = generalRequirementsNonActiveMd;
	}

	@Column(name="gen_req_ivd")
	public boolean isGeneralRequirementsIvd() {
		return generalRequirementsIvd;
	}

	public void setGeneralRequirementsIvd(boolean generalRequirementsIvd) {
		this.generalRequirementsIvd = generalRequirementsIvd;
	}
	
	@Column(name="training_active_md")
	public boolean isTrainingActiveMd() {
		return trainingActiveMd;
	}

	public void setTrainingActiveMd(boolean trainingActiveMd) {
		this.trainingActiveMd = trainingActiveMd;
	}
	
	@Column(name="training_non_active_md")
	public boolean isTrainingNonActiveMd() {
		return trainingNonActiveMd;
	}

	public void setTrainingNonActiveMd(boolean trainingNonActiveMd) {
		this.trainingNonActiveMd = trainingNonActiveMd;
	}

	@Column(name="training_ivd")
	public boolean isTrainingIvd() {
		return trainingIvd;
	}

	public void setTrainingIvd(boolean trainingIvd) {
		this.trainingIvd = trainingIvd;
	}
	
	@Transient
	public boolean getAreAllRequirementsValid() {
		return getAreRequirementForActiveMdSatisfy() ||
			   getAreRequirementForNonActiveMdSatisfy() ||
			   getAreRequirementForIvdSatisfy();
	}
	
	@Transient
	public boolean getAreRequirementForActiveMdSatisfy(){
		return isFormalLegalRequiremets() &&
				   isGeneralRequirementsActiveMd() &&
				   getAuditingTrainingActiveMd() &&
				   isRecentActivities();
	}
	
	@Transient
	public boolean getAreRequirementForNonActiveMdSatisfy(){
		return isFormalLegalRequiremets() &&
				   isGeneralRequirementsNonActiveMd() &&
				   isTrainingNonActiveMd() &&
				   isRecentActivities();
	}
	
	@Transient
	public boolean getAreRequirementForIvdSatisfy(){
		return isFormalLegalRequiremets() &&
				   isGeneralRequirementsIvd() &&
				   isTrainingIvd() &&
				   isRecentActivities();
	}
	
	@Transient
	public boolean getAuditingTrainingActiveMd(){
		return isTrainingActiveMd();
	}
	
	@Transient
	public boolean getAuditingTrainingNonActiveMd(){
		return isTrainingNonActiveMd();
	}
	
	@Transient
	public boolean getAuditingTrainingIvd(){
		return isTrainingIvd();
	}
}
