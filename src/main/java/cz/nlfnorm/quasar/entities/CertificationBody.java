package cz.nlfnorm.quasar.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name = "quasar_company_id_seq", sequenceName = "quasar_company_id_seq", initialValue = 1, allocationSize =1)
@Table(name = "quasar_certification_body")
public class CertificationBody extends IdentifiableEntity {
	
	private static final long serialVersionUID = 7172242080658460324L;
	private String name;
	
	@Id
	@Override
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quasar_company_id_seq")
	public Long getId() {
		return super.getId();
	}
	
	@Column(name = "name", nullable = false, length = 60, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
