package sk.peterjurkovic.cpr.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name ="basic_requirement")
@Inheritance(strategy = InheritanceType.JOINED)
public class BasicRequirement extends AbstractEntity {

	private static final long serialVersionUID = 713L;
	
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
	
	@NotEmpty(message = "Název požadavku musí být vyplněn")
	@Length( max = 150, message= "Název požadavku může mít max. 150 znaků")
	@Column(name="name", length = 150)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Type( type = "text")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	

	
}
