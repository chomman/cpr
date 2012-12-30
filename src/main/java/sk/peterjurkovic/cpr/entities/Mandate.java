package sk.peterjurkovic.cpr.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;


@Entity
@Table(name ="mandate")
@Inheritance(strategy = InheritanceType.JOINED)
public class Mandate extends AbstractEntity {

	
	private static final long serialVersionUID = 815498413L;
	private Long id;
	private String mandateName;
	private String mandateFileUrl;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="mandate_name", length = 25)
	public String getMandateName() {
		return mandateName;
	}

	public void setMandateName(String mandateName) {
		this.mandateName = mandateName;
	}

	public String getMandateFileUrl() {
		return mandateFileUrl;
	}

	public void setMandateFileUrl(String mandateFileUrl) {
		this.mandateFileUrl = mandateFileUrl;
	}
	
	
	
}
