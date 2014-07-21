package cz.nlfnorm.quasar.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.Min;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import cz.nlfnorm.entities.User;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class AbstractAuditorCode extends IdentifiableEntity{
	
	private Auditor auditor;
	
	private LocalDateTime changed = new LocalDateTime();
	
	private User changedBy;
	
	/**
	 * Number of NB 1023 audits in TA
	 */
	private int numberOfNbAudits;
	/**
	 * Number of ISO 13485 audits in TA
	 */
	private int numberOfIso13485Audits;
	
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "auditor_id")
	public Auditor getAuditor(){
		return auditor;
	}
		

	public void setAuditor(Auditor auditor) {
		this.auditor = auditor;
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
	
	@Min(value = 0)
	@Column(name = "number_of_nb_audits")
	public int getNumberOfNbAudits() {
		return numberOfNbAudits;
	}

	public void setNumberOfNbAudits(int numberOfNBAudits) {
		this.numberOfNbAudits = numberOfNBAudits;
	}

	@Min(value = 0)
	@Column(name = "number_of_iso13485_audits")
	public int getNumberOfIso13485Audits() {
		return numberOfIso13485Audits;
	}

	public void setNumberOfIso13485Audits(int numberOfIso13485Audits) {
		this.numberOfIso13485Audits = numberOfIso13485Audits;
	}
	
	@Transient
	public void incrementNumberOfNbAudits(int val){
		numberOfNbAudits += val;
	}
	
	@Transient
	public void incrementNumberOfIso13485Audits(int val){
		numberOfIso13485Audits += val;
	}
	
	@Transient
	public int getTotalNumberOfAudits(){
		return getNumberOfIso13485Audits() + getNumberOfNbAudits();
	}
	
	@Transient
	protected int getNoOfAuditsInTraining(final int trainingTrehsold){
		if(getTotalNumberOfAudits() >= trainingTrehsold){
			return trainingTrehsold;
		}
		return getTotalNumberOfAudits();
	}
}
