package cz.nlfnorm.quasar.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import cz.nlfnorm.entities.User;

@Entity
@Table(name="quasar_partner")
@SequenceGenerator(name = "quasar_partner_id_seq", sequenceName = "quasar_partner_id_seq", initialValue = 1, allocationSize =1)
public class Partner extends IdentifiableEntity {
	
	private static final long serialVersionUID = 6895179406391276009L;
	
	private String name;
	
	private User manager;
	
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_manager")
	public User getManager() {
		return manager;
	}

	public void setManager(User manger) {
		this.manager = manger;
	}
	
}
