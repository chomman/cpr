package cz.nlfnorm.quasar.entities;

import java.io.Serializable;

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
public class Experience implements Serializable{

	private static final long serialVersionUID = -7567740861282727772L;
	private Long id;
	private String name;
	private boolean medicalDeviceExperience;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Experience other = (Experience) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
}
