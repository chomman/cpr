package sk.peterjurkovic.cpr.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

/**
 * Entita reprezentujuca hlavny obsah verejnej sekcie
 * 
 */
@Entity
@Table(name = "webpage_content")
@Inheritance(strategy = InheritanceType.JOINED)
public class WebpageContent extends AbstractEntity{
	
	
	private static final long serialVersionUID = 669931L;

	private Long id;
	
	private String name;
	
	private String description;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	@Column(length = 50)
	@Length(min = 1, max = 50, message = "Název musí být vyplněn")
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	
	
	
}
