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
@Table(name = "quasar_qs_auditor")
public class QsAuditor extends AbstractFunction {
	
	private Integer itcId;
	private boolean isInTraining;
	
	private boolean generalRequiremets;
	private boolean trainingAuditing;
	private boolean anyEacCodeGranted;
	
	@Column(name = "itc_id")
	public Integer getItcId() {
		return itcId;
	}
	
	@Column(name = "is_in_training")
	public boolean isInTraining() {
		return isInTraining;
	}	
		
	@Column(name = "general_requirements")
	public boolean isGeneralRequiremets() {
		return generalRequiremets;
	}
		
	@Column(name = "training_auditing")
	public boolean isTrainingAuditing() {
		return trainingAuditing;
	}
	
	@Column(name = "has_any_eac_code_granted")
	public boolean isAnyEacCodeGranted() {
		return anyEacCodeGranted;
	}

	public void setAnyEacCodeGranted(boolean anyEacCodeGranted) {
		this.anyEacCodeGranted = anyEacCodeGranted;
	}
	
	public void setItcId(Integer itcId) {
		this.itcId = itcId;
	}
	public void setInTraining(boolean isInTraining) {
		this.isInTraining = isInTraining;
	}
	
	public void setGeneralRequiremets(boolean generalRequiremets) {
		this.generalRequiremets = generalRequiremets;
	}
	public void setTrainingAuditing(boolean trainingAuditing) {
		this.trainingAuditing = trainingAuditing;
	}
	
	@Transient
	public boolean getAreAllRequirementsValid(){
		return isFormalLegalRequiremets() &&
			   isGeneralRequiremets() &&
			   isRecentActivities() &&
			   isTrainingAuditing();
	}
	
}
