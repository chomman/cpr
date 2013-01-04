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
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entita reprezentujuca notifikovanu/autorizovanu osobu
 * 
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
	
	private String notifiedBodyCode;
	
	private Address address;
	
	private String webpage;
	
	private String phone;
	
	private String fax;
	
	private String email;
	
	private Boolean etaCertificationAllowed; 
	
	private String description;
	
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "name", length = 100)
	@NotEmpty
	@Length(max = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	@NotEmpty
	@Column(name = "notified_body_code", length = 25)
	@Length(max = 25)
	public String getNotifiedBodyCode() {
		return notifiedBodyCode;
	}

	public void setNotifiedBodyCode(String notifiedBodyCode) {
		this.notifiedBodyCode = notifiedBodyCode;
	}
	
	@Valid
	@ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	public Address getAddress() {
		return address;
	}
	
	
	public void setAddress(Address address) {
		this.address = address;
	}
	
	@Pattern(regexp = "^(https?://)?[a-z_0-9\\-\\.]+\\.[a-z]{2,4}.*$")
	@Column(name = "web", length = 50)
	public String getWebpage() {
		return webpage;
	}

	public void setWebpage(String webpage) {
		this.webpage = webpage;
	}
	
	@Length(min = 9, max = 20)
	@Pattern(regexp ="[0-9\\+\\s]", message = "Telefón je v chybném tvaru.")
	@Column(name = "phone", length = 20)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min = 9, max = 20)
	@Column(name = "fax", length = 20)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column
	@Type(type = "text")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "is_eta_certification_allowed")
	public Boolean getEtaCertificationAllowed() {
		return etaCertificationAllowed;
	}

	public void setEtaCertificationAllowed(Boolean etaCertificationAllowed) {
		this.etaCertificationAllowed = etaCertificationAllowed;
	}
	
	
	@Length(max = 45)
	@Email( message = "Email je v chybném tvaru.")
	@Column(length = 45)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "NotifiedBody [id=" + id + ", name=" + name
				+ ", notifiedBodyCode=" + notifiedBodyCode + ", address="
				+ address + ", webpage=" + webpage + ", phone=" + phone
				+ ", fax=" + fax + ", email=" + email
				+ ", etaCertificationAllowed=" + etaCertificationAllowed + "]";
	}
	
	
	
	
	
	
	
}
