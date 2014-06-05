package cz.nlfnorm.web.forms.portal;

import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.validator.constraints.Length;

import cz.nlfnorm.entities.PortalCurrency;
import cz.nlfnorm.entities.User;

public class PortalUserForm extends PortalOrderForm{
	
	
	@Length(min = 6, message = "{error.firstName}")
	private String password;
	
	@Length(min = 6, message = "{error.password}")
	private String confirmPassword;
	
	
	public PortalUserForm(){
		super();
	}
	
	public PortalUserForm(PortalCurrency currency){
		super(currency);
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
			
	public User toUser(){
		User user = new User();
		user.setPassword(password);
		user.setFirstName(StringEscapeUtils.unescapeHtml(getFirstName()));
		user.setLastName(StringEscapeUtils.unescapeHtml(getLastName()));
		user.setEmail(getEmail());
		user.setEnabled(true);
		getUserInfo().setUser(user);
		user.setUserInfo(getUserInfo());
		return user;
	}		
}
