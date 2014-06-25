package cz.nlfnorm.quasar.views;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class AbstractFunction {

	private Long id;
	private boolean formalLegalRequiremets;
	private boolean recentActivities;
	
	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "formal_legal_requirements")
	public boolean isFormalLegalRequiremets() {
		return formalLegalRequiremets;
	}
	
	public void setFormalLegalRequiremets(boolean formalLegalRequiremets) {
		this.formalLegalRequiremets = formalLegalRequiremets;
	}
	
	@Column(name = "recent_acitivities")
	public boolean isRecentActivities() {
		return recentActivities;
	}
	
	public void setRecentActivities(boolean recentActivities) {
		this.recentActivities = recentActivities;
	}
	
	@Transient
	public abstract boolean getAreAllRequirementsValid();
	
}
