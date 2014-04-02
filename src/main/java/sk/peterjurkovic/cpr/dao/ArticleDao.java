package sk.peterjurkovic.cpr.dao;

import java.util.List;
import java.util.Map;

import sk.peterjurkovic.cpr.entities.Article;

/**
 * Dao rozhranie pre aktuality
 * 
 * @author Peter Jurkovic (email@peterjurkovic.com)
 *
 */
public interface ArticleDao extends BaseDao<Article, Long>{
	
	
	/**
	 * Vrati dalsie mozne ID
	 * 
	 * @return Long dalie id, tkore bude vygenerovane
	 */
	Long getNextIdValue();
	
	
	
	/**
	 * Vrati nastrankovanu stranku aktualitz, na zaklade danych kriterii
	 * 
	 * @param int cislo stranky
	 * @param Map<String, Object> criteria
	 * @return List<Article> stranka aktualit
	 */
	List<Article> getArticlePage(int pageNumber, Map<String, Object> criteria);
	
	
	
	/**
	 * Vrati pocet aktualit vyhovujuci danym kriteriam
	 * 
	 * @param Map<String, Object> criteria
	 * @return Long pocet 
	 */
	Long getCountOfArticles(Map<String, Object> criteria);
	
	
	
	/**
	 * Vrati titulkty aktualit pre naseptavac, resp autocomplete
	 * 
	 * @param String term
	 * @return List<Article>
	 */
	List<Article> autocomplateSearch(final String query);
	
	
	
	/**
	 * Vrati zoznam najnovsich aktualit
	 * 
	 * @param count pocet, resp limit ktory sa ma vratit
	 * @return List<Article> 
	 */
	List<Article> getNewestArticles(int count);
	
	
	
	/**
	 * Vrati aktuality pre verejnu sekciu, kotre su aktitovane publikovane.
	 * @param int stranka 
	 * @return List<Article> nastrankovanz zoznam aktualit 
	 */
	List<Article> getArticlePageForPublic(int pageNumber);
	
	
	
	/**
	 * Vrati pocet aktualit pre verejnu sekciu, vyhovujuci kriteriam, tz. su publikovane, datum a cas zaciatku, 
	 * resp konca publikacie vyhovuje aktualnemu datumu
	 * 
	 * @return Long pocet
	 */
	Long getCountOfArticlesForPublic();
}
