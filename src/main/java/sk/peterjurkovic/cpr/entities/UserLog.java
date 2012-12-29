package sk.peterjurkovic.cpr.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.jadira.usertype.dateandtime.joda.PersistentDateTime;
import org.joda.time.DateTime;


@Entity
@Table(name = "users_log")
@TypeDefs( { @TypeDef(name = "jodaDateTime", typeClass = PersistentDateTime.class) })
public class UserLog implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 15986L;
	private Long id;
    private DateTime loginDateAndTime;
    private DateTime logoutDateAndTime;
    private String userName;
    private Boolean loginSuccess;
    private String ipAddress;
    private User user;
    private String sessionId;
    
    @Id
    @Column(name = "id")
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name = "login_date")
    @Type(type = "jodaDateTime")
	public DateTime getLoginDateAndTime() {
		return loginDateAndTime;
	}

	public void setLoginDateAndTime(DateTime loginDateAndTime) {
		this.loginDateAndTime = loginDateAndTime;
	}
	
	@Column(name = "logout_date")
    @Type(type = "jodaDateTime")
	public DateTime getLogoutDateAndTime() {
		return logoutDateAndTime;
	}

	public void setLogoutDateAndTime(DateTime logoutDateAndTime) {
		this.logoutDateAndTime = logoutDateAndTime;
	}
	
	@Column(name = "user_name", length = 50)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "login_success")
	public Boolean getLoginSuccess() {
		return loginSuccess;
	}

	public void setLoginSuccess(Boolean loginSuccess) {
		this.loginSuccess = loginSuccess;
	}
	
	@Column(name = "ip_address", length = 100)
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	@ManyToOne
    @JoinColumn(name = "id_user")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Column(name = "session_id", length = 100)
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}	
    
    
    
}	
