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
import sk.peterjurkovic.cpr.dao.UserDao;
import sk.peterjurkovic.cpr.entities.Authority;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.enums.UserOrder;


/**
 * Implementacia UserDao
 * @author Peter Jurkovič (email@peterjurkovic.sk) 
 */
@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User, Long> implements UserDao{
	
	public UserDaoImpl() {
        super(User.class);
    }
	
	
	@Override
	public User getUserByUsername(String username) {
		return (User) sessionFactory.getCurrentSession()
				.createQuery("FROM User u WHERE u.email=:username")
				.setString("username", username)
				.setMaxResults(1)
				.uniqueResult();
	}

	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersByRole(String code) {
		return sessionFactory.getCurrentSession()
			.createQuery("FROM User u LEFT JOIN u.authoritySet authority WHERE authority.code = :code")
			.setString("code", code)
			.list();
	}

	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Authority> getAllAuthorities() {
		 StringBuffer hql = new StringBuffer();
         hql.append("	FROM Authority role ");
         hql.append("  WHERE role.enabled = true  ");
         hql.append("  ORDER BY role.name");
         
         return sessionFactory.getCurrentSession()
         				.createQuery(hql.toString())
         				.list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserPage(int pageNumber, Map<String, Object> criteria) {
		StringBuffer hql = new StringBuffer("from User u");
		hql.append(prepareHqlForQuery(criteria));
		if((Integer)criteria.get("orderBy") != null){
			hql.append(UserOrder.getSqlById((Integer)criteria.get("orderBy") ));
		}else{
			hql.append(UserOrder.getSqlById(1));
		}

		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		prepareHqlQueryParams(hqlQuery, criteria);
		hqlQuery.setFirstResult(Constants.PAGINATION_PAGE_SIZE * ( pageNumber -1));
		hqlQuery.setMaxResults(Constants.PAGINATION_PAGE_SIZE);
		hqlQuery.setCacheable(true);
		hqlQuery.setCacheRegion(CacheRegion.USER_CACHE);
		return hqlQuery.list();
	}


	@Override
	public Long getCountOfUsers(Map<String, Object> criteria) {
		StringBuffer hql = new StringBuffer("SELECT count(*) FROM User u");
		hql.append(prepareHqlForQuery(criteria));
		Query hqlQuery = sessionFactory.getCurrentSession().createQuery(hql.toString());
		prepareHqlQueryParams(hqlQuery, criteria);
		hqlQuery.setCacheable(true);
		hqlQuery.setCacheRegion(CacheRegion.USER_CACHE);
	    return (Long) hqlQuery.uniqueResult();
	}
	
	
	
	private String prepareHqlForQuery(final Map<String, Object> criteria){
		List<String> where = new ArrayList<String>();
		if(criteria.size() != 0){
			if(StringUtils.isNotBlank((String)criteria.get("query"))){
				where.add(" a.title like CONCAT('%', :query , '%')");
			}
			if((DateTime)criteria.get("createdFrom") != null){
				where.add(" a.created <= :createdFrom ");
			}
			if((DateTime)criteria.get("createdTo") != null){
				where.add(" a.created >= :createdTo ");
			}
			Long groupId = (Long)criteria.get("enabled");
			if(groupId != null){
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
			DateTime publishedSince = (DateTime)criteria.get("createdFrom");
			if(publishedSince != null){
				hqlQuery.setTimestamp("createdFrom", publishedSince.toDate());
			}
			DateTime publishedUntil = (DateTime)criteria.get("createdTo");
			if(publishedUntil != null){
				hqlQuery.setTimestamp("createdTo", publishedUntil.toDate());
			}
		   
			Boolean enabled = (Boolean)criteria.get("enabled");
			if(enabled != null){
				hqlQuery.setBoolean("enabled", enabled);
			}
		}
	}


	@Override
	public boolean isUserNameUniqe(Long id, String userName) {
		StringBuilder hql = new StringBuilder("SELECT count(*) FROM User u WHERE u.email=:userName");
		if(id != 0){
			hql.append(" AND u.id<>:id");
		}
		Long result = null;
		Query query = sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setString("userName", userName);
		if(id != 0){
			query.setLong("id", id);
		}
		result = (Long)query.uniqueResult();
		return (result == 0);
	}

}
