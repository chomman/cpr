package cz.nlfnorm.entities;

import java.io.Serializable;

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
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

@Entity
@SequenceGenerator(name = "user_has_online_publication_seq", sequenceName = "user_has_online_publication_seq", initialValue = 1, allocationSize =1)
@Table(name="user_has_online_publication")
public class UserOnlinePublication implements Serializable {
	
	private static final long serialVersionUID = 2415936857505611756L;

	private Long id;
	
	private User user;
	
	private PortalProduct portalProduct;
	
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

	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portal_product_id", nullable = false)
	public PortalProduct getPortalProduct() {
		return portalProduct;
	}

	public void setPortalProduct(PortalProduct portalProduct) {
		this.portalProduct = portalProduct;
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
