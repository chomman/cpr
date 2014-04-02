package sk.peterjurkovic.cpr.entities;

import java.util.Locale;

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

import org.apache.commons.lang.Validate;
import org.hibernate.annotations.Type;


@Entity
@SequenceGenerator(name = "webpage_content_id_seq", sequenceName = "webpage_content_id_seq", initialValue = 1, allocationSize =1)
@Table(name = "webpage_content")
public class WebpageContent {
	
    private Long id;
    private Locale locale;
    private Webpage webpage;
    
    private String name;
    private String url;
    private String title;
    private String description;
    private String content;
    
    
    
    public WebpageContent(){ }
 
    public WebpageContent(Locale locale){
    	Validate.notNull(locale);
    	this.locale = locale;
    }
    
    
    
    
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "webpage_content_id_seq")
	public Long getId() {
		return id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "webpage_id")
	public Webpage getWebpage() {
		return webpage;
	}
	
	@Column(nullable = false, length = 2)
	public Locale getLocale() {
		return locale;
	}
	
	@Column(nullable = false, length = 200)
	public String getName() {
		return name;
	}
	
	@Column(length = 200)
	public String getTitle() {
		return title;
	}
	
	@Type(type = "text")
	public String getDescription() {
		return description;
	}
	
	@Type(type = "text")
	public String getContent() {
		return content;
	}
	
	@Column(name = "")
	public String getUrl() {
		return url;
	}

	
	

	public void setWebpage(Webpage webpage) {
		this.webpage = webpage;
	}
    
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
}
