package cz.nlfnorm.quasar.entities;

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
import javax.persistence.Transient;


@Entity
@Table(name = "quasar_auditor_has_special_training")
@SequenceGenerator(name = "quasar_special_training_id_seq", sequenceName = "quasar_special_training_id_seq", initialValue = 1, allocationSize =1)
public class SpecialTraining extends BaseEntity {

	private static final long serialVersionUID = 6961590682550545554L;
	private Auditor auditor;
	private int hours;
	private String name;
		
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quasar_special_training_id_seq")
	@Override
	public Long getId() {
		return super.getId();
	}
			
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	
	@Column(length = 150)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
		
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auditor_id")	
	public Auditor getAuditor() {
		return auditor;
	}


	public void setAuditor(Auditor auditor) {
		this.auditor = auditor;
	}


	@Transient
	@Override
	public String getCode() {
		return super.getCode();
	}
	
	
}
