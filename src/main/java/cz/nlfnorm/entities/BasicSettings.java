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
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

/**
 * Zakladne nastavenia systemu
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Entity
@Table(name = "basic_settings")
@SequenceGenerator(name = "basic_settings_id_seq", sequenceName = "basic_settings_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
public class BasicSettings extends AbstractEntity {

	private static final long serialVersionUID = 202201311391L;
	
	private String systemEmail;
	private String version;
	
	private String companyName;
	private String city;
	private String street;
	private String zip;
	
	private String ico;
	private String dic;
	
	private String phone;
	private String invoiceEmai;
	
	private String czAccountNumber;
	private String czSwift;
	private String czIban;
	
	private String euAccountNumber;
	private String euSwift;
	private String euIban;	
	
	private String plasticPortalEmail;
	
	private String portalAdminName;
	private String portalAdminContact;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "basic_settings_id_seq")
	public Long getId() {
		return super.getId();
	}
	
	
	@Email
	@Column(name ="main_system_email", length = 50)
	@Length(min =1, max = 50, message = "Email je nesprávane vyplněn")
	public String getSystemEmail() {
		return systemEmail;
	}

	public void setSystemEmail(String systemEmail) {
		this.systemEmail = systemEmail;
	}
	
	@Column(name ="version", length = 30)
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name ="company_name", length = 100)
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name ="city", length = 50)
	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	@Column(name ="street", length = 80)
	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}
	
	@Pattern(regexp = "(^\\d{3}\\s?\\d{2}$|)*", message = "{error.zip}")
	@Column(name ="zip", length = 6)
	public String getZip() {
		return zip;
	}


	public void setZip(String zip) {
		this.zip = zip;
	}
	
	@Pattern(regexp = "(^\\d{8}$|)*",message = "{error.ico}")
	@Column(name ="ico", length = 10)
	public String getIco() {
		return ico;
	}


	public void setIco(String ico) {
		this.ico = ico;
	}

	@Column(name ="dic", length = 10)
	public String getDic() {
		return dic;
	}


	public void setDic(String dic) {
		this.dic = dic;
	}

	@Pattern(regexp = "(^[+]?[()/0-9. -]{9,}$|)*", message = "{error.phone}")
	@Column(name ="phone", length = 20)
	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Email(message = "{error.email}")
	@Column(name ="invoice_email", length = 35)
	public String getInvoiceEmai() {
		return invoiceEmai;
	}


	public void setInvoiceEmai(String invoiceEmai) {
		this.invoiceEmai = invoiceEmai;
	}

	@Column(name ="cz_account_number", length = 25)
	public String getCzAccountNumber() {
		return czAccountNumber;
	}


	public void setCzAccountNumber(String czAccountNumber) {
		this.czAccountNumber = czAccountNumber;
	}

	@Column(name ="cz_swift", length = 8)
	public String getCzSwift() {
		return czSwift;
	}


	public void setCzSwift(String czSwift) {
		this.czSwift = czSwift;
	}

	
	@Column(name ="cz_iban", length = 34)
	public String getCzIban() {
		return czIban;
	}


	public void setCzIban(String czIban) {
		this.czIban = czIban;
	}

	@Column(name ="eu_account_number", length = 25)
	public String getEuAccountNumber() {
		return euAccountNumber;
	}


	public void setEuAccountNumber(String euAccountNumber) {
		this.euAccountNumber = euAccountNumber;
	}

	@Column(name ="eu_swift", length = 8)
	public String getEuSwift() {
		return euSwift;
	}


	public void setEuSwift(String euSwift) {
		this.euSwift = euSwift;
	}

	@Column(name ="eu_iban", length = 34)
	public String getEuIban() {
		return euIban;
	}


	public void setEuIban(String euIban) {
		this.euIban = euIban;
	}


	@Column(name ="plastic_portal_email", length = 40)
	public String getPlasticPortalEmail() {
		return plasticPortalEmail;
	}

	public void setPlasticPortalEmail(String plasticPortalEmail) {
		this.plasticPortalEmail = plasticPortalEmail;
	}

	
	@Column(name ="portal_admin_name", length = 50)
	public String getPortalAdminName() {
		return portalAdminName;
	}


	public void setPortalAdminName(String portalAdminName) {
		this.portalAdminName = portalAdminName;
	}

	
	@Column(name ="portal_admin_contacts", length = 80)
	public String getPortalAdminContact() {
		return portalAdminContact;
	}


	public void setPortalAdminContact(String portalAdminContact) {
		this.portalAdminContact = portalAdminContact;
	}

	
		
	
}
