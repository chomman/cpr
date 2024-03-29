package cz.nlfnorm.dao;

import java.util.List;

import cz.nlfnorm.dto.AutocompleteDto;
import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.Webpage;
import cz.nlfnorm.enums.WebpageModule;
import cz.nlfnorm.enums.WebpageType;

/**
 * DAO rozhranie k manipulacii s verejnymi sekciami systemu
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
public interface WebpageDao extends BaseDao<Webpage, Long>{
	
	
	/**
	 * Na zaklade jedinecneho identifikatoru kategorie sa vratia vsetky verejne sekcie.
	 * 
	 * @param Long ID kategorie
	 * @return List<Webpage> zoznam verejnych sekcii v danej kategorii
	 */
	List<Webpage> getPublicSection(Long sectionId);
	
	
	/**
	 * Vrati nasledujuce iD, ktore bude priradene pri dalsom inserte.
	 * 
	 * @return Long 
	 */
	Long getNextIdValue();
	
	
	List<Webpage> getAllOrderedWebpages();
	
	
	List<Webpage> getTopLevelWepages(boolean enabledOnly);
	
	int getMaxOrderInNode(Long nodeId);
	
	List<AutocompleteDto> autocomplete(String term, boolean enabledOnly, Long excludeId);
	
	Webpage getHomePage();
	
	Webpage getWebpageByModule(WebpageModule webpageModule);
	
	
	Webpage getTopParentWebpage(Webpage childrenNode);
	
	List<Webpage> getChildrensOfNode(Long id, boolean publishedOnly);
	
	void incrementOrder(Webpage parentWebpage, int threshold);

	void decrementOrder(Webpage parentWebpage, int threshold);
	
	List<Webpage> getLatestPublishedNews(int limit);
	
	PageDto search(int pageNumber, String term, Long parentNodeId);
	
	void updatetsVector(Webpage webpage);
	
	List<Webpage> getFooterWebpages();
	
	List<Webpage> getOldNonArchivedNewsInNode(Webpage node);
	
	List<Webpage> getSimilarWebpages(Webpage webpage, int limit, WebpageType webpageType);
}
