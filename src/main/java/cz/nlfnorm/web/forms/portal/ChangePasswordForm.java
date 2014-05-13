package cz.nlfnorm.web.forms.portal;

import org.hibernate.validator.constraints.Length;

import com.drew.lang.annotations.NotNull;

import cz.nlfnorm.entities.User;

public class ChangePasswordForm {
	
	
	@NotNull
	private Long userId;
	
	@Length(min = 6, max = 50, message = "{error.password}")
	private String currentPassword;
	@Length(min = 6, max = 50, message = "{error.password}")
	private String newPassword;
	@Length(min = 6, max = 50, message = "{error.password}")
	private String confirmPassword;
	
	private String redirectUrl;
	
	public ChangePasswordForm(){}
	
	public ChangePasswordForm(User user){
		this.userId = user.getId();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	
	
	
	
	
}
