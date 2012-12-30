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

@Entity
@Table(name = "address")
@Inheritance(strategy = InheritanceType.JOINED)
public class Address extends AbstractEntity {

	
	private static final long serialVersionUID = 198926L;
	
	private Long id;
	
	private String city;
	
	private String street;
	
	private String zip;
	
	private Country country;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}


	@Column( name = "city", length= 50)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Column( name = "street", length= 100)
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	
	
	 @Column(name = "zip", length = 6)
	public String getZip() {
		return (zip == null ? "" : zip);
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
	

}
