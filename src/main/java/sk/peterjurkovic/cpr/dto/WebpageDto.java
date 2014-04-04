package sk.peterjurkovic.cpr.dto;

import org.apache.commons.lang.Validate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.joda.time.LocalDateTime;

import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.entities.WebpageContent;
import sk.peterjurkovic.cpr.enums.SystemLocale;
import sk.peterjurkovic.cpr.enums.WebpageType;

import com.drew.lang.annotations.NotNull;

public class WebpageDto{
	
	@NotNull
	private Long id;
	@NotNull
	private WebpageType webpageType;
	@URL
	@Length(max = 255)
	private String redirectUrl;
	private Webpage redirectWebpage;
	private Boolean locked;
	private LocalDateTime publishedSince;
	@Length(max = 2, min = 2)
	private String locale;
	private Boolean enabled;
	
	@Length(max = 200, min = 1)
	private String name;
	@Length(max = 250)
	private String url;
    @Length(max = 250)
    private String title;
    private String description;
    private String content;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public WebpageType getWebpageType() {
		return webpageType;
	}
	
	public void setWebpageType(WebpageType webpageType) {
		this.webpageType = webpageType;
	}
	
	public String getRedirectUrl() {
		return redirectUrl;
	}
	
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	
	public Webpage getRedirectWebpage() {
		return redirectWebpage;
	}
	
	public void setRedirectWebpage(Webpage redirectWebpage) {
		this.redirectWebpage = redirectWebpage;
	}
	
	public Boolean getLocked() {
		return locked;
	}
	
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}
	
	public LocalDateTime getPublishedSince() {
		return publishedSince;
	}
	public void setPublishedSince(LocalDateTime publishedSince) {
		this.publishedSince = publishedSince;
	}
	
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public WebpageContent toWebpageContent(){
		WebpageContent webpageContent = new WebpageContent();
		webpageContent.setContent(content);
		webpageContent.setDescription(description);
		webpageContent.setName(name);
		webpageContent.setUrl(url);
		webpageContent.setTitle(title);
		return webpageContent;
	}
	
	private void setWebpageContent(WebpageContent webpageContent){
		if(webpageContent != null){
			content = webpageContent.getContent();
			description = webpageContent.getDescription();
			name = webpageContent.getName();
			url = webpageContent.getUrl();
			title = webpageContent.getTitle();
		}
	}
	
	public void setWebpage(Webpage webpage, String inLang){
		Validate.notNull(webpage);
		if(SystemLocale.isAvaiable(inLang)){
			throw new IllegalArgumentException(String.format("Local: [%s] is not avaiable. ", inLang));
		}
		id = webpage.getId();
		enabled = webpage.getEnabled();
		webpageType = webpage.getWebpageType();
		redirectUrl = webpage.getRedirectUrl();
		locked = webpage.getLocked();
		publishedSince = webpage.getPublishedSince();
		locale = inLang;
		enabled = webpage.getEnabled();
		setWebpageContent(webpage.getLocalized().get(inLang));
		
	}
	
}
