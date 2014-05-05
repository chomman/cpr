package cz.nlfnorm.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	private String mandateName;
	private String mandateFileUrl;
	private Set<Mandate> changes;

	
	public Mandate(){
		changes = new HashSet<Mandate>();
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mandate_id_seq")
	public Long getId() {
		return super.getId();
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
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "mandate_has_changes", joinColumns = @JoinColumn(name = "mandate_id"), inverseJoinColumns = @JoinColumn(name = "mandate_change_id"))
	public Set<Mandate> getChanges() {
		return changes;
	}

	public void setChanges(Set<Mandate> changes) {
		this.changes = changes;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((mandateName == null) ? 0 : mandateName.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mandate other = (Mandate) obj;
		if (mandateName == null) {
			if (other.mandateName != null)
				return false;
		} else if (!mandateName.equals(other.mandateName))
			return false;
		return true;
	}
	
}
