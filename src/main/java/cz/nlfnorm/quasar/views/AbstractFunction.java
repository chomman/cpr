package cz.nlfnorm.quasar.views;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class AbstractFunction {

	private Long id;
	private Integer itcId;
	private boolean isInTraining;
	private boolean formalLegalRequiremets;
	private boolean generalRequiremets;
	private boolean trainingAuditing;
	private boolean recentActivities;
	
	
	@Id
	public Long getId() {
		return id;
	}
	@Column(name = "itc_id")
	public Integer getItcId() {
		return itcId;
	}
	
	@Column(name = "is_in_training")
	public boolean isInTraining() {
		return isInTraining;
	}
	
	@Column(name = "formal_legal_requirements")
	public boolean isFormalLegalRequiremets() {
		return formalLegalRequiremets;
	}
		
	@Column(name = "general_requirements")
	public boolean isGeneralRequiremets() {
		return generalRequiremets;
	}
		
	@Column(name = "training_auditing")
	public boolean isTrainingAuditing() {
		return trainingAuditing;
	}
	

	@Column(name = "recent_acitivities")
	public boolean isRecentActivities() {
		return recentActivities;
	}
	
	@Transient
	public boolean getAreAllRequirementsValid(){
		return isFormalLegalRequiremets() &&
			   isGeneralRequiremets() &&
			   isRecentActivities() &&
			   isTrainingAuditing();
	}
}
