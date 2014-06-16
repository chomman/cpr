package cz.nlfnorm.quasar.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import cz.nlfnorm.dao.impl.IdentifiableByLong;
import cz.nlfnorm.entities.User;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class AbstractAuditorCode implements Serializable, IdentifiableByLong{
	
	private Long id;

	private Auditor auditor;
	
	private LocalDateTime changed = new LocalDateTime();
	
	private User changedBy;
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Transient
	@Override
	public Long getId() {
		return id;
	}
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractAuditorCode other = (AbstractAuditorCode) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
