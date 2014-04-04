package sk.peterjurkovic.cpr.dto;

import org.apache.commons.lang.Validate;
import org.hibernate.validator.constraints.Length;

import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.entities.WebpageContent;
import sk.peterjurkovic.cpr.enums.SystemLocale;

public class WebpageContentDto extends AbstractWebpageDto{
	
	@Length(min = 2, max = 2)
	private String locale;
	private WebpageContent webpageContent;
	
	public WebpageContentDto(){}
	
	public WebpageContentDto(Webpage webpage, String locale){
		Validate.notNull(webpage);
		if(!SystemLocale.isAvaiable(locale)){
			throw new IllegalArgumentException("Locale: " + locale + " is not avaiable" );
		}
		setWebpage(webpage);
		this.locale = locale;
		this.webpageContent = webpage.getLocalized().get(locale);
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
	
	@Override
	public void setWebpage(Webpage webpage) {
		setId( webpage.getId() );
	}
	
	
}
