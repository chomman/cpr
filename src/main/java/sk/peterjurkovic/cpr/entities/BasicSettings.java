package sk.peterjurkovic.cpr.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;


@Entity
@Table(name = "basic_settings")
@Inheritance(strategy = InheritanceType.JOINED)
public class BasicSettings extends AbstractEntity {

	private static final long serialVersionUID = 202201311391L;
	
	private Long id;
	
	private String systemName;
	
	private String ownerName;
	
	private String systemEmail;
	
	private String googleAnalyticsTrackingCode;
	
	private String csnOnlineUrl;

	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name ="system_name")
	@Length(min =1, max = 255, message = "Název systému musí být vyplněn")
	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	
	@Column(name ="owner_name")
	@Length(min =1, max = 255, message = "Název ITC musí být vyplněn")
	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	
	@Email
	@Column(name ="main_system_email", length = 50)
	@Length(min =1, max = 50, message = "Email je nesprávane vyplněn")
	public String getSystemEmail() {
		return systemEmail;
	}

	public void setSystemEmail(String systemEmail) {
		this.systemEmail = systemEmail;
	}
	
	@Column(name ="google_analytics_code")
	@Type(type = "text")
	public String getGoogleAnalyticsTrackingCode() {
		return googleAnalyticsTrackingCode;
	}
	
	
	public void setGoogleAnalyticsTrackingCode(String googleAnalyticsTrackingCode) {
		this.googleAnalyticsTrackingCode = googleAnalyticsTrackingCode;
	}
	
	@Column(name ="csn_online_url")
	public String getcsnOnlineUrl() {
		return csnOnlineUrl;
	}

	public void setcsnOnlineUrl(String csnOnlineUrl) {
		this.csnOnlineUrl = csnOnlineUrl;
	}
	
	
	
}
