package sk.peterjurkovic.cpr.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.joda.time.LocalDateTime;

import sk.peterjurkovic.cpr.web.json.DateTimeDeserializer;

/**
 * Entita reprezentujuca aktualitu 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Entity
@Table(name="article")
@Inheritance(strategy = InheritanceType.JOINED)
public class Article extends AbstractEntity {


	private static final long serialVersionUID = 8803;
	
	private Long id;
	
	private String title;
	
	private String header;
	
	private String articleContent;
	
	private LocalDateTime publishedSince;
	
	private LocalDateTime publishedUntil;
	
	private Long timestamp;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	

	public void setId(Long id) {
		this.id = id;
	}
	
	@Length(min = 1, max = 150, message = "Titulek aktuality musí být vyplněn")
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
	@Column(name = "article_content")
	public String getArticleContent() {
		return articleContent;
	}

	public void setArticleContent(String content) {
		this.articleContent = content;
	}

	
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	@Column(name = "published_since")
	@JsonDeserialize(using=DateTimeDeserializer.class)
	public LocalDateTime getPublishedSince() {
		return publishedSince;
	}


	public void setPublishedSince(LocalDateTime publishedSince) {
		this.publishedSince = publishedSince;
	}
	
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	@Column(name = "published_until")
	@JsonDeserialize(using=DateTimeDeserializer.class)
	public LocalDateTime getPublishedUntil() {
		return publishedUntil;
	}


	public void setPublishedUntil(LocalDateTime publishedUntil) {
		this.publishedUntil = publishedUntil;
	}

	@Transient
	public Long getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
}
