package sk.peterjurkovic.cpr.services;

import java.util.List;
import java.util.Map;

import sk.peterjurkovic.cpr.entities.Article;

/**
 * Rozhranie aktuality
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
public interface ArticleService {
	
	/**
	 * Vytvori novu aktualitu 
	 * @param article
	 */
	void createArticle(Article article);
	
	/**
	 * Aktualizuje danu aktualitu, nastavi casovu znamku zmenu na aktualny cas
	 * @param article
	 */
	void updateArticle(Article article);
	
	
	/**
	 * Nenavratne odstrani aktualitu zo systemu
	 * @param article
	 */
	void deleteArticle(Article article);
	
	/**
	 * Vrati aktualitu na zaklade daneho ID
	 * @param id identifikator aktuality
	 * @return najdena aktualita, alebo NULL
	 */
	Article getArticleById(Long id);
	
	/**
	 * Vrati aktualitu na zaklade daneho kodu, resp. SEO URL
	 * 
	 * @param code url aktuality
	 * @return najdena aktualita, alebo NULL
	 */
	Article getArticleByCode(String code);
	
	/**
	 * Vrati vsetky evidovane aktuality v systeme, nezavisle od ich stavu
	 * @return zoznam aktualti
	 */
	List<Article> getAll();
	
	
	/**
	 * Vytvori alebo aktualizuje aktualitu, v pripade ak je ID danej aktuaity NULL, alebo 0, 
	 * vytvori sa nova polozvka v databze. Inak sa zmeni casova znamka poslednej zmenu a persistuje
	 * 
	 * @param article
	 */
	void saveOrUpdate(Article article);
	
	
	/**
	 * Vrato stranku aktualit, na zaklade danych kriterii.
	 * 
	 * @param pageNumber cislo stranky
	 * @param criteria kriteria na zaklade ktorych sa aplikuje filter
	 * @return zoznam aktualit
	 */
	List<Article> getArticlePage(int pageNumber, Map<String,Object> criteria);
	
	
	/**
	 * Vrati pocet aktualit vyhovujucich danym kriteriam
	 * 
	 * @param criteria kriteria na zaklade ktorych sa aplikuje filter
	 * @return zoznam aktualit
	 */
	Long getCountOfArticles(Map<String,Object> criteria);
	
	
	/**
	 * Vrati zoznam aktualit, ktorych nazov vyhovuje danemu termu. 
	 * 
	 * @param query hladany vyraz
	 * @return zoznam aktualit
	 */
	List<Article> autocomplateSearch(final String query);
	
	
	/**
	 * Vrati pocet najnovsich, publikovanych aktualit.
	 * 
	 * @param count pozadovany pocet, resp. limit
	 * @return zoznam aktualit
	 */
	List<Article> getNewestArticles(int count);
	
	/**
	 * Vrato publikovanu stranku aktualit, na zaklade danych kriterii.
	 * 
	 * @param pageNumber cislo stranky
	 * @return zoznam aktualit
	 */
	List<Article> getArticlePageForPublic(int pageNumber);
	
	
	/**
	 * Vrati pocet publikovanych aktualit vyhovujucich danym kriteriam
	 * 
	 * @return zoznam aktualit
	 */
	Long getCountOfArticlesForPublic();
}
