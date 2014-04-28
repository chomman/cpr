package sk.peterjurkovic.cpr.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.joda.time.LocalDate;

import sk.peterjurkovic.cpr.enums.OrderStatus;


@Entity
@Table(name="portal_order")
@SequenceGenerator(name = "portal_order_id_seq", sequenceName = "portal_order_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
public class PortalOrder extends AbstractEntity{

	private static final long serialVersionUID = -4311231873883523058L;

	private User user;
	private PortalProduct portalProduct;
	
	private BigDecimal price;
	private BigDecimal vat;
	private OrderStatus orderStatus;
	private LocalDate dateOfActivation;
	private Boolean emailSent;
	
	private String firstName;
	private String lastName;
	
	private String city;
	private String street;
	private String zip;

	private String companyName;
	private String ico;
	private String dic;
	
	private String phone;
	private String email;
	
	private String ipAddress;
	private String note;
	
	public PortalOrder(){
		this.orderStatus = OrderStatus.PENDING;
		this.emailSent = false;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "portal_order_id_seq")
	@Override
	public Long getId(){
		return super.getId();
	}
	
	@NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@NotNull(message = "{error.protalProduct.empty}")
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portal_product", nullable = false)
	public PortalProduct getPortalProduct() {
		return portalProduct;
	}

	public void setPortalProduct(PortalProduct portalProduct) {
		this.portalProduct = portalProduct;
	}

	
	@NotNull(message = "{error.portalService.price}")
	@Range(min = 0, max = 100000, message = "{error.portalService.price.range}")
	@Column(name = "price", nullable = false)
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@Column(name = "vat")
	public BigDecimal getVat() {
		return vat;
	}

	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}
	
	@NotEmpty(message = "{error.firstName}")
	@Column(name = "first_name", length = 50)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@NotEmpty(message = "{error.lastName}")
	@Column(name = "last_name", length = 50)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Column(name = "city", length = 50)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name = "street", length = 50)
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	
	@Pattern(regexp = "(^\\d{3}\\s?\\d{2}$|)*", message = "{error.zip}")
	@Column(name = "zip", length = 6)
	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
	@Column(name = "company_name", length = 50)
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Pattern(regexp = "(^\\d{8}$|)*",message = "{error.ico}")
	@Column(name = "ico", length = 8)
	public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}
	
	@Column(name = "dic", length = 15)
	public String getDic() {
		return dic;
	}

	public void setDic(String dic) {
		this.dic = dic;
	}
	
	@Pattern(regexp = "(^[+]?[()/0-9. -]{9,}$|)*", message = "{error.phone}")
	@Column(name = "phone", length = 25)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Email(message = "{error.email}")
	@Column(name = "email", length = 50)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "note", length = 300)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Enumerated(value = EnumType.STRING)
	@Column(name = "order_status", length = 25)
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	@Column(name = "date_of_activation")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	public LocalDate getDateOfActivation() {
		return dateOfActivation;
	}

	public void setDateOfActivation(LocalDate dateOfActivation) {
		this.dateOfActivation = dateOfActivation;
	}
	
	@Column(name = "email_sent")
	public Boolean getEmailSent() {
		return emailSent;
	}

	public void setEmailSent(Boolean emailSent) {
		this.emailSent = emailSent;
	}
	
	@Column(name = "ip_address", length = 45)
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Transient
	@Override
	public String getCode() {
		return null;
	}
	
	@Transient
	@Override
	public Boolean getEnabled() {
		return null;
	}
}
