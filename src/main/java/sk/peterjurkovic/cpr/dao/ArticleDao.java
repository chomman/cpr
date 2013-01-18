package sk.peterjurkovic.cpr.dao;

import sk.peterjurkovic.cpr.entities.Article;

public interface ArticleDao extends BaseDao<Article, Long>{
	
	Long getNextIdValue();
}
