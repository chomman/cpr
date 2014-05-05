package cz.nlfnorm.dto;

import org.joda.time.LocalDateTime;
import org.jsoup.helper.Validate;

import com.drew.lang.annotations.NotNull;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import cz.nlfnorm.entities.Webpage;
import cz.nlfnorm.enums.WebpageModule;
import cz.nlfnorm.enums.WebpageType;
import cz.nlfnorm.web.json.deserializers.LocalDateTimeDeserializer;

public class WebpageSettingsDto extends AbstractWebpageDto{
	
	@NotNull
	private WebpageType webpageType;
	private Boolean lockedCode;
	private Boolean lockedRemove;
	@JsonDeserialize( using = LocalDateTimeDeserializer.class)
	private LocalDateTime publishedSince;
	@NotNull
	private Boolean enabled;
	@NotNull
	private Boolean showThumbnail;
	private WebpageModule webpageModule;
	@NotNull
	private Boolean isOnlyForRegistrated;
	
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
	
	public Boolean getLockedCode() {
		return lockedCode;
	}

	public void setLockedCode(Boolean lockedCode) {
		this.lockedCode = lockedCode;
	}

	public Boolean getLockedRemove() {
		return lockedRemove;
	}

	public void setLockedRemove(Boolean lockedRemove) {
		this.lockedRemove = lockedRemove;
	}
	
	public Boolean getShowThumbnail() {
		return showThumbnail;
	}

	public void setShowThumbnail(Boolean showThumbnail) {
		this.showThumbnail = showThumbnail;
	}
	public WebpageModule getWebpageModule() {
		return webpageModule;
	}
	public void setWebpageModule(WebpageModule webpageModule) {
		this.webpageModule = webpageModule;
	}
	public Boolean getIsOnlyForRegistrated() {
		return isOnlyForRegistrated;
	}
	public void setIsOnlyForRegistrated(Boolean isOnlyForRegistrated) {
		this.isOnlyForRegistrated = isOnlyForRegistrated;
	}

	@Override
	public void setWebpage(Webpage webpage){
		setId( webpage.getId() );
		this.webpageType = webpage.getWebpageType();
		this.lockedCode = webpage.getLockedCode();
		this.lockedRemove = webpage.getLockedRemove();
		this.publishedSince = webpage.getPublishedSince();
		this.enabled = webpage.getEnabled();
		this.showThumbnail = webpage.getShowThumbnail();
		this.webpageModule = webpage.getWebpageModule();
		this.isOnlyForRegistrated = webpage.getIsOnlyForRegistrated();
	}
	
}
