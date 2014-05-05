package cz.nlfnorm.entities;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractStandardCsn extends AbstractStandard {
	
	private static final long serialVersionUID = 7355845973505289967L;

	private String csnName;
	
	private String csnOnlineId;
	
	private String note;

	@Column(name ="csn_name", length = 45)
	public String getCsnName() {
		return csnName;
	}

	public void setCsnName(String csnName) {
		this.csnName = csnName;
	}
	
	@Column(name = "csn_online_id", length = 10)
	public String getCsnOnlineId() {
		return csnOnlineId;
	}

	public void setCsnOnlineId(String csnOnlineId) {
		this.csnOnlineId = csnOnlineId;
	}
	
	@Column
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
