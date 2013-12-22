package sk.peterjurkovic.cpr.entities;

import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Entita reprezentujuca skupinu vyrobku podla EU vesniku 305/2011
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "standard_group_id_seq", sequenceName = "standard_group_id_seq", initialValue = 1, allocationSize =1)
@Table(name = "standard_group")
public class StandardGroup extends AbstractEntity {

	
	private static final long serialVersionUID = 1985481354L;
	
	private Long id;
	
	private String czechName;
	
	private String englishName;
	
	private CommissionDecision commissionDecision;
	
	private Set<Mandate> mandates;
		
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "standard_group_id_seq")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@NotBlank(message = "Český název skupiny musí být vyplňen")
	@Column(name = "czech_name")
	public String getCzechName() {
		return czechName;
	}

	public void setCzechName(String czechName) {
		this.czechName = czechName;
	}
	
	@Column(name = "english_name")
	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commission_decision_id")
	public CommissionDecision getCommissionDecision() {
		return commissionDecision;
	}

	public void setCommissionDecision(CommissionDecision commissionDecision) {
		this.commissionDecision = commissionDecision;
	}
	

	@OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
	@JoinTable(name = "standard_group_has_mandate")
	public Set<Mandate> getMandates() {
		return mandates;
	}

	public void setMandates(Set<Mandate> mandates) {
		this.mandates = mandates;
	}

	
	
	
	
	
	
	
	
	
}
