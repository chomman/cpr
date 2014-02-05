package sk.peterjurkovic.cpr.utils;

import sk.peterjurkovic.cpr.dto.WebpageDto;
import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.resolvers.LocaleResolver;

public class WebpageUtils {
	
	public static WebpageDto toDTO(final Webpage webpage,final String langCode){
		if(!LocaleResolver.isAvailable(langCode)){
			throw new IllegalArgumentException(String.format("Lang code %s is not available.", langCode));
		}
		if(langCode.equalsIgnoreCase(LocaleResolver.CODE_EN)){
			return toEnglishDto(webpage);
		}
		return toCzechDto(webpage);
	}
	
	
	public static WebpageDto toCzechDto(final Webpage webpage){
		WebpageDto dto = new WebpageDto();
		dto.setTitle(webpage.getTitleCzech());
		dto.setName(webpage.getNameCzech());
		dto.setDescription(webpage.getDescriptionCzech());
		dto.setTopText(webpage.getTopTextCzech());
		dto.setBottomText(webpage.getBottomTextCzech());
		dto.setWebpageCategory(webpage.getWebpageCategory());
		dto.setWebpageContent(webpage.getWebpageContent());
		dto.setCode(webpage.getCode());
		dto.setEnabled(webpage.getEnabled());
		dto.setLocale(LocaleResolver.CODE_CZ);
		dto.setId(webpage.getId());
		return dto;
	}
	
	public static WebpageDto toEnglishDto(final Webpage webpage){
		WebpageDto dto = new WebpageDto();
		dto.setTitle(webpage.getTitleEnglish());
		dto.setName(webpage.getNameEnglish());
		dto.setDescription(webpage.getDescriptionEnglish());
		dto.setTopText(webpage.getTopTextEnglish());
		dto.setBottomText(webpage.getBottomTextEnglish());
		dto.setWebpageCategory(webpage.getWebpageCategory());
		dto.setWebpageContent(webpage.getWebpageContent());
		dto.setCode(webpage.getCode());
		dto.setEnabled(webpage.getEnabled());
		dto.setLocale(LocaleResolver.CODE_EN);
		dto.setId(webpage.getId());
		return dto;
	}
	
}
