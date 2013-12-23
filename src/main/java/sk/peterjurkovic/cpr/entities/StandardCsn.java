package sk.peterjurkovic.cpr.entities;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Asociacna entita, ktora prepaja normu a CSN.
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 */


@Entity
@SequenceGenerator(name = "standard_has_csn_id_seq", sequenceName = "standard_has_csn_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "standard_csn")
public class StandardCsn extends AbstractEntity {

	
	private static final long serialVersionUID = 7791L;
	
	private Long id;
		
	private String csnName;

	private String csnOnlineId;
	
	private Boolean canceled = false;
	
	private String note;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "standard_has_csn_id_seq")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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

	public Boolean getCanceled() {
		return canceled;
	}
	
	@Column(name = "is_canceled")
	public void setCanceled(Boolean canceled) {
		this.canceled = canceled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((csnOnlineId == null) ? 0 : csnOnlineId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		StandardCsn other = (StandardCsn) obj;
		if (csnOnlineId == null) {
			if (other.csnOnlineId != null)
				return false;
		} else if (!csnOnlineId.equals(other.csnOnlineId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
