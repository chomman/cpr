package sk.peterjurkovic.cpr.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
/**
 * Entita reprezentujuca kategoriu verejnej sekcie
 * 
 */
@Entity
@SequenceGenerator(name = "webpage_category_id_seq", sequenceName = "webpage_category_id_seq", initialValue = 1, allocationSize =1)
@Table(name = "webpage_category")
public class WebpageCategory extends AbstractEntity {
	
	private static final long serialVersionUID = 9831651311L;

	private Long id;
	
	private String name;

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "webpage_category_id_seq")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(length = 50)
	@Length(min = 1, max = 50, message = "Název musí být vyplněn")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
