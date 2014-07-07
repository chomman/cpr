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

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import cz.nlfnorm.entities.User;

@Entity
@SequenceGenerator(name = "quasar_comment_id_seq", sequenceName = "quasar_comment_id_seq", initialValue = 1, allocationSize =1)
@Table(name = "quasar_comment")
public class Comment extends IdentifiableEntity {
	
	private static final long serialVersionUID = -5195625626340774738L;
	
	private User user;
	private LocalDateTime created;
	private String comment;
	private AbstractLog abstractLog;
	
	public Comment(){}
	
	public Comment(User user){
		this.user = user;
		created = new LocalDateTime();
	}
	
	@Id
	@Override
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quasar_comment_id_seq")
	public Long getId() {
		return super.getId();
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "created")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	@Column(name = "comment", length = 300)
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "audit_log_id", nullable = false)
	public AbstractLog getAbstractLog() {
		return abstractLog;
	}

	public void setAbstractLog(AbstractLog abstractLog) {
		this.abstractLog = abstractLog;
	}
	
	
}	
