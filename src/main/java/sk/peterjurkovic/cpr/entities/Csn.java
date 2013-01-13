package sk.peterjurkovic.cpr.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entita reprezentujuca cesku technicku normu
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */

@Entity
@Table(name = "csn")
@Inheritance(strategy = InheritanceType.JOINED)
public class Csn extends AbstractEntity {

	private static final long serialVersionUID = 1999821354L;
	
	private Long id;
	
	private String csnName;

	private String csnOnlineId;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name ="csn_name", length = 45)
	@NotEmpty(message = "Název ČSN musí být vyplněn.")
	@Length(min = 1, max = 45, message = "Název ČSN musí být vyplněn.")
	public String getCsnName() {
		return csnName;
	}

	public void setCsnName(String csnName) {
		csnName = this.csnName;
	}

	@Column(name = "csn_online_id", length = 10)
	@Pattern(regexp = "(^[0-9]{1,10}$|)*", message = "ČSN online ID obsahuje neplatnou hodnotu")
	public String getCsnOnlineId() {
		return csnOnlineId;
	}

	public void setCsnOnlineId(String csnOnlineId) {
		csnOnlineId = this.csnOnlineId;
	}
	
	
}
