package cz.nlfnorm.quasar.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import cz.nlfnorm.entities.User;
import cz.nlfnorm.quasar.enums.LogStatus;

@Entity
@SuppressWarnings("serial")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SequenceGenerator(name = "quasar_log_id_seq", sequenceName = "quasar_log_id_seq", initialValue = 1, allocationSize =1)
public abstract class AbstractLog extends IdentifiableEntity{
	
	private LogStatus status;
	private Auditor auditor;
	private int revision;
	private LocalDateTime changed;
	private LocalDateTime created;
	private User createdBy;
	private User changedBy;
	
	private Set<Comment> comments = new HashSet<>();
	
	public AbstractLog(){
		created = new LocalDateTime();
		changed = new LocalDateTime();
		status = LogStatus.DRAFT;
		revision = 1;
	}
	
	public AbstractLog(Auditor auditor){
		this();
		this.auditor = auditor;
	}
	
	@Id
	@Override
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quasar_log_id_seq")
	public Long getId() {
		return super.getId();
	}
	
	
	@Column(name = "changed")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	public LocalDateTime getChanged() {
		return changed;
	}

	public void setChanged(LocalDateTime changed) {
		this.changed = changed;
	}	
	@Column(name = "created")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user_changed_by")
	public User getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(User changedBy) {
		this.changedBy = changedBy;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user_created_by")
	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	@Type(type="cz.nlfnorm.quasar.hibernate.LogStatusUserType")
	@Column(name = "status")
	public LogStatus getStatus() {
		return status;
	}

	public void setStatus(LogStatus status) {
		this.status = status;
	}
	
	@Transient
	public Auditor getAuditor() {
		return auditor;
	}

	public void setAuditor(Auditor auditor) {
		this.auditor = auditor;
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}
	
	@OrderBy(clause = "created")
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@OneToMany(mappedBy = "abstractLog", fetch = FetchType.LAZY, orphanRemoval = true)
	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	
	public void newRevision(){
		revision++;
	}
	
	@Transient
	public int getCountOfComments(){
		return comments.size();
	}
	
	/**
	 * Return TRUE if log status is DRAFT or REFUSED.
	 * @see {@link LogStatus}
	 * @return
	 */
	@Transient
	public boolean isEditable(){
		if( 
			status != null && 
			status.equals(LogStatus.DRAFT) || 
			status.equals(LogStatus.REFUSED)
		){
			return true;
		}
		return false;
	}
	
}
