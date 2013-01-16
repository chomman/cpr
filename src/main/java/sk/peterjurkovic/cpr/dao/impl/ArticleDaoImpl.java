package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.ArticleDao;
import sk.peterjurkovic.cpr.entities.Article;

@Repository("articleDao")
public class ArticleDaoImpl extends BaseDaoImpl<Article, Long>  implements ArticleDao {

	public ArticleDaoImpl(){
		super(Article.class);
	}
}
