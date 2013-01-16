package sk.peterjurkovic.cpr.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.constants.CacheRegion;
import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.dao.StandardDao;
import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.enums.StandardOrder;

@Repository("standardDao")
public class StandardDaoImpl extends BaseDaoImpl<Standard, Long> implements StandardDao {
	
	public StandardDaoImpl(){
		super(Standard.class);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Standard> getStandardPage(final int pageNumber,Long standardGroupId, 
			int orderById,
			String query,
			DateTime startValidity, 
			DateTime stopValidity
			){
		StringBuffer hql = new StringBuffer("from Standard s");
		hql.append(prepareFIlterQuery(query, startValidity, stopValidity));
		hql.append(StandardOrder.getSqlById(orderById));
		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		if(query != null){
			hqlQuery.setString("query", query);
		}
		if(startValidity != null){
			
			hqlQuery.setTimestamp("startValidity", startValidity.toDate());
		}
		if(stopValidity != null){
			hqlQuery.setTimestamp("stopValidity", stopValidity.toDate());
		}
		hqlQuery.setFirstResult(Constants.PAGINATION_PAGE_SIZE * ( pageNumber -1));
		hqlQuery.setMaxResults(Constants.PAGINATION_PAGE_SIZE);
		hqlQuery.setCacheable(true);
		hqlQuery.setCacheRegion(CacheRegion.CPR_CACHE);
		return hqlQuery.list();
	}
	
	public Long getCountOfSdandards(final Long standardGroupId, 
			int orderById,
			String query,
			DateTime startValidity, 
			DateTime stopValidity
			) {
		StringBuffer hql = new StringBuffer("SELECT count(*) FROM Standard s");
		hql.append(prepareFIlterQuery(query, startValidity, stopValidity));
		hql.append(StandardOrder.getSqlById(orderById));
		Query hqlQuery = sessionFactory.getCurrentSession().createQuery(hql.toString());
		if(query != null){
			hqlQuery.setString("query", query);
		}
		if(startValidity != null){
			logger.info(startValidity.toString());
			hqlQuery.setTimestamp("startValidity", startValidity.toDate());
		}
		if(stopValidity != null){
			hqlQuery.setTimestamp("stopValidity", stopValidity.toDate());
		}
		hqlQuery.setCacheable(true);
		hqlQuery.setCacheRegion(CacheRegion.CPR_CACHE);
	    return (Long) hqlQuery.uniqueResult();

	}


	@Override
	public boolean isStandardIdUnique(final String standardId,final Long id) {
		StringBuilder hql = new StringBuilder("SELECT count(*) FROM Standard s WHERE s.standardId=:standardId");
		if(id != 0){
			hql.append(" AND s.id<>:id");
		}
		Long result = null;
		Query query = sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setString("standardId", standardId);
		if(id != 0){
			query.setLong("id", id);
		}
		result = (Long)query.uniqueResult();
		logger.info("Is uniqe: " + (result == 0));
		return (result == 0);
	}


	@Override
	public void clearStandardTags(final Standard standard) {
		StringBuilder hql = new StringBuilder("delete from Tag tag where tag.standard=:standard");
		sessionFactory.getCurrentSession()
		.createQuery(hql.toString())
		.setEntity("standard", standard)
		.executeUpdate();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Standard> autocomplateSearch(final String query) {
		StringBuilder hql = new StringBuilder("select s.id, s.standardId, s.standardName from Standard s");
		hql.append(" where s.standardId like :query ");
		List<Standard> standards = new ArrayList<Standard>();
		standards = sessionFactory.getCurrentSession()
				.createQuery(hql.toString())
				.setString("query", "%" + query + "%")
				.setMaxResults(8)
				.list();
		return standards;
	}
	
	
	private String prepareFIlterQuery(String query,	DateTime startValidity, DateTime stopValidity){
		List<String> where = new ArrayList<String>();
		if(query != null){
			where.add(" s.standardId like CONCAT('%', :query , '%')");
		}
		if(startValidity != null){
			where.add(" (s.startValidity<=:startValidity)");
		}
		
		if(stopValidity != null){
			where.add(" (s.stopValidity>=:stopValidity)");
		}
		
		return (where.size() > 0 ? " WHERE " + StringUtils.join(where.toArray(), " AND ") : "");

	}
	
}
