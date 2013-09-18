package sk.peterjurkovic.cpr.entities;

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

/**
 * Stinok, resp. potencionalny nazov vyhladavaneho vyrobku, evidovany pri danej norme
 * 
 */
@Entity
@SequenceGenerator(name = "tag_id_seq", sequenceName = "tag_id_seq", initialValue = 1, allocationSize =1)
@Table(name = "tag")
public class Tag {

	private Long id;
	private String name;
	private Standard standard;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_id_seq")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	@Column(length = 100)
	@Length(min = 1, max = 100)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "standard_id")
	public Standard getStandard() {
		return standard;
	}
	
	
	public void setStandard(Standard standard) {
		this.standard = standard;
	}
	
	
}
