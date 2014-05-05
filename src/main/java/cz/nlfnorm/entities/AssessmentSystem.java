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
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * System posudzovania nemennosti parametrov vyrobkov
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "assessment_system")
@SequenceGenerator(name = "assessment_system_id_seq", sequenceName = "assessment_system_id_seq", initialValue = 1, allocationSize =1)
public class AssessmentSystem extends AbstractEntity {

	
	private static final long serialVersionUID = 9953135441L;
	
	private Long id;
	
	private String name;
	
	private String assessmentSystemCode;
	
	private String declarationOfPerformanceText;
	
	private String description;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assessment_system_id_seq")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "system_code", length = 20)
	public String getAssessmentSystemCode() {
		return assessmentSystemCode;
	}
	

	public void setAssessmentSystemCode(String assessmentSystemCode) {
		this.assessmentSystemCode = assessmentSystemCode;
	}
	
	@Column(name = "description")
	@Type(type = "text")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@NotEmpty(message = "Název systému musí být vyplněn")
	@Length(max = 75, message = "Název systému může mít max. 75 znaků")
	@Column(length = 75)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "dop_text")
	@Type(type = "text")
	public String getDeclarationOfPerformanceText() {
		return declarationOfPerformanceText;
	}

	public void setDeclarationOfPerformanceText(String declarationOfPerformanceText) {
		this.declarationOfPerformanceText = declarationOfPerformanceText;
	}
	
	
	

}