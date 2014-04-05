package sk.peterjurkovic.cpr.services;

import java.util.List;

import sk.peterjurkovic.cpr.entities.Webpage;

public interface WebpageService {

	void saveWebpage(Webpage webpage);
	
	void updateWebpage(Webpage webpage);
	
	void deleteWebpage(Webpage webpage);
	
	Webpage getWebpageById(Long id);
	
	Webpage getWebpageByCode(String code);
	
	List<Webpage> getAll();
	
	void saveOrUpdate(Webpage webpage);
	
	List<Webpage> getPublicSection(Long sectionId);
	
	String getSeoUniqueUrl(String name);
	
	boolean isWebpageUrlUniqe(String code, Long id);
	
	List<Webpage> getAllOrderedWebpages();
	
	List<Webpage> getTopLevelWepages();
	
	int getNextOrderValue(Long nodeId);
	
	Long createNewWebpage(Webpage form, Long webpageNodeId);
	
	void createWebpageContent(Long webpageId, String langCode);
}
