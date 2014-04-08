package sk.peterjurkovic.cpr.dao;

import java.util.List;

import sk.peterjurkovic.cpr.dto.AutocompleteDto;
import sk.peterjurkovic.cpr.entities.Webpage;

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
	
	List<AutocompleteDto> autocomplete(String term);
	
	Webpage getHomePage();
}
