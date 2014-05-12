package cz.nlfnorm.web.forms.portal;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import cz.nlfnorm.entities.User;
import cz.nlfnorm.entities.UserInfo;

public class BaseUserForm {
	
	private Long id;
	
	@NotEmpty(message = "{error.firstName}")
	private String firstName;
	
	@NotEmpty(message = "{error.lastName}")
	private String lastName;
	
	@Valid
	private UserInfo userInfo = new UserInfo();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	
	public void setUser(final User user){
		id = user.getId();
		firstName = user.getFirstName();
		lastName  = user.getLastName();
		if(user.getUserInfo() == null){
			userInfo = new UserInfo();
		}else{
			userInfo = user.getUserInfo();
		}
	}
}
