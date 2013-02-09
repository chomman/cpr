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
import sk.peterjurkovic.cpr.dao.StandardDao;
import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.entities.StandardGroup;
import sk.peterjurkovic.cpr.enums.StandardOrder;

@Repository("standardDao")
public class StandardDaoImpl extends BaseDaoImpl<Standard, Long> implements StandardDao {
	
	public StandardDaoImpl(){
		super(Standard.class);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Standard> getStandardPage(final int pageNumber,final Map<String, Object> criteria ){
		StringBuffer hql = new StringBuffer("from Standard s");
		hql.append(prepareHqlForQuery(criteria));
		if((Integer)criteria.get("orderBy") != null){
			hql.append(StandardOrder.getSqlById((Integer)criteria.get("orderBy") ));
		}else{
			hql.append(StandardOrder.getSqlById(6));
		}
		
		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		prepareHqlQueryParams(hqlQuery, criteria);
		hqlQuery.setFirstResult(Constants.ADMIN_PAGINATION_PAGE_SIZE * ( pageNumber -1));
		hqlQuery.setMaxResults(Constants.ADMIN_PAGINATION_PAGE_SIZE);
		hqlQuery.setCacheable(true);
		hqlQuery.setCacheRegion(CacheRegion.CPR_CACHE);
		return hqlQuery.list();
	}
	
	public Long getCountOfSdandards(final Map<String, Object> criteria) {
		StringBuffer hql = new StringBuffer("SELECT count(*) FROM Standard s");
		hql.append(prepareHqlForQuery(criteria));
		Query hqlQuery = sessionFactory.getCurrentSession().createQuery(hql.toString());
		prepareHqlQueryParams(hqlQuery, criteria);
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
	
	
	private String prepareHqlForQuery(Map<String, Object> criteria){
		List<String> where = new ArrayList<String>();
		if(criteria.size() != 0){
			if((String)criteria.get("query") != null){
				where.add(" s.standardId like CONCAT('%', :query , '%')");
			}
			if((DateTime)criteria.get("startValidity") != null){
				where.add(" (s.startValidity<=:startValidity)");
			}
			if((DateTime)criteria.get("stopValidity") != null){
				where.add(" (s.stopValidity>=:stopValidity)");
			}
			Long groupId = (Long)criteria.get("groupId");
			if(groupId != null && groupId != 0){
				where.add(" (s.standardGroup.id=:groupId)");
			}
		}
		return (where.size() > 0 ? " WHERE " + StringUtils.join(where.toArray(), " AND ") : "");

	}
	
	private void prepareHqlQueryParams(Query hqlQuery, Map<String, Object> criteria){
		if(criteria.size() != 0){
			if((String)criteria.get("query") != null){
				hqlQuery.setString("query", (String)criteria.get("query"));
			}
			DateTime startValidity = (DateTime)criteria.get("startValidity");
			if(startValidity != null){
				
				hqlQuery.setTimestamp("startValidity", startValidity.toDate());
			}
			DateTime stopValidity = (DateTime)criteria.get("stopValidity");
			if(stopValidity != null){
				hqlQuery.setTimestamp("stopValidity", stopValidity.toDate());
			}
			Long groupId = (Long)criteria.get("groupId");
			if(groupId != null && groupId != 0){
				hqlQuery.setLong("groupId", groupId);
			}
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Standard> getLastEditedOrNewestStandards(final int count) {
		StringBuilder hql = new StringBuilder("from Standard s where s.enabled=true order by s.changed desc");
		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		hqlQuery.setMaxResults(count);
		hqlQuery.setCacheable(true);
		hqlQuery.setCacheRegion(CacheRegion.CPR_CACHE);
		return hqlQuery.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Standard> getStandardByStandardGroupForPublic(StandardGroup StandardGroup){
		return sessionFactory.getCurrentSession()
				.createQuery("from Standard s where s.enabled=true and s.standardGroup.id=:id order by s.standardId")
				.setLong("id", StandardGroup.getId())
				.setCacheable(true)
				.setCacheRegion(CacheRegion.CPR_CACHE)
				.list();
	}
	
}
