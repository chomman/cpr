package sk.peterjurkovic.cpr.services;

import java.util.List;

import sk.peterjurkovic.cpr.entities.WebpageCategory;

public interface WebpageCategoryService {
	
	void createWebpageCategory(WebpageCategory webpageCategory);
	
	void updateWebpageCategory(WebpageCategory webpageCategory);
	
	void deleteWebpageCategory(WebpageCategory webpageCategory);
	
	WebpageCategory getWebpageCategoryById(Long id);
	
	WebpageCategory getWebpageCategoryByCode(String code);
	
	List<WebpageCategory> getAll();
	
	void saveOrUpdate(WebpageCategory webpageCategory);
	
}
