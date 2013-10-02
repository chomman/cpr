package sk.peterjurkovic.cpr.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

/**
 * Zakladne nastavenia systemu
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Entity
@Table(name = "basic_settings")
@SequenceGenerator(name = "basic_settings_id_seq", sequenceName = "basic_settings_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
public class BasicSettings extends AbstractEntity {

	private static final long serialVersionUID = 202201311391L;
	
	private Long id;
	
	private String systemName;
	
	private String headerTitle;
	
	private String ownerName;
	
	private String systemEmail;
	
	private String googleAnalyticsTrackingCode;
	
	private String csnOnlineUrl;
	
	private String version;

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "basic_settings_id_seq")
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
	
	@Column(name ="header_title")
	@Length(min =1, max = 150, message = "Titulek hlavičky veřejné sekce musí být vyplněn")
	public String getHeaderTitle() {
		return headerTitle;
	}

	public void setHeaderTitle(String headerTitle) {
		this.headerTitle = headerTitle;
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
	public String getCsnOnlineUrl() {
		return csnOnlineUrl;
	}

	public void setCsnOnlineUrl(String csnOnlineUrl) {
		this.csnOnlineUrl = csnOnlineUrl;
	}

	@Column(name ="version", length = 30)
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	
	
}
