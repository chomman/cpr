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
	public List<Article> autocomplateSearch(final String query) {
		StringBuilder hql = new StringBuilder("select a.id, a.title from Article a WHERE ");
		hql.append(" a.title like CONCAT('%', :query , '%') ");
		List<Article> articles = new ArrayList<Article>();
		articles = sessionFactory.getCurrentSession()
				.createQuery(hql.toString())
				.setString("query", query)
				.setMaxResults(8)
				.list();
		return articles;
	}
	

	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Article> getArticlePage(final int pageNumber,final Map<String, Object> criteria) {
		
		StringBuffer hql = new StringBuffer("from Article a");
		hql.append(prepareHqlForQuery(criteria));
		if((Integer)criteria.get("orderBy") != null){
			hql.append(ArticleOrder.getSqlById((Integer)criteria.get("orderBy") ));
		}else{
			hql.append(ArticleOrder.getSqlById(1));
		}

		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		prepareHqlQueryParams(hqlQuery, criteria);
		hqlQuery.setFirstResult(Constants.ADMIN_PAGINATION_PAGE_SIZE * ( pageNumber -1));
		hqlQuery.setMaxResults(Constants.ADMIN_PAGINATION_PAGE_SIZE);
		hqlQuery.setCacheable(true);
		hqlQuery.setCacheRegion(CacheRegion.NEWS_CACHE);
		return hqlQuery.list();
	}

	@Override
	public Long getCountOfArticles(final Map<String, Object> criteria) {
		StringBuffer hql = new StringBuffer("SELECT count(*) FROM Article a");
		hql.append(prepareHqlForQuery(criteria));
		Query hqlQuery = sessionFactory.getCurrentSession().createQuery(hql.toString());
		prepareHqlQueryParams(hqlQuery, criteria);
		hqlQuery.setCacheable(true);
		hqlQuery.setCacheRegion(CacheRegion.NEWS_CACHE);
		return (Long) hqlQuery.uniqueResult();
	}
	
	
	
	private String prepareHqlForQuery(final Map<String, Object> criteria){
		List<String> where = new ArrayList<String>();
		if(criteria.size() != 0){
			if(StringUtils.isNotBlank((String)criteria.get("query"))){
				where.add(" a.title like CONCAT('%', :query , '%')");
			}
			if((DateTime)criteria.get("publishedSince") != null){
				where.add(" a.created >= :publishedSince ");
			}
			if((DateTime)criteria.get("publishedUntil") != null){
				where.add(" a.created < :publishedUntil ");
			}
			Boolean enabled = (Boolean)criteria.get("enabled");
			if(enabled != null){
				where.add(" (a.enabled=:enabled)");
			}
		}
		return (where.size() > 0 ? " WHERE " + StringUtils.join(where.toArray(), " AND ") : "");

	}
	
	
	
	private void prepareHqlQueryParams(final Query hqlQuery,final Map<String, Object> criteria){
		if(criteria.size() != 0){
			if(StringUtils.isNotBlank((String)criteria.get("query"))){
				hqlQuery.setString("query", (String)criteria.get("query"));
			}
			DateTime publishedSince = (DateTime)criteria.get("publishedSince");
			if(publishedSince != null){
				hqlQuery.setTimestamp("publishedSince", publishedSince.toDate());
			}
			DateTime publishedUntil = (DateTime)criteria.get("publishedUntil");
			if(publishedUntil != null){
				hqlQuery.setTimestamp("publishedUntil", publishedUntil.plusDays(1).toDate());
			}
		   
			Boolean enabled = (Boolean)criteria.get("enabled");
			if(enabled != null){
				hqlQuery.setBoolean("enabled", enabled);
			}
		}
	}
	/**
	 * select 
       case 
         when min(start_day_plan) is not NULL then min(start_day_plan) 
         else to_date((min(insertDate)) - cast('1 month' as interval),'yyyy-MM-dd' ) 
       end
     from MyTable
	 * @return
	 */
	
	private StringBuffer getHqlArticleQueryForPublicSection(){
		StringBuffer hql = new StringBuffer("from Article a");
		hql.append(" where a.enabled = true ");
		hql.append(" and (now() >=a.publishedSince or a.publishedSince = null)");
		hql.append(" and (now() <=a.publishedUntil or a.publishedUntil = null)");
        hql.append(" order by a.id desc");
        return hql;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Article> getNewestArticles(int count) {
		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(getHqlArticleQueryForPublicSection().toString());
		hqlQuery.setMaxResults(count);
		hqlQuery.setCacheable(true);
		hqlQuery.setCacheRegion(CacheRegion.NEWS_CACHE);
		return hqlQuery.list();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Article> getArticlePageForPublic(final int pageNumber) {
		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(getHqlArticleQueryForPublicSection().toString());
		hqlQuery.setFirstResult(Constants.PUBLIC_PAGINATION_PAGE_SIZE * ( pageNumber -1));
		hqlQuery.setMaxResults(Constants.PUBLIC_PAGINATION_PAGE_SIZE);
		hqlQuery.setCacheable(true);
		hqlQuery.setCacheRegion(CacheRegion.NEWS_CACHE);
		return hqlQuery.list();
	}
	
	
	@Override
	public Long getCountOfArticlesForPublic() {
		StringBuffer hql = new StringBuffer("SELECT count(*) ");
		hql.append(getHqlArticleQueryForPublicSection().toString());
		Query hqlQuery = sessionFactory.getCurrentSession().createQuery(hql.toString());
		hqlQuery.setCacheable(true);
		hqlQuery.setCacheRegion(CacheRegion.NEWS_CACHE);
		return (Long) hqlQuery.uniqueResult();
	}
}
