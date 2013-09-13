package sk.peterjurkovic.cpr.web.forms.admin;

import org.joda.time.LocalDateTime;


public class ArticleForm {
	
	private Long id;
	
	private String title;
	
	private String header;
	
	private String articleContent;
	
	private LocalDateTime publishedSince;
	
	private LocalDateTime publishedUntil;
	
	private Boolean enabled;
	
	private Long timestamp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getArticleContent() {
		return articleContent;
	}

	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}

	public LocalDateTime getPublishedSince() {
		return publishedSince;
	}

	public void setPublishedSince(LocalDateTime publishedSince) {
		this.publishedSince = publishedSince;
	}

	public LocalDateTime getPublishedUntil() {
		return publishedUntil;
	}

	public void setPublishedUntil(LocalDateTime publishedUntil) {
		this.publishedUntil = publishedUntil;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
		
}
