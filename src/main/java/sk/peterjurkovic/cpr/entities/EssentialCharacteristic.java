package sk.peterjurkovic.cpr.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
/**
 * Zakladna charakteristika daneho DoP
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Entity
@Table(name="essential_characteristic")
public class EssentialCharacteristic {
	
	private Long id;
	
	private Requirement requirement;
	
	private DeclarationOfPerformance declarationOfPerformance;
	
	private String value;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requirement_id")
	public Requirement getRequirement() {
		return requirement;
	}

	public void setRequirement(Requirement requirement) {
		this.requirement = requirement;
	}
	
	@Length(max = 255, message = "Hodnota je příliš dlouhá.")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "declaration_of_performance_id")
	public DeclarationOfPerformance getDeclarationOfPerformance() {
		return declarationOfPerformance;
	}

	public void setDeclarationOfPerformance(
			DeclarationOfPerformance declarationOfPerformance) {
		this.declarationOfPerformance = declarationOfPerformance;
	}

	
	
	
	
	
}
