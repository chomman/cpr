package sk.peterjurkovic.cpr.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;


@Entity
@Table(name="article")
@Inheritance(strategy = InheritanceType.JOINED)
public class Article extends AbstractEntity {


	private static final long serialVersionUID = 8803;
	
	private Long id;
	
	private String title;
	
	private String header;
	
	private String content;
	
	private DateTime released = new DateTime();

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(length = 150)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
	
	@Type(type = "text")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Type(type = "jodaDateTime")
	public DateTime getReleased() {
		return released;
	}

	public void setReleased(DateTime released) {
		this.released = released;
	}
	
	
	

}
