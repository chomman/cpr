package sk.peterjurkovic.cpr.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.constants.CacheRegion;
import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.dao.DeclarationOfPerformanceDao;
import sk.peterjurkovic.cpr.entities.DeclarationOfPerformance;

@Repository("declarationOfPerformanceDao")
public class DeclarationOfPerformanceDaoImpl 
		extends BaseDaoImpl<DeclarationOfPerformance, Long> 
		implements DeclarationOfPerformanceDao{
	
	
	public DeclarationOfPerformanceDaoImpl(){
		super(DeclarationOfPerformance.class);
	}

	
	@Override
	public DeclarationOfPerformance getByToken(final String token) {
		return (DeclarationOfPerformance) sessionFactory.getCurrentSession()
				.createCriteria(DeclarationOfPerformance.class)
				.add( Restrictions.eq("token", token) )
				.setMaxResults(1)
				.uniqueResult();
		
	}


	@Override
	public void deleteEssentialCharacteristicByDopId(final Long id) {
		sessionFactory.getCurrentSession()
			.createQuery("delete from EssentialCharacteristic ch where ch.declarationOfPerformance.id=:id")
			.setLong("id", id)
			.executeUpdate();
	}

	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<DeclarationOfPerformance> getDopPage(final int pageNumber,final Map<String, Object> criteria){ 
		StringBuffer hql = new StringBuffer("from DeclarationOfPerformance dop ");
		hql.append(prepareHqlForQuery(criteria));
		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		prepareHqlQueryParams(hqlQuery, criteria);
		hqlQuery.setFirstResult(Constants.ADMIN_PAGINATION_PAGE_SIZE * ( pageNumber -1));
		hqlQuery.setMaxResults(Constants.ADMIN_PAGINATION_PAGE_SIZE);
		hqlQuery.setCacheable(true);
		hqlQuery.setCacheRegion(CacheRegion.CPR_CACHE);
		return hqlQuery.list();
	}


	
	@Override
	public Long getCountOfDop(final Map<String, Object> criteria) {
		StringBuffer hql = new StringBuffer("SELECT count(*) FROM DeclarationOfPerformance dop ");
		hql.append(prepareHqlForQuery(criteria));
		Query hqlQuery = sessionFactory.getCurrentSession().createQuery(hql.toString());
		prepareHqlQueryParams(hqlQuery, criteria);
		hqlQuery.setCacheable(true);
		hqlQuery.setCacheRegion(CacheRegion.CPR_CACHE);
	    return (Long) hqlQuery.uniqueResult();
	}
	
	
	
	
	private String prepareHqlForQuery(final Map<String, Object> criteria){
		List<String> where = new ArrayList<String>();
		if(criteria.size() != 0){
			if((DateTime)criteria.get("createdFrom") != null){
				where.add(" dop.created>=:createdFrom");
			}
			if((DateTime)criteria.get("createdTo") != null){
				where.add(" dop.created<=:createdTo");
			}
		}
		return (where.size() > 0 ? " WHERE " + StringUtils.join(where.toArray(), " AND ") : "");

	}
	
	
	private void prepareHqlQueryParams(final Query hqlQuery,final Map<String, Object> criteria){
		if(criteria.size() != 0){
			
			DateTime createdFrom = (DateTime)criteria.get("createdFrom");
			if(createdFrom != null){
				hqlQuery.setTimestamp("createdFrom", createdFrom.toDate());
			}
			DateTime createdTo = (DateTime)criteria.get("createdTo");
			if(createdTo != null){
				hqlQuery.setTimestamp("createdTo", createdTo.plusDays(1).toDate());
			}
		}
	}


}
