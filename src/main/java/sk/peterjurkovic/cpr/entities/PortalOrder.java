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

import sk.peterjurkovic.cpr.enums.OrderStatus;


@Entity
@Table(name="portal_order")
@SequenceGenerator(name = "portal_order_id_seq", sequenceName = "portal_order_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
public class PortalOrder extends AbstractEntity{

	private static final long serialVersionUID = -4311231873883523058L;

	private User user;
	private PortalService portalService;
	
	private BigDecimal price;
	private BigDecimal vat;
	private OrderStatus orderStatus;
	
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
	
	private String note;
	
	public PortalOrder(){
		this.orderStatus = OrderStatus.PENDING;
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

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portal_service")
	public PortalService getPortalService() {
		return portalService;
	}

	public void setPortalService(PortalService portalService) {
		this.portalService = portalService;
	}
	
	@Column(name = "price")
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

	@Column(name = "first_name", length = 50)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
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

	@Column(name = "phone", length = 25)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

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

	@Transient
	@Override
	public String getCode() {
		throw new UnsupportedOperationException("not supported");
	}
	
	@Transient
	@Override
	public Boolean getEnabled() {
		throw new UnsupportedOperationException("not supported");
	}
}
