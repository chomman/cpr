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

/**
 * Entity of Quality system assesmet reporting system, represents EAC code
 * 
 * @author Peter Jurkovic
 * @date Jun 6, 2014
 */
@Entity
@SequenceGenerator(name = "quasar_eac_code_id_seq", sequenceName = "quasar_eac_code_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "quasar_eac_code")
public class EacCode extends BaseEntity {

	private static final long serialVersionUID = 1385780507346969333L;
	
	private String name;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quasar_eac_code_id_seq")
	@Override
	public Long getId() {
		return super.getId();
	}
	
	@Length(message = "{error.eacCode.name}", max = 120)
	@Column(name = "name", length = 120)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	
	
}
