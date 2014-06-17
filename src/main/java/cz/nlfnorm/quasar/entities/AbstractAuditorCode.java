package cz.nlfnorm.quasar.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import cz.nlfnorm.entities.User;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class AbstractAuditorCode extends IdentifiableEntity{
	
	private Auditor auditor;
	
	private LocalDateTime changed = new LocalDateTime();
	
	private User changedBy;
	
	@ManyToOne(cascade = CascadeType.DETACH)
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
	
}
