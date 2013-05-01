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

import org.hibernate.validator.constraints.Length;

/**
 * Entita reprezentujuca pozadovany parameter evidovany pri harmonizovanej norme
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Entity
@Table(name="requirement")
@Inheritance(strategy = InheritanceType.JOINED)
public class Requirement extends AbstractEntity {


	private static final long serialVersionUID = 13375L;
	
	private Long id;
	
	private String name;
	
	private String levels;
	
	private String note;
	
	private String section;

	private Standard standard;
	
	private Country country;
	
	private Boolean npd;
	
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "name", length = 100)
	@Length(min =1,  max = 100, message = "Název musí být vyplněn.")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(length = 50)
	@Length( max = 50, message = "Úroveň obsahuje více než 50 znaků")
	public String getLevels() {
		return levels;
	}

	public void setLevels(String levels) {
		this.levels = levels;
	}
	
	@Column(length = 150)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	@Column(length = 50)
	@Length( max = 50, message = "Ustanovení obsahuje více než 50 znaků")
	public String getSection() {
		return section;
	}


	public void setSection(String section) {
		this.section = section;
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
    @JoinColumn(name = "country_id")
	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Boolean getNpd() {
		return npd;
	}

	public void setNpd(Boolean npd) {
		this.npd = npd;
	}
	
	
	
}
