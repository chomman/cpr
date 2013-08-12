package sk.peterjurkovic.cpr.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Asociacna entita, ktora prepaja normu a CSN.
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 */


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "standard_has_csn")
public class StandardCsn extends AbstractEntity {

	
	private static final long serialVersionUID = 7791L;
	
	private Long id;
	
	private Standard standard;
	
	private String csnName;

	private String csnOnlineId;
	
	private String note;
	
	
	
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "standard_id")
	public Standard getStandard() {
		return standard;
	}

	public void setStandard(Standard standard) {
		this.standard = standard;
	}
	
	@Column
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	@Column(name ="csn_name", length = 45)
	@NotEmpty(message = "Název ČSN musí být vyplněn.")
	public String getCsnName() {
		return csnName;
	}
	
	public void setCsnName(String csnName) {
		this.csnName = csnName;
	}

	@Column(name = "csn_online_id", length = 10)
	@Pattern(regexp = "(^[0-9]{1,10}$|)*", message = "ČSN online ID obsahuje neplatnou hodnotu")
	public String getCsnOnlineId() {
		return csnOnlineId;
	}

	public void setCsnOnlineId(String csnOnlineId) {
		this.csnOnlineId = csnOnlineId;
	}
	
}