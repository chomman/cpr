package cz.nlfnorm.web.forms.portal;

import org.hibernate.validator.constraints.Length;

import cz.nlfnorm.entities.User;

public class ChangePasswordForm extends ResetPassowrdForm{
		
	@Length(min = 6, max = 50, message = "{error.password}")
	private String currentPassword;
	
	
	private String redirectUrl;
	
	public ChangePasswordForm(){}
	
	public ChangePasswordForm(User user){
		super(user);
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	
	
	
	
	
}
