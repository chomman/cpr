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

@Entity
@Table(name = "address")
@Inheritance(strategy = InheritanceType.JOINED)
public class Address extends AbstractEntity {

	
	private static final long serialVersionUID = 198926L;
	
	private Long id;
	
	private String city;
	
	private String street;
	
	private String zip;
	

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}


	@Length(max = 50, message = "Město může mít max. 50 znaků")
	@Column( name = "city", length= 50)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(max = 100, message = "Ulice může mít max. 100 znaků")
	@Column( name = "street", length= 100)
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	
	@Pattern(regexp = "(^\\d{3}\\s?\\d{2}$|)*", message = "PSČ je v chybném tvaru")
	@Column(name = "zip", length = 6)
	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

}
