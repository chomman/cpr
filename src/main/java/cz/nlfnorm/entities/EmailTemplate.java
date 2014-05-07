package cz.nlfnorm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;


@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name = "email_template_id_seq", sequenceName = "email_template_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "email_template")
public class EmailTemplate extends AbstractEntity {
	
	private String name;
	private String subject;
	private String body;
	private String variables;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_template_id_seq")
	public Long getId() {
		return super.getId();
	}
	
	@Column(name = "name", length = 100)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "subject", length = 150)
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
		
	@Type(type = "text")
	@Column(name = "body")
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	@Type(type = "text")
	@Column(name = "variables")
	public String getVariables() {
		return variables;
	}
	public void setVariables(String variables) {
		this.variables = variables;
	}
	
	
	
}
