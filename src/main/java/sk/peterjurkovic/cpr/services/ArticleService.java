package sk.peterjurkovic.cpr.services;

import java.util.List;

import sk.peterjurkovic.cpr.entities.Article;

public interface ArticleService {
	
	void createArticle(Article article);
	
	void updateArticle(Article article);
	
	void deleteArticle(Article article);
	
	Article getArticleById(Long id);
	
	Article getArticleByCode(String code);
	
	List<Article> getAll();
	
	void saveOrUpdate(Article article);
	
}
