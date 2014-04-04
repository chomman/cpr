package sk.peterjurkovic.cpr.dto;

import org.joda.time.LocalDateTime;
import org.jsoup.helper.Validate;

import com.drew.lang.annotations.NotNull;

import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.enums.WebpageType;

public class WebpageSettingsDto extends AbstractWebpageDto{
	
	@NotNull
	private WebpageType webpageType;
	private Boolean locked;
	private LocalDateTime publishedSince;
	@NotNull
	private Boolean enabled;
	
	public WebpageSettingsDto(){}
	
	public WebpageSettingsDto(final Webpage webpage){
		Validate.notNull(webpage);
		setWebpage(webpage);
	}
	
	public WebpageType getWebpageType() {
		return webpageType;
	}
	public void setWebpageType(WebpageType webpageType) {
		this.webpageType = webpageType;
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
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	@Override
	public void setWebpage(Webpage webpage){
		setId( webpage.getId() );
		this.webpageType = webpage.getWebpageType();
		this.locked = webpage.getLocked();
		this.publishedSince = webpage.getPublishedSince();
		this.enabled = webpage.getEnabled();
	}
	
}
