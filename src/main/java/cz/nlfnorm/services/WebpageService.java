package cz.nlfnorm.services;

import java.nio.file.AccessDeniedException;
import java.util.List;

import cz.nlfnorm.dto.AutocompleteDto;
import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.Webpage;
import cz.nlfnorm.enums.WebpageModule;

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
	
	void deleteWebpageWithAttachments(Long id) throws AccessDeniedException ;
	
	Webpage getHomePage();
	
	Webpage getWebpageByModule(WebpageModule webpageModule);
	
	Webpage getTopParentWebpage(Webpage childrenNode);
	
	List<Webpage> getChildrensOfNode(Long id, boolean publishedOnly);
	
	void moveWebpage(Webpage webpage, Long parentId, int order);
	
	List<Webpage> getLatestPublishedNews(int limit);
	
	void incrementHit(Webpage webpage);
	
	PageDto getSearchPage(int pageNumber, String term, Long nodeId);
}
