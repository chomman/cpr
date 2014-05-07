package cz.nlfnorm.entities;

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
	
	private String systemEmail;
	private String version;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "basic_settings_id_seq")
	public Long getId() {
		return super.getId();
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
	
	@Column(name ="version", length = 30)
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}


	
	
}
