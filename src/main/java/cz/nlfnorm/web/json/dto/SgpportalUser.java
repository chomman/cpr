package cz.nlfnorm.web.json.dto;

import org.apache.commons.lang.Validate;
import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cz.nlfnorm.entities.User;
import cz.nlfnorm.web.json.serializers.SgpportalUserSerializer;

@JsonSerialize(using = SgpportalUserSerializer.class)
public class SgpportalUser {
	
	private long userId;
	private String login;
	private String pass;
	private LocalDateTime changed;
	
	public SgpportalUser(){}
	
	public SgpportalUser(User user){
		Validate.notNull(user);
		setUser(user);
	}
	
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public LocalDateTime getChanged() {
		return changed;
	}
	public void setChanged(LocalDateTime changed) {
		this.changed = changed;
	}
	
	public void setUser(User user){
		this.userId = user.getId();
		this.login = user.getEmail();
		this.changed = user.getChanged();
		this.pass = user.getSgpPassword();
	}
	
	
}
