package sk.peterjurkovic.cpr.services;

import java.util.List;

import sk.peterjurkovic.cpr.dto.AutocompleteDto;
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
	
	String getUniqeCode(String name);
	
	String getUniqeCode(String name, Long excludeId);
	
	boolean isWebpageUrlUniqe(String code, Long id);
	
	List<Webpage> getAllOrderedWebpages();
	
	List<Webpage> getTopLevelWepages();
	
	List<Webpage> getTopLevelWepages(boolean enabledOnly);
	
	int getNextOrderValue(Long nodeId);
	
	Long createNewWebpage(Webpage form, Long webpageNodeId);
	
	Long createNewWebpage(Webpage form);
	
	void createWebpageContent(Long webpageId, String langCode);
	
	List<AutocompleteDto> autocomplete(String term);
	
	void deleteWebpageAvatar(Long id);
	
	void deleteWebpageWithAttachments(Long id);
	
	Webpage getHomePage();
}
