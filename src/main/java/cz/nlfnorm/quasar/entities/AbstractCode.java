package cz.nlfnorm.quasar.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class AbstractCode extends BaseEntity {
	
	@JsonIgnore
	private int auditsInTrainingTreshold = 1;

	@Min(value = 0)
	@Column(name = "audits_in_training_treshold")
	public int getAuditsInTrainingTreshold() {
		return auditsInTrainingTreshold;
	}

	public void setAuditsInTrainingTreshold(int auditsInTrainingTreshold) {
		this.auditsInTrainingTreshold = auditsInTrainingTreshold;
	}
	
}
