package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.ArticleDao;
import sk.peterjurkovic.cpr.entities.Article;

@Repository("articleDao")
public class ArticleDaoImpl extends BaseDaoImpl<Article, Long>  implements ArticleDao {

	public ArticleDaoImpl(){
		super(Article.class);
	}

	@Override
	public Long getNextIdValue() {
		Long nextId = (Long) sessionFactory.getCurrentSession()
				.createQuery("SELECT MAX(article.id) FROM Article article")
				.setCacheable(false)
				.setMaxResults(1)
				.uniqueResult();
        if (nextId == null) {
            return 1L;
        }
        return nextId + 1;
	}
}
