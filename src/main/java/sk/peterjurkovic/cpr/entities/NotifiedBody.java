package sk.peterjurkovic.cpr.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entita reprezentujuca notifikovanu/autorizovanu osobu
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */

@Entity
@Table(name="notified_body")
@Inheritance(strategy = InheritanceType.JOINED)
public class NotifiedBody extends AbstractEntity {

	
	private static final long serialVersionUID = 313L;
	
	private Long id;
	
	private String name;
	
	private String number;
	
	private Address address;
	
	private String webpage;
	
	private String phone;
	
	private String fax;
	
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "name", length = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "number", length = 10)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	public Address getAddress() {
		return address;
	}
	
	
	public void setAddress(Address address) {
		this.address = address;
	}
	
	@Column(name = "web", length = 45)
	public String getWebpage() {
		return webpage;
	}

	public void setWebpage(String webpage) {
		this.webpage = webpage;
	}
	
	@Column(name = "phone", length = 20)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name = "fax", length = 20)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	
	
	
	
	
	
}
