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

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

/**
 * Entita reprezentujuca mandat
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Entity
@Table(name ="mandate")
@SequenceGenerator(name = "mandate_id_seq", sequenceName = "mandate_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
public class Mandate extends AbstractEntity {

	
	private static final long serialVersionUID = 815498413L;
	private Long id;
	private String mandateName;
	private String mandateFileUrl;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mandate_id_seq")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@NotEmpty(message = "Název mandátu musí být vyplněn")
	@Length( max = 25, message= "Název mandátu může mít max. 25 znaků")
	@Column(name="mandate_name", length = 25)
	public String getMandateName() {
		return mandateName;
	}

	public void setMandateName(String mandateName) {
		this.mandateName = mandateName;
	}
	
	@URL(message = "Adresa odkazu je v chybném tvaru")
	@Column(name = "mandate_file_url")
	public String getMandateFileUrl() {
		return mandateFileUrl;
	}

	public void setMandateFileUrl(String mandateFileUrl) {
		this.mandateFileUrl = mandateFileUrl;
	}

	
}
