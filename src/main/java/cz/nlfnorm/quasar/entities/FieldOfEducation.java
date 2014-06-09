package cz.nlfnorm.quasar.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name = "quasar_field_of_education_id_seq", sequenceName = "quasar_field_of_education_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "quasar_field_of_education")
public class FieldOfEducation extends BaseEntity {
	
	private static final long serialVersionUID = 3239374337036888408L;
	
	private String name;
	private boolean forActiveMedicalDevices;
	private boolean forNonActiveMedicalDevices;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quasar_field_of_education_id_seq")
	@Override
	public Long getId() {
		return super.getId();
	}
	
	@Column(name = "name", length = 50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "is_for_active_md" )
	public boolean isForActiveMedicalDevices() {
		return forActiveMedicalDevices;
	}
	public void setForActiveMedicalDevices(boolean forActiveMedicalDevices) {
		this.forActiveMedicalDevices = forActiveMedicalDevices;
	}
	
	@Column(name = "is_for_non_active_md" )
	public boolean isForNonActiveMedicalDevices() {
		return forNonActiveMedicalDevices;
	}
	public void setForNonActiveMedicalDevices(boolean forNonActiveMedicalDevices) {
		this.forNonActiveMedicalDevices = forNonActiveMedicalDevices;
	}

	
	
	
}
