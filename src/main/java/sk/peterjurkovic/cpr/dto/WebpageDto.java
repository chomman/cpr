package sk.peterjurkovic.cpr.dto;

import sk.peterjurkovic.cpr.entities.WebpageCategory;
import sk.peterjurkovic.cpr.entities.WebpageContent;

public class WebpageDto {
	
	private Long id;
	private String name;
	private String title;
	private String description;
	private String topText;
	private String bottomText;
	private WebpageCategory webpageCategory;
	private WebpageContent webpageContent;
	private String locale;
	private String code;
	private boolean enabled;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTopText() {
		return topText;
	}
	public void setTopText(String topText) {
		this.topText = topText;
	}
	public String getBottomText() {
		return bottomText;
	}
	public void setBottomText(String bottomText) {
		this.bottomText = bottomText;
	}
	public WebpageCategory getWebpageCategory() {
		return webpageCategory;
	}
	public void setWebpageCategory(WebpageCategory webpageCategory) {
		this.webpageCategory = webpageCategory;
	}
	public WebpageContent getWebpageContent() {
		return webpageContent;
	}
	public void setWebpageContent(WebpageContent webpageContent) {
		this.webpageContent = webpageContent;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
	
	
	
}
