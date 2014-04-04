package sk.peterjurkovic.cpr.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;


@Embeddable
public class WebpageContent {
	
    private String name;
    private String url;
    private String title;
    private String description;
    private String content;
    
    
    @Column(nullable = false, length = 200)
	public String getName() {
		return name;
	}
	
	@Column(length = 250)
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
	
	@Column(name = "url")
	public String getUrl() {
		return url;
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
