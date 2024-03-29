package cz.nlfnorm.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.dao.ExceptionLogDao;
import cz.nlfnorm.entities.ExceptionLog;
import cz.nlfnorm.enums.ExceptionLogOrder;

@Repository("exceptionLogDao")
public class ExceptionLogDaoImpl extends BaseDaoImpl<ExceptionLog, Long> implements ExceptionLogDao{
	
	public ExceptionLogDaoImpl(){
		super(ExceptionLog.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExceptionLog> getExceptionLogPage(int pageNumber, Map<String, Object> criteria) {
		StringBuilder hql = new StringBuilder("from ExceptionLog el");
		hql.append(prepareHqlForQuery(criteria));
		if((Integer)criteria.get("orderBy") != null){
			hql.append(ExceptionLogOrder.getSqlById((Integer)criteria.get("orderBy") ));
		}else{
			hql.append(ExceptionLogOrder.getSqlById(1));
		}
		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		prepareHqlQueryParams(hqlQuery, criteria);
		hqlQuery.setFirstResult(Constants.ADMIN_PAGINATION_PAGE_SIZE * ( pageNumber -1));
		hqlQuery.setMaxResults(Constants.ADMIN_PAGINATION_PAGE_SIZE);
		return (List<ExceptionLog>) hqlQuery.list();
	}

	@Override
	public Long getCountOfLogs(Map<String, Object> criteria) {
		StringBuilder hql = new StringBuilder("SELECT count(*) FROM ExceptionLog el");
		hql.append(prepareHqlForQuery(criteria));
		Query hqlQuery = sessionFactory.getCurrentSession().createQuery(hql.toString());
		prepareHqlQueryParams(hqlQuery, criteria);
	    return (Long) hqlQuery.uniqueResult();
	}
	
	
	private String prepareHqlForQuery(final Map<String, Object> criteria){
		List<String> where = new ArrayList<String>();
		if(criteria.size() != 0){
			if(StringUtils.isNotBlank((String)criteria.get("query"))){
				where.add(" el.type like CONCAT('%', :query , '%')");
			}
			if((DateTime)criteria.get("createdFrom") != null){
				where.add(" el.created >= :createdFrom ");
			}
			if((DateTime)criteria.get("createdTo") != null){
				where.add(" el.created <= :createdTo ");
			}
		}
		return (where.size() > 0 ? " WHERE " + StringUtils.join(where.toArray(), " AND ") : "");

	}
	
	
	
	
	private void prepareHqlQueryParams(final Query hqlQuery,final Map<String, Object> criteria){
		if(criteria.size() != 0){
			if(StringUtils.isNotBlank((String)criteria.get("query"))){
				hqlQuery.setString("query", (String)criteria.get("query"));
			}
			DateTime publishedSince = (DateTime)criteria.get("createdFrom");
			if(publishedSince != null){
				hqlQuery.setTimestamp("createdFrom", publishedSince.toDate());
			}
			DateTime publishedUntil = (DateTime)criteria.get("createdTo");
			if(publishedUntil != null){
				hqlQuery.setTimestamp("createdTo", publishedUntil.toDate());
			}
		}
	}

	@Override
	public ExceptionLog getLastException() {
		Query hqlQuery =  sessionFactory
							.getCurrentSession()
							.createQuery("from ExceptionLog e order by e.id desc");
		hqlQuery.setMaxResults(1);
		hqlQuery.setCacheable(false);
		return (ExceptionLog)hqlQuery.uniqueResult();
	}
}
