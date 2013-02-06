package sk.peterjurkovic.cpr.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="webpage")
@Inheritance(strategy = InheritanceType.JOINED)
public class Webpage extends AbstractEntity {

	
	private static final long serialVersionUID = 3981658331L;
	
	private Long id;
	
	private String name;
	
	private String title;
	
	private String keywords;
	
	private String description;
	
	private WebpageCategory webpageCategory;
	
	private String topText;
	
	private WebpageContent webpageContent;
	
	private String bottomText;
	
	private Long timestamp;
	

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "name", length = 100)
	@Length(min = 1, max = 100, message = "Název musí být vyplněn")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "title", length = 150)
	@Length(min = 1, max = 150, message = "Titulek musí být vyplněn")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "keywords", length = 150)
	@Length(max = 150, message = "Překročili jste délku klíčových slov")
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	@Column(name = "description", length = 255)
	@Length(max = 255, message = "Překročili jste délku popisku")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "webpage_category_id")
	public WebpageCategory getWebpageCategory() {
		return webpageCategory;
	}

	public void setWebpageCategory(WebpageCategory webpageCategory) {
		this.webpageCategory = webpageCategory;
	}
	
	@Type(type = "text")
	@Column(name = "top_text")
	public String getTopText() {
		return topText;
	}

	
	public void setTopText(String topText) {
		this.topText = topText;
	}
	
	@Type(type = "text")
	@Column(name = "bottom_text")
	public String getBottomText() {
		return bottomText;
	}

	public void setBottomText(String bottomText) {
		this.bottomText = bottomText;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "webpage_content_id")
	public WebpageContent getWebpageContent() {
		return webpageContent;
	}
	

	public void setWebpageContent(WebpageContent webpageContent) {
		this.webpageContent = webpageContent;
	}
	
	@Transient
	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
}
