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
	
	private Csn csn;
	
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
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "csn_id")
	public Csn getCsn() {
		return csn;
	}

	public void setCsn(Csn csn) {
		this.csn = csn;
	}
	
	@Column
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	
	
}
