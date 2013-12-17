package sk.peterjurkovic.cpr.entities;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
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
@SequenceGenerator(name = "notified_body_id_seq", sequenceName = "notified_body_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
public class NotifiedBody extends AbstractEntity {

	
	private static final long serialVersionUID = 313L;
	
	private Long id;
	
	private String name;
	
	private String noCode;
	
	private String aoCode;
	
	private Address address;
	
	private Country country;
	
	private String webpage;
	
	private String phone;
	
	private String fax;
	
	private String email;
	
	private Boolean etaCertificationAllowed; 
	
	private String description;
	
	private String nandoCode;
	
	public NotifiedBody(){
		setEnabled(Boolean.TRUE);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notified_body_id_seq")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "name", length = 200)
	@Length(min =1, max = 200, message = "Název musí být vyplněn.")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	@NotEmpty(message = "Identifikátor osoby musí být vyplněno")
	@Column(name = "no_code", length = 25)
	@Length(max = 25, message = "Číslo osoby může mít max. 25 znaků.")
	public String getNoCode() {
		return noCode;
	}

	public void setNoCode(String noCode) {
		this.noCode = noCode;
	}
	
	@Column(name = "ao_code", length = 25)
	@Length(max = 25, message = "Číslo osoby může mít max. 25 znaků.")	
	public String getAoCode() {
		return aoCode;
	}

	public void setAoCode(String aoCode) {
		this.aoCode = aoCode;
	}

	@Valid
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	public Address getAddress() {
		return address;
	}
	
	
	public void setAddress(Address address) {
		this.address = address;
	}
	
	@Pattern(regexp = "(^(https?://)?[a-z_0-9\\-\\.]+\\.[a-z]{2,4}.*$|)*", message = "Webová stránka je v chybném tvaru.")
	@Column(name = "web", length = 100)
	public String getWebpage() {
		return webpage;
	}

	public void setWebpage(String webpage) {
		this.webpage = webpage;
	}
	

	@Pattern(regexp ="(^[0-9\\+\\s]{9,20}$|)*", message = "Telefón je v chybném tvaru.")
	@Column(name = "phone", length = 20)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Pattern(regexp ="(^[0-9\\+\\s]{9,20}$|)*", message = "Fax je v chybném tvaru.")
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
	
	@Column(name = "nando_code")
	public String getNandoCode() {
		return nandoCode;
	}

	public void setNandoCode(String nandoCode) {
		this.nandoCode = nandoCode;
	}

	@Override
	public String toString() {
		return "NotifiedBody [id=" + id + ", name=" + name
				+ ", notifiedBodyCode=" + noCode + ", address="
				+ address + ", webpage=" + webpage + ", phone=" + phone
				+ ", fax=" + fax + ", email=" + email
				+ ", etaCertificationAllowed=" + etaCertificationAllowed + "]";
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
