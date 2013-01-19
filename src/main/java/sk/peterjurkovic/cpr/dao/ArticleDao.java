package sk.peterjurkovic.cpr.dao;

import java.util.List;
import java.util.Map;

import sk.peterjurkovic.cpr.entities.Article;

public interface ArticleDao extends BaseDao<Article, Long>{
	
	Long getNextIdValue();
	
	List<Article> getArticlePage(int pageNumber, Map<String, Object> criteria);
	
	Long getCountOfArticles(Map<String, Object> criteria);
}
