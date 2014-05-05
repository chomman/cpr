package cz.nlfnorm.web.forms.portal;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.entities.PortalCurrency;
import cz.nlfnorm.entities.PortalOrder;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.entities.UserInfo;
import cz.nlfnorm.enums.PortalOrderSource;

public class PortalUserForm {
	
	private Long id;
	
	@NotEmpty(message = "{error.email.empty}")
	@Email(message = "{error.email}")
	private String email;
	
	@NotEmpty(message = "{error.firstName}")
	private String firstName;
	
	@NotEmpty(message = "{error.lastName}")
	private String lastName;
	
	@Length(min = 6, message = "{error.firstName}")
	private String password;
	
	@Length(min = 6, message = "{error.password}")
	private String confirmPassword;
	
	@NotNull
	private PortalCurrency portalCurrency = PortalCurrency.CZK;
	
	@NotNull
	private PortalOrderSource portalOrderSource = PortalOrderSource.NLFNORM;
	
	@Valid
	private UserInfo userInfo;
	
	@NotEmpty(message = "{error.protalProduct.empty}")
	private List<Long> portalProductItems;
	
	public PortalUserForm(){}
	
	public PortalUserForm(PortalCurrency currency){
		this.portalCurrency = currency;
		this.portalProductItems = new ArrayList<Long>();
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
		
	public PortalCurrency getPortalCurrency() {
		return portalCurrency;
	}
	public void setPortalCurrency(PortalCurrency portalCurrency) {
		this.portalCurrency = portalCurrency;
	}
		
	public List<Long> getPortalProductItems() {
		return portalProductItems;
	}

	public void setPortalProductItems(List<Long> portalProductItems) {
		this.portalProductItems = portalProductItems;
	}
	
	public PortalOrderSource getPortalOrderSource() {
		return portalOrderSource;
	}

	public void setPortalOrderSource(PortalOrderSource portalOrderSource) {
		this.portalOrderSource = portalOrderSource;
	}
	
	

	public User toUser(){
		User user = new User();
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		userInfo.setUser(user);
		user.setUserInfo(getUserInfo());
		return user;
	}
	
	public PortalOrder toPortalOrder(){
		PortalOrder order = new PortalOrder();
		order.setVat(Constants.VAT);
		order.setCurrency(getPortalCurrency());
		order.setPortalOrderSource(getPortalOrderSource());
		order.setEmail(getEmail());
		order.setPhone(userInfo.getPhone());
		order.setFirstName(getFirstName());
		order.setLastName(getLastName());
		
		order.setCity(userInfo.getCity());
		order.setZip(userInfo.getZip());
		order.setStreet(userInfo.getStreet());
		
		order.setCompanyName(userInfo.getCompanyName());
		order.setDic(userInfo.getDic());
		order.setIco(userInfo.getIco());
		return order;
	}

	@Override
	public String toString() {
		return "PortalUserForm [id=" + id + ", email=" + email + ", firstName="
				+ firstName + ", lastName=" + lastName + ", password="
				+ (password != null ? password.length() : "[NULL]" ) + ", portalCurrency=" + portalCurrency + ", portalOrderSource="
				+ portalOrderSource + ", userInfo=" + userInfo
				+ ", portalProductItems=" + portalProductItems + "]";
	}
	
		
}
