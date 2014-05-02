package sk.peterjurkovic.cpr.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import sk.peterjurkovic.cpr.enums.OnlinePublication;

@Entity
@SequenceGenerator(name = "user_has_online_publication_seq", sequenceName = "user_has_online_publication_seq", initialValue = 1, allocationSize =1)
@Table(name="user_has_online_publication")
public class UserOnlinePublication {
	
	private Long id;
	
	private User user;
	
	private OnlinePublication onlinePublication;
	
	private LocalDate validity;
	
	private LocalDateTime created;
	
	private LocalDateTime changed;
	
	public UserOnlinePublication(){
		validity = new LocalDate().plusYears(1);
		created = new LocalDateTime();
		changed = new LocalDateTime();
	}
		
	public UserOnlinePublication(User user){
		this();
		if(user == null){
			throw new IllegalArgumentException("User can not be NULL");
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_has_online_publication_seq")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Enumerated(value = EnumType.STRING)
	@Column(name = "online_publication", length = 20, nullable = false)
	public OnlinePublication getOnlinePublication() {
		return onlinePublication;
	}

	public void setOnlinePublication(OnlinePublication onlinePublication) {
		this.onlinePublication = onlinePublication;
	}
	
	@Column(name = "validity", nullable = false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	public LocalDate getValidity() {
		return validity;
	}

	public void setValidity(LocalDate validity) {
		this.validity = validity;
	}
	
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	public LocalDateTime getChanged() {
		return changed;
	}
	
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	public void setChanged(LocalDateTime changed) {
		this.changed = changed;
	}
	
	
	
	
	
}
