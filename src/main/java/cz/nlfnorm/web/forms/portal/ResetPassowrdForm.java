package cz.nlfnorm.web.forms.portal;

import org.hibernate.validator.constraints.Length;

import com.drew.lang.annotations.NotNull;

import cz.nlfnorm.entities.User;

public class ResetPassowrdForm {
	
	@NotNull
	private Long userId;
	
	@Length(min = 6, max = 50, message = "{error.password}")
	private String newPassword;
	@Length(min = 6, max = 50, message = "{error.password}")
	private String confirmPassword;
	
	public ResetPassowrdForm(){}
	
	public ResetPassowrdForm(final User user){
		this.userId = user.getId();
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
	
}
