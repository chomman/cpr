package sk.peterjurkovic.cpr.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.dao.CsnTerminologyDao;
import sk.peterjurkovic.cpr.dto.PageDto;
import sk.peterjurkovic.cpr.entities.CsnTerminology;

/**
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 * @date Aug 7, 2013
 *
 */
@Repository("csnTerminologyDao")
public class CsnTerminologyDaoImpl extends BaseDaoImpl<CsnTerminology, Long> implements CsnTerminologyDao{
	
	public CsnTerminologyDaoImpl(){
		super(CsnTerminology.class);
	}

	
	@Override
	public boolean isTitleUniqe(final Long csnId,final Long terminologyId,final String code) {
		StringBuilder hql = new StringBuilder("SELECT count(*) FROM CsnTerminology ct WHERE ");
		hql.append(" ct.csn.id=:csnId AND ct.code=:code");
		if(terminologyId != null && terminologyId != 0){
			hql.append(" AND ct.id<>:terminologyId");
		}
		Long result = null;
		Query query = sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setString("code", code);
		query.setLong("csnId", csnId);
		if(terminologyId != null && terminologyId != 0){
			query.setLong("terminologyId", terminologyId);
		}
		result = (Long)query.uniqueResult();
		return (result == 0);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<CsnTerminology> searchInTerminology(final String term) {
		StringBuilder hql = new StringBuilder("select distinct t.title  from CsnTerminology t");
		hql.append(" where t.title like CONCAT('', :term , '%')");
		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		hqlQuery.setParameter("term", term);
		hqlQuery.setMaxResults(6);
		return hqlQuery.list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public PageDto getCsnTerminologyPage(int pageNumber, Map<String, Object> criteria) {
		StringBuilder hql = new StringBuilder("from CsnTerminology t ");
		hql.append(prepareHqlForQuery(criteria));
		Query hqlQuery = sessionFactory.getCurrentSession().createQuery("select count(*) " + hql.toString());
		prepareHqlQueryParams(hqlQuery, criteria);
		PageDto items = new PageDto();
		Long countOfItems = (Long)hqlQuery.uniqueResult();
		if(countOfItems == null){
			items.setCount(0l);
		}else{
			items.setCount(countOfItems);
			if(items.getCount() > 0){
				hqlQuery = sessionFactory.getCurrentSession().createQuery(hql.toString());
				prepareHqlQueryParams(hqlQuery, criteria);
				hqlQuery.setCacheable(false);
				hqlQuery.setFirstResult(Constants.ADMIN_PAGINATION_PAGE_SIZE * ( pageNumber -1));
				hqlQuery.setMaxResults(Constants.ADMIN_PAGINATION_PAGE_SIZE);
				items.setItems(hqlQuery.list());
			}
		}
		return items;
	}
	
	private String prepareHqlForQuery(final Map<String, Object> criteria){
		List<String> where = new ArrayList<String>();
		if(criteria.size() != 0){
			
			if(StringUtils.isNotBlank((String)criteria.get("query"))){
				where.add(" t.title like CONCAT('%', :query , '%') ");
			}
			
			if(StringUtils.isNotBlank((String)criteria.get("csnId"))){
				where.add(" t.csn.csnId like CONCAT('%', :csnId , '%') ");
			}
			
			if(StringUtils.isNotBlank((String)criteria.get("csnCategory"))){
				where.add(" t.csn.classificationSymbol like CONCAT('%', :csnCategory , '%')");	
			}
			
			if(StringUtils.isNotBlank((String)criteria.get("name"))){
				where.add(" t.csn.czechName like CONCAT('%', :name , '%') or t.csn.englishName like CONCAT('%', :name , '%') ");
			}

		}
		return (where.size() > 0 ? " WHERE " + StringUtils.join(where.toArray(), " AND ") : "");
	}
	
	
	private void prepareHqlQueryParams(final Query hqlQuery,final Map<String, Object> criteria){
		if(criteria.size() != 0){
			if(StringUtils.isNotBlank((String)criteria.get("query"))){
				hqlQuery.setString("query", (String)criteria.get("query"));
			}
			if(StringUtils.isNotBlank((String)criteria.get("csnId"))){
				hqlQuery.setString("csnId", (String)criteria.get("csnId"));
			}
			if(StringUtils.isNotBlank((String)criteria.get("csnId"))){
				hqlQuery.setString("csnId", (String)criteria.get("csnId"));
			}
			if(StringUtils.isNotBlank((String)criteria.get("csnCategory"))){
				hqlQuery.setString("csnCategory", (String)criteria.get("csnCategory"));
			}
			if(StringUtils.isNotBlank((String)criteria.get("name"))){
				hqlQuery.setString("name", (String)criteria.get("name"));
			}
		}
	}
	
}
