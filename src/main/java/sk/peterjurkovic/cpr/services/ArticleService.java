package sk.peterjurkovic.cpr.services;

import java.util.List;
import java.util.Map;

import sk.peterjurkovic.cpr.entities.Article;

public interface ArticleService {
	
	void createArticle(Article article);
	
	void updateArticle(Article article);
	
	void deleteArticle(Article article);
	
	Article getArticleById(Long id);
	
	Article getArticleByCode(String code);
	
	List<Article> getAll();
	
	void saveOrUpdate(Article article);
	
	List<Article> getArticlePage(int pageNumber, Map<String,Object> criteria);
	
	Long getCountOfArticles(Map<String,Object> criteria);
	
	List<Article> autocomplateSearch(final String query);
	
	List<Article> getNewestArticles(int count);
	
	List<Article> getArticlePageForPublic(int pageNumber);
	
	Long getCountOfArticlesForPublic();
}
