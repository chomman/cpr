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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import cz.nlfnorm.entities.User;


@Entity
@SequenceGenerator(name = "quasar_auditor_has_experience_id_seq", sequenceName = "quasar_auditor_has_experience_id_seq", initialValue = 1, allocationSize =1)
@Table(name = "quasar_auditor_has_experience")
public class AuditorExperience extends IdentifiableEntity{

	private static final long serialVersionUID = -2044107138060217401L;
	
	private Auditor auditor;
	private Experience experience;
	private int years = 0;
	private LocalDateTime changed;
	private User changedBy;
	
	public AuditorExperience(){}
	public AuditorExperience(Auditor auditor){
		this.auditor = auditor;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quasar_auditor_has_experience_id_seq")
	public Long getId() {
		return super.getId();
	}
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auditor_id")
	public Auditor getAuditor() {
		return auditor;
	}
	public void setAuditor(Auditor auditor) {
		this.auditor = auditor;
	}
	
	@NotNull(message = "{error.auditorExperience.workExperience}")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "experience_id")
	public Experience getExperience() {
		return experience;
	}
	public void setExperience(Experience experience) {
		this.experience = experience;
	}
	
	@Min(value = 0)
	@Max(value = 99)
	@Column(nullable = false)
	public int getYears() {
		return years;
	}
	public void setYears(int years) {
		this.years = years;
	}
	@Column(name = "changed")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	public LocalDateTime getChanged() {
		return changed;
	}

	public void setChanged(LocalDateTime changed) {
		this.changed = changed;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user_changed_by")
	public User getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(User changedBy) {
		this.changedBy = changedBy;
	}
	
}
