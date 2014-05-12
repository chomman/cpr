package cz.nlfnorm.web.forms.portal;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.entities.PortalCurrency;
import cz.nlfnorm.entities.PortalOrder;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.enums.PortalOrderSource;

public class PortalUserForm extends BaseUserForm{
	
	
	@NotEmpty(message = "{error.email.empty}")
	@Email(message = "{error.email}")
	private String email;
	
	@Length(min = 6, message = "{error.firstName}")
	private String password;
	
	@Length(min = 6, message = "{error.password}")
	private String confirmPassword;
	
	@NotNull
	private PortalCurrency portalCurrency = PortalCurrency.CZK;
	
	@NotNull
	private PortalOrderSource portalOrderSource = PortalOrderSource.NLFNORM;
		
	@NotEmpty(message = "{error.protalProduct.empty}")
	private List<Long> portalProductItems;
	
	public PortalUserForm(){}
	
	public PortalUserForm(PortalCurrency currency){
		this.portalCurrency = currency;
		this.portalProductItems = new ArrayList<Long>();
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
		user.setFirstName(getFirstName());
		user.setLastName(getLastName());
		user.setEmail(email);
		getUserInfo().setUser(user);
		user.setUserInfo(getUserInfo());
		return user;
	}
	
	public PortalOrder toPortalOrder(){
		PortalOrder order = new PortalOrder();
		order.setVat(Constants.VAT);
		order.setCurrency(getPortalCurrency());
		order.setPortalOrderSource(getPortalOrderSource());
		order.setEmail(getEmail());
		order.setPhone(getUserInfo().getPhone());
		order.setFirstName(getFirstName());
		order.setLastName(getLastName());
		
		order.setCity(getUserInfo().getCity());
		order.setZip(getUserInfo().getZip());
		order.setStreet(getUserInfo().getStreet());
		
		order.setCompanyName(getUserInfo().getCompanyName());
		order.setDic(getUserInfo().getDic());
		order.setIco(getUserInfo().getIco());
		return order;
	}

	@Override
	public String toString() {
		return "PortalUserForm [id=" + getId() + ", email=" + email + ", firstName="
				+ getFirstName() + ", lastName=" + getLastName() + ", password="
				+ (password != null ? password.length() : "[NULL]" ) + ", portalCurrency=" + portalCurrency + ", portalOrderSource="
				+ portalOrderSource + ", userInfo=" + getUserInfo()
				+ ", portalProductItems=" + portalProductItems + "]";
	}
	
		
}
