package sk.peterjurkovic.cpr.services;

import java.util.List;

import sk.peterjurkovic.cpr.entities.WebpageContent;

public interface WebpageContentService {
	
	void createWebpageContent(WebpageContent webpageContent);
	
	void updateWebpageContent(WebpageContent webpageContent);
	
	void deleteWebpageContent(WebpageContent webpageContent);
	
	WebpageContent getWebpageContentById(Long id);
	
	WebpageContent getWebpageContentByCode(String code);
	
	List<WebpageContent> getAll();
	
	void saveOrUpdate(WebpageContent webpageContent);

}
