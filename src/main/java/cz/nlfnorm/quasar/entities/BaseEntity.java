package cz.nlfnorm.quasar.entities;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import cz.nlfnorm.entities.User;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class BaseEntity extends IdentifiableEntity{
	
	private String code;
	private boolean enabled = true;
	private LocalDateTime changed;
	private User changedBy;
	

	@Transient
	public String getCode() {
	       return code;
	   }

	  
    public void setCode(String code) {
       this.code = code;
    }
    
    
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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
