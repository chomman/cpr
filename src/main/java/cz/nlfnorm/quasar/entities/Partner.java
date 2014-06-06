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

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="partner")
@SequenceGenerator(name = "quasar_partner_id_seq", sequenceName = "quasar_partner_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
public class Partner extends BaseEntity {
	
	private static final long serialVersionUID = 6895179406391276009L;
	
	private String name;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quasar_partner_id_seq")
	@Override
	public Long getId() {
		return super.getId();
	}

	@Length(message = "{error.partner.name}", max = 50)
	@Column(name = "name", length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
