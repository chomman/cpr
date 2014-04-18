package sk.peterjurkovic.cpr.dto;

import org.apache.commons.lang.Validate;
import org.hibernate.validator.constraints.Length;

import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.entities.WebpageContent;
import sk.peterjurkovic.cpr.enums.SystemLocale;
import sk.peterjurkovic.cpr.web.json.deserializers.WebpageDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class WebpageContentDto extends AbstractWebpageDto{
	
	@Length(min = 2, max = 2)
	private String locale;
	private WebpageContent webpageContent;
	private String redirectUrl;
	private String code;
	@JsonDeserialize(using = WebpageDeserializer.class)
	private Webpage redirectWebpage;
	
	public WebpageContentDto(){}
	
	public WebpageContentDto(Webpage webpage, String locale){
		Validate.notNull(webpage);
		if(!SystemLocale.isAvaiable(locale)){
			throw new IllegalArgumentException("Locale: " + locale + " is not avaiable" );
		}
		setWebpage(webpage);
		this.locale = locale;
		if(webpage.getLocalized().containsKey(locale)){
			this.webpageContent = webpage.getLocalized().get(locale);
		}else{
			this.webpageContent = new WebpageContent();
		}
	}
	
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public WebpageContent getWebpageContent() {
		return webpageContent;
	}
	public void setWebpageContent(WebpageContent webpageContent) {
		this.webpageContent = webpageContent;
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
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public void setWebpage(Webpage webpage) {
		setId( webpage.getId() );
		setRedirectUrl(webpage.getRedirectUrl());
		setRedirectWebpage(webpage.getRedirectWebpage());
		setCode(webpage.getCode());
	}
	
	
}
