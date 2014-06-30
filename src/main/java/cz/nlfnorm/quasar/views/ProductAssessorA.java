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
public class ProductAssessorA extends AbstractNandoFunction {

	private boolean minAudits;
	
	@Column(name="min_audits")
	public boolean isMinAudits() {
		return minAudits;
	}

	public void setMinAudits(boolean minAudits) {
		this.minAudits = minAudits;
	}	

	@Transient
	public boolean getAuditingTrainingActiveMd(){
		return isMinAudits() && super.getAuditingTrainingActiveMd();
	}
	
	@Transient
	public boolean getAuditingTrainingNonActiveMd(){
		return isMinAudits() && super.getAuditingTrainingNonActiveMd();
	}
	
	@Transient
	public boolean getAuditingTrainingIvd(){
		return isMinAudits() && super.getAuditingTrainingIvd();
	}
	
		
	@Transient
	public boolean getAreRequirementForNonActiveMdSatisfy(){
		return super.getAreRequirementForNonActiveMdSatisfy() &&
			   isMinAudits();
	}
	
	@Transient
	public boolean getAreRequirementForIvdSatisfy(){
		return super.getAreRequirementForIvdSatisfy() &&
				isMinAudits();
	}
		
}
