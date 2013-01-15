package sk.peterjurkovic.cpr.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.constants.CacheRegion;
import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.dao.StandardDao;
import sk.peterjurkovic.cpr.entities.Standard;

@Repository("standardDao")
public class StandardDaoImpl extends BaseDaoImpl<Standard, Long> implements StandardDao {
	
	public static final int ORDER_BY_NAME_ASC = 1;
	public static final int ORDER_BY_NAME_DESC = 2;
	public static final int ORDER_BY_STANDARD_ID_ASC = 3;
	public static final int ORDER_BY_STANDARD_ID_DESC = 4;
	public static final int ORDER_BY_CREATE_ASC = 5;
	public static final int ORDER_BY_CREATE_DESC = 6;
	
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
		List<Standard> standards = new ArrayList<Standard>();
		standards = sessionFactory.getCurrentSession()
				.createQuery("from Standard s")
				.setFirstResult(Constants.PAGINATION_PAGE_SIZE * ( pageNumber -1))
				.setMaxResults(Constants.PAGINATION_PAGE_SIZE)
				.setCacheable(true)
				.setCacheRegion(CacheRegion.CPR_CACHE)
				.list();
		return standards;
	}
	
	public Long getCountOfSdandards(final Long standardGroupId, 
			int orderById,
			String query,
			DateTime startValidity, 
			DateTime stopValidity
			) {
		StringBuffer hql = new StringBuffer("SELECT count(*) FROM Standard s");

		return (Long) sessionFactory.getCurrentSession()
				.createQuery(hql.toString())
				.setCacheable(true)
				.setCacheRegion(CacheRegion.CPR_CACHE)
				.uniqueResult();

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
	
	
	
	
}
