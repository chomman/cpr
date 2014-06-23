package cz.nlfnorm.quasar.views;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class AbstractFunction {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private boolean enabled;
	private Integer itcId;
	private String degrees;
	private boolean isInTraining;
	private boolean formalLegalRequiremets;
	private boolean generalRequiremets;
	private boolean trainingAuditing;
	private boolean recentActivities;
	
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "first_name")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name = "last_name")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	@Column(name = "itc_id")
	public Integer getItcId() {
		return itcId;
	}
	public void setItcId(Integer itcId) {
		this.itcId = itcId;
	}
	public String getDegrees() {
		return degrees;
	}
	public void setDegrees(String degrees) {
		this.degrees = degrees;
	}
	@Column(name = "is_in_training")
	public boolean isInTraining() {
		return isInTraining;
	}
	public void setInTraining(boolean isInTraining) {
		this.isInTraining = isInTraining;
	}
	@Column(name = "formal_legal_requirements")
	public boolean isFormalLegalRequiremets() {
		return formalLegalRequiremets;
	}
	public void setFormalLegalRequiremets(boolean formalLegalRequiremets) {
		this.formalLegalRequiremets = formalLegalRequiremets;
	}
	
	@Column(name = "general_requirements")
	public boolean isGeneralRequiremets() {
		return generalRequiremets;
	}
	public void setGeneralRequiremets(boolean generalRequiremets) {
		this.generalRequiremets = generalRequiremets;
	}
	
	@Column(name = "training_auditing")
	public boolean isTrainingAuditing() {
		return trainingAuditing;
	}
	public void setTrainingAuditing(boolean trainingAuditing) {
		this.trainingAuditing = trainingAuditing;
	}

	@Column(name = "recent_acitivities")
	public boolean isRecentActivities() {
		return recentActivities;
	}
	public void setRecentActivities(boolean recentActivities) {
		this.recentActivities = recentActivities;
	}
	@Transient
	public boolean getAreAllRequirementsValid(){
		return isFormalLegalRequiremets() &&
			   isGeneralRequiremets() &&
			   isRecentActivities() &&
			   isTrainingAuditing();
	}
}
