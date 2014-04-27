package sk.peterjurkovic.cpr.web.forms.portal;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.entities.UserInfo;

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
	@Valid
	private UserInfo userInfo;
	
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
}
