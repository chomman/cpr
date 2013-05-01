package sk.peterjurkovic.cpr.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

/**
 * Entita reprezentujuca clensku krajinu 
 * @author peto
 *
 */
@Entity
@Table(name = "country")
@Inheritance(strategy = InheritanceType.JOINED)
public class Country extends AbstractEntity {

	
	private static final long serialVersionUID = 13369L;
	
	private Long id;
	
	private String countryName;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Length(min = 1, max = 45, message = "Název státu musí být vyplněn")
	@Column(name = "country_name", length = 45)
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	@Override
	public String toString() {
		return "Country [id=" + id + ", countryName=" + countryName + "]";
	}
	
	
	
}
