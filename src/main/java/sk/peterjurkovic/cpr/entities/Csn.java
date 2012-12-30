package sk.peterjurkovic.cpr.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "csn")
@Inheritance(strategy = InheritanceType.JOINED)
public class Csn extends AbstractEntity {

	private static final long serialVersionUID = 1999821354L;
	
	private Long id;
	
	private String CsnName;

	private String CsnOnlineId;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name ="csn_name", length = 25)
	public String getCsnName() {
		return CsnName;
	}

	public void setCsnName(String csnName) {
		CsnName = csnName;
	}

	@Column(name = "csn_online_id", length = 7)
	public String getCsnOnlineId() {
		return CsnOnlineId;
	}

	public void setCsnOnlineId(String csnOnlineId) {
		CsnOnlineId = csnOnlineId;
	}
	
	
}
