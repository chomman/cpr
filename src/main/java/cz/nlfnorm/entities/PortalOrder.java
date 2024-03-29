package cz.nlfnorm.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang.Validate;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;

import cz.nlfnorm.enums.OrderStatus;
import cz.nlfnorm.enums.PortalCountry;
import cz.nlfnorm.enums.PortalOrderSource;
import cz.nlfnorm.enums.PortalProductType;
import cz.nlfnorm.utils.PriceUtils;
import cz.nlfnorm.utils.RequestUtils;


@Entity
@Table(name="portal_order")
@SequenceGenerator(name = "portal_order_id_seq", sequenceName = "portal_order_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
public class PortalOrder extends AbstractEntity{

	private static final long serialVersionUID = -4311231873883523058L;

	private User user;
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
	private PortalCurrency currency;
	
	private Set<PortalOrderItem> orderItems;
	private PortalOrderSource portalOrderSource;
	
	private String userAgent;
	private String referer;
	private PortalCountry portalCountry;
	
	private String duzp;
	private String paymentDate;
	
	private boolean deleted;
	
	public PortalOrder(){
		this.orderStatus = OrderStatus.PENDING;
		this.emailSent = false;
		this.orderItems = new HashSet<PortalOrderItem>();
		this.portalOrderSource = PortalOrderSource.NLFNORM;
		this.currency = PortalCurrency.CZK;
		this.portalCountry = PortalCountry.CR;
		this.deleted = false;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "portal_order_id_seq")
	@Override
	public Long getId(){
		return super.getId();
	}
	
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@OrderBy(clause = "id ASC")
	@OneToMany(mappedBy = "portalOrder", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
	public Set<PortalOrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Set<PortalOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
	@Column(name = "vat", precision = 3, scale = 2)
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

	@Enumerated(value = EnumType.STRING)
	@Column(name = "order_status", length = 25)
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "order_source", length = 15)	
	public PortalOrderSource getPortalOrderSource() {
		return portalOrderSource;
	}

	public void setPortalOrderSource(PortalOrderSource portalOrderSource) {
		this.portalOrderSource = portalOrderSource;
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
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "currency", length = 3, nullable = false)	
	public PortalCurrency getCurrency() {
		return currency;
	}

	public void setCurrency(PortalCurrency currency) {
		this.currency = currency;
	}	
	
	@Column(name = "user_agent", length = 150)
	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	@Column(name = "referer", length = 250)
	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "portal_country", length = 2)
	public PortalCountry getPortalCountry() {
		return portalCountry;
	}

	public void setPortalCountry(PortalCountry portalCountry) {
		this.portalCountry = portalCountry;
	}
	
	/**
	 * Datum uskutečnění zdanitelného plnění
	 * @return datum vo formate dd.MM.yyyy
	 */
	@Column(name = "duzp", length = 10)
	public String getDuzp() {
		return duzp;
	}
	
	public void setDuzp(String duzp) {
		this.duzp = duzp;
	}
	/**
	 * Datum uhrady objednavky
	 * @return datum vo formate dd.MM.yyyy
	 */
	@Column(name = "paymen_tDate", length = 10)
	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	@Column(name = "is_deleted")	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Transient
	@Override
	public Boolean getEnabled() {
		return null;
	}
	
	@Transient
	public boolean isPayed(){
		return orderStatus != null && orderStatus.equals(OrderStatus.PAYED);
	}
	
	@Transient
	public String getFormatedVat(){
		return PriceUtils.getFormatedVat(vat);
	}
	
		
	@Transient
	public boolean getIsActivated(){
		return dateOfActivation != null;
	}
	
	@Transient 
	public void merge(PortalOrder form){
		setOrderStatus(form.getOrderStatus());
		setFirstName(form.getFirstName());
		setLastName(form.getLastName());
		setCity(form.getCity());
		setStreet(form.getStreet());
		setZip(form.getZip());
		
		setCompanyName(form.getCompanyName());
		setIco(form.getIco());
		setDic(form.getDic());
		
		setEmail(form.getEmail());
		setPhone(form.getPhone());
		setPortalCountry(form.getPortalCountry());
		setDuzp(form.getDuzp());
		setPaymentDate(form.getPaymentDate());
	}
	
	@Transient 
	public boolean removeOrderItem(final Long id){
		Validate.notNull(id);
		Iterator<PortalOrderItem> i = orderItems.iterator();
		while(i.hasNext()){
			PortalOrderItem item = i.next();
			if(item.getId().equals(id)){
				return orderItems.remove(item);
			}
		}
		return false;
	}
	
	@Transient
	public BigDecimal getTotalPrice(){
		BigDecimal totalPrice = new BigDecimal("0");
		for(PortalOrderItem item : getOrderItems()){
			totalPrice = totalPrice.add(item.getPrice());
		}
		return totalPrice;
	}
	
	@Transient
	public BigDecimal getTotalPriceWithVat(){
		BigDecimal totalPrice = new BigDecimal("0");
		for(PortalOrderItem item : getOrderItems()){
			totalPrice = totalPrice.add(item.getPriceWithVat());
		}
		return totalPrice;
	}
	
	@Transient
	public BigDecimal getVatPriceValue(){
		return getTotalPriceWithVat().subtract(getTotalPrice());
	}
	
	@Transient
	public String getBrowser(){
		return RequestUtils.getBrowserName(getUserAgent());
	}
	
	@Transient
	public PortalProduct getRegistrationPortalProduct(){
		for(PortalOrderItem item : getOrderItems()){
			PortalProductType type = item.getPortalProduct().getPortalProductType();
			if(type.equals(PortalProductType.REGISTRATION)){
				return item.getPortalProduct();
			}
		}
		return null;
	}
	
	@Transient
	public List<PortalProduct> getPublications(){
		List<PortalProduct> products = new ArrayList<PortalProduct>();
		for(PortalOrderItem item : getOrderItems()){
			PortalProductType type = item.getPortalProduct().getPortalProductType();
			if(type.equals(PortalProductType.PUBLICATION)){
				products.add(item.getPortalProduct());
			}
		}
		return products;
	}
	
	@Transient
	public String getVatPriceValueWithCurrency(){
		return getVatPriceValue() + " " + currency.getSymbol();
	}
	
	@Transient
	public String getTotalPriceWithCurrency(){
		return getTotalPrice().toString() + " " + currency.getSymbol();
	}
	
	@Transient
	public String getTotalPriceWithVatAndCurrency(){
		return getTotalPriceWithVat().toString() + " " + currency.getSymbol();
	}
	
	@Transient
	public boolean getIsInEuro(){
		return currency.equals(PortalCurrency.EUR);
	}
	
	@Transient
	public String getPercentageVat(){
		return PriceUtils.getFormatedVat(vat);
	}
	
	@Transient
	public String getOrderNo(){
		return getId().toString();
	}

	
	
	
}
