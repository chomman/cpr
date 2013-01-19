package sk.peterjurkovic.cpr.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.constants.CacheRegion;
import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.dao.ArticleDao;
import sk.peterjurkovic.cpr.entities.Article;
import sk.peterjurkovic.cpr.enums.ArticleOrder;

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

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Article> getArticlePage(int pageNumber,Map<String, Object> criteria) {
		
		StringBuffer hql = new StringBuffer("from Article a");
		hql.append(prepareHqlForQuery(criteria));
		if((Integer)criteria.get("orderBy") != null){
			hql.append(ArticleOrder.getSqlById((Integer)criteria.get("orderBy") ));
		}else{
			hql.append(ArticleOrder.getSqlById(1));
		}

		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		prepareHqlQueryParams(hqlQuery, criteria);
		hqlQuery.setFirstResult(Constants.PAGINATION_PAGE_SIZE * ( pageNumber -1));
		hqlQuery.setMaxResults(Constants.PAGINATION_PAGE_SIZE);
		hqlQuery.setCacheable(true);
		hqlQuery.setCacheRegion(CacheRegion.NEWS_CACHE);
		return hqlQuery.list();
	}

	@Override
	public Long getCountOfArticles(Map<String, Object> criteria) {
		StringBuffer hql = new StringBuffer("SELECT count(*) FROM Article a");
		hql.append(prepareHqlForQuery(criteria));
		Query hqlQuery = sessionFactory.getCurrentSession().createQuery(hql.toString());
		prepareHqlQueryParams(hqlQuery, criteria);
		hqlQuery.setCacheable(true);
		hqlQuery.setCacheRegion(CacheRegion.NEWS_CACHE);
	    return (Long) hqlQuery.uniqueResult();
	}
	
	
	
	private String prepareHqlForQuery(Map<String, Object> criteria){
		List<String> where = new ArrayList<String>();
		if(criteria.size() != 0){
			if((String)criteria.get("query") != null){
				where.add(" a.title like CONCAT('%', :query , '%')");
			}
			if((DateTime)criteria.get("publishedSince") != null){
				where.add(" CASE WHEN (a.publishedSince is not null THEN (a.publishedSince<=:publishedSince) ELSE (a.created<=:publishedSince) END ");
			}
			if((DateTime)criteria.get("publishedUntil") != null){
				where.add(" CASE WHEN (a.publishedUntil is not null THEN (a.publishedUntil>=:publishedUntil) ELSE (a.created>=:publishedUntil) END ");
			}
			Long groupId = (Long)criteria.get("enabled");
			if(groupId != null){
				where.add(" (a.enabled=:enabled)");
			}
		}
		return (where.size() > 0 ? " WHERE " + StringUtils.join(where.toArray(), " AND ") : "");

	}
	
	
	
	private void prepareHqlQueryParams(Query hqlQuery, Map<String, Object> criteria){
		if(criteria.size() != 0){
			if((String)criteria.get("query") != null){
				hqlQuery.setString("query", (String)criteria.get("query"));
			}
			DateTime publishedSince = (DateTime)criteria.get("publishedSince");
			if(publishedSince != null){
				hqlQuery.setTimestamp("publishedSince", publishedSince.toDate());
			}
			DateTime publishedUntil = (DateTime)criteria.get("publishedUntil");
			if(publishedUntil != null){
				hqlQuery.setTimestamp("publishedUntil", publishedUntil.toDate());
			}
		   
			Boolean enabled = (Boolean)criteria.get("groupId");
			if(enabled != null){
				hqlQuery.setBoolean("enabled", enabled);
			}
		}
	}
}
