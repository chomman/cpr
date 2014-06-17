package cz.nlfnorm.quasar.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * QUASAR Entity
 * 
 * Represent working experience
 * @author Peter Jurkovic
 * @date Jun 16, 2014
 */
@Entity
@Table(name = "quasar_experience")
public class Experience extends IdentifiableEntity{

	private static final long serialVersionUID = -7567740861282727772L;
	private String name;
	private boolean medicalDeviceExperience;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return super.getId();
	}
	
	@Column(nullable = false, length = 30)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(nullable = false, name = "is_md_exp")
	public boolean isMedicalDeviceExperience() {
		return medicalDeviceExperience;
	}
	
	public void setMedicalDeviceExperience(boolean medicalDeviceExperience) {
		this.medicalDeviceExperience = medicalDeviceExperience;
	}
	
}
