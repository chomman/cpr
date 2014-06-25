package cz.nlfnorm.quasar.views;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Cache (usage=CacheConcurrencyStrategy.READ_ONLY) 
@Table(name = "quasar_product_assessor_a")
public class ProductAssessorA extends AbstractFunction {

	private boolean generalRequirementsActiveMd;
	private boolean generalRequirementsNonActiveMd;
	private boolean generalRequirementsIvd;
	
	private boolean trainingActiveMd;
	private boolean trainingNonActiveMd;
	private boolean trainingIvd;
	
	private boolean minAudits;

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
	
	@Column(name="min_audits")
	public boolean isMinAudits() {
		return minAudits;
	}

	public void setMinAudits(boolean minAudits) {
		this.minAudits = minAudits;
	}
	
	@Transient
	public boolean getAuditingTrainingActiveMd(){
		return isMinAudits() && isTrainingActiveMd();
	}
	
	@Transient
	public boolean getAuditingTrainingNonActiveMd(){
		return isMinAudits() && isTrainingNonActiveMd();
	}
	
	@Transient
	public boolean getAuditingTrainingIvd(){
		return isMinAudits() && isTrainingIvd();
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
			   isMinAudits() &&
			   isRecentActivities();
	}
	
	@Transient
	public boolean getAreRequirementForIvdSatisfy(){
		return isFormalLegalRequiremets() &&
			   isGeneralRequirementsIvd() &&
			   isTrainingIvd() &&
			   isMinAudits() &&
			   isRecentActivities();
	}
	
	@Transient
	public boolean getAreAllRequirementsValid() {
		return getAreRequirementForActiveMdSatisfy() ||
			   getAreRequirementForNonActiveMdSatisfy() ||
			   getAreRequirementForIvdSatisfy();
	}
	
	
	
}
