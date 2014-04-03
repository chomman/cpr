package sk.peterjurkovic.cpr.services;

import java.util.List;

import sk.peterjurkovic.cpr.entities.Webpage;

public interface WebpageService {

	void createWebpage(Webpage webpage);
	
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
	
	/**
	 * Vrati nasledujuce poradie verejnej sekcie, v danom uzle. V pripade, ak je daný uzol NULL, 
	 * jedná za o najvyssieho rodica. 
	 * 
	 * @param nodeId
	 * @return nasledujuce poradie
	 */
	int getNextOrderValue(Long nodeId);
}
