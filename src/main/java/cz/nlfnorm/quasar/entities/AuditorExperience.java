package cz.nlfnorm.quasar.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;


@Entity
@SequenceGenerator(name = "quasar_auditor_has_experience_id_seq", sequenceName = "quasar_auditor_has_experience_id_seq", initialValue = 1, allocationSize =1)
@Table(name = "quasar_auditor_has_experience")
public class AuditorExperience extends IdentifiableEntity{

	private static final long serialVersionUID = -2044107138060217401L;
	
	private Auditor auditor;
	
	private Experience experience;
	
	private int years = 0;
	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quasar_auditor_has_experience_id_seq")
	public Long getId() {
		return super.getId();
	}

	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "auditor_id", insertable = false, updatable = false)
	public Auditor getAuditor() {
		return auditor;
	}

	public void setAuditor(Auditor auditor) {
		this.auditor = auditor;
	}
	
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "experience_id", insertable = false, updatable = false)
	public Experience getExperience() {
		return experience;
	}

	public void setExperience(Experience experience) {
		this.experience = experience;
	}
	
	@Min(value = 0)
	@Column(nullable = false)
	public int getYears() {
		return years;
	}

	public void setYears(int years) {
		this.years = years;
	}
	
	
}
