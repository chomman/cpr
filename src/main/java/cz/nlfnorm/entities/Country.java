package cz.nlfnorm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;

/**
 * Entita reprezentujuca clensku krajinu 
 * @author peto
 *
 */
@Entity
@Table(name = "country")
@SequenceGenerator(name = "country_id_seq", sequenceName = "country_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
public class Country extends AbstractEntity {

	
	private static final long serialVersionUID = 13369L;
	
	private String countryName;
	
	private Boolean inEu = true;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "country_id_seq")
	public Long getId() {
		return super.getId();
	}
		
	@Length(min = 1, max = 45, message = "{error.country.name}")
	@Column(name = "country_name", length = 45)
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	@Column(name =  "is_in_eu")
	public Boolean getInEu() {
		return inEu;
	}

	public void setInEu(Boolean inEu) {
		this.inEu = inEu;
	}

	@Transient
	public void merge(final Country country){
		this.setCode(country.getCode());
		this.setCountryName(country.getCountryName());
		this.setInEu(country.getInEu());
	}
	
	
}
