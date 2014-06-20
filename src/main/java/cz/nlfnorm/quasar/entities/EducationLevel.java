package cz.nlfnorm.quasar.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;


/**
 * Entity of Quality system assesmet reporting system, represents education level
 * 
 * 
 * A master’s degree can substitute 1 year of the working experience,
 * Post-Graduate study PGS can substitute 2 years, and PhD and higher (Doc.,
 * Prof. etc.) in a relevant subject e.g. including device design,
 * clinical/performance requirements may be used to substitute 3 years working
 * experience, respectively. The total substitution together cannot exceed 3
 * years.
 * 
 * @author Peter Jurkovic
 * @date Jun 6, 2014
 */

@Entity
@Table(name = "quasar_education_level")
public class EducationLevel extends IdentifiableEntity{
	
	public static final int HIGH_SCHOOL = 2;
	public static final int BACHALOR = 3;
	public static final int MASTER = 4;
	public static final int PGS = 5;
	public static final int PHD = 6;
	public static final int DOC_PROF = 7;
	
	private static final long serialVersionUID = -6396483476015802928L;
	private String name;
	private int yeasSubstitution;
	
	@Id
	@Override
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return super.getId();
	}
	
	
	@Column(length = 30)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Min(value = 0)
	@Column(nullable = false, name = "yeas_substitution")
	public int getYeasSubstitution() {
		return yeasSubstitution;
	}
	public void setYeasSubstitution(int yeasSubstitution) {
		this.yeasSubstitution = yeasSubstitution;
	}
}
