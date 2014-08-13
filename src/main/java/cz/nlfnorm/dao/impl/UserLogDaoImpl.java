package cz.nlfnorm.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.dao.UserLogDao;
import cz.nlfnorm.entities.UserLog;
import cz.nlfnorm.enums.UserLogOrder;


@Repository("userLog")
public class UserLogDaoImpl extends BaseDaoImpl<UserLog, Long> implements UserLogDao{

	public UserLogDaoImpl() {
		super(UserLog.class);
	}

	/**
	 * Metoda vrati zalogovany zaznam podla zadanej session ID.
	 * 
	 * @param String sessionID
	 * @return UserLog zalogovany zaznam Uzivatela
	 */
	@Override
	public UserLog getBySessionId(String sessionId) {
		 StringBuffer hql = new StringBuffer();
         hql.append("  FROM UserLog l ");
         hql.append("  WHERE l.sessionId = :sessionId");
         return (UserLog) sessionFactory.getCurrentSession()
         				.createQuery(hql.toString())
         				.setString("sessionId", sessionId)
         				.setMaxResults(1)
         				.uniqueResult();
	}
	
	
	/**
	 * Metoda vrati stranku pristupov do administracnej sekcie systemu
	 * 
	 * @param int cislo stranky
	 * @param kriteria, podla ktorych sa vykona filter
	 * 
	 * @return stranka uzivatelskych pristpov
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserLog> getLogPage(int pageNumber, Map<String, Object> criteria) {
		StringBuffer hql = new StringBuffer("from UserLog ul");
		hql.append(prepareHqlForQuery(criteria));
		if((Integer)criteria.get("orderBy") != null){
			hql.append(UserLogOrder.getSqlById((Integer)criteria.get("orderBy") ));
		}else{
			hql.append(UserLogOrder.getSqlById(1));
		}
		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		prepareHqlQueryParams(hqlQuery, criteria);
		hqlQuery.setFirstResult(Constants.ADMIN_PAGINATION_PAGE_SIZE * ( pageNumber -1));
		hqlQuery.setMaxResults(Constants.ADMIN_PAGINATION_PAGE_SIZE);
		return (List<UserLog>) hqlQuery.list();
	}

	
	
	/**
	 * Vrati pocet uzivatelskych pristupov, vyhovujucich danym kriteriam
	 * 
	 * @param Map<String, Object> kriteria, podla ktorych sa aplikuje filter
	 * @return Long pocet vyhovujucich zaznamov
	 */
	@Override
	public Long getCountOfLogs(Map<String, Object> criteria) {
		StringBuffer hql = new StringBuffer("SELECT count(*) FROM UserLog ul");
		hql.append(prepareHqlForQuery(criteria));
		Query hqlQuery = sessionFactory.getCurrentSession().createQuery(hql.toString());
		prepareHqlQueryParams(hqlQuery, criteria);
	    return (Long) hqlQuery.uniqueResult();
	}
	
	

	
	private String prepareHqlForQuery(final Map<String, Object> criteria){
		List<String> where = new ArrayList<String>();
		if(criteria.size() != 0){
			if(StringUtils.isNotBlank((String)criteria.get("query"))){
				where.add(
						  " ( unaccent(lower(ul.user.firstName)) like unaccent(lower(CONCAT('%', :query , '%'))) OR" +
						  "   unaccent(lower(ul.user.lastName)) like unaccent(lower(CONCAT('%', :query , '%'))) OR" +
						  "   ul.user.email like CONCAT('%', :query , '%')  )"
						  );
			}
			if((DateTime)criteria.get("createdFrom") != null){
				where.add(" ul.loginDateAndTime >= :createdFrom ");
			}
			if((DateTime)criteria.get("createdTo") != null){
				where.add(" ul.loginDateAndTime <= :createdTo ");
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



	
}
