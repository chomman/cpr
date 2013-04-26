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
 * 
 * @see {@link sk.peterjurkovic.cpr.dao.UserDao}
 * @author Peter Jurkovič (email@peterjurkovic.sk) 
 */
@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User, Long> implements UserDao{
	
	public UserDaoImpl() {
        super(User.class);
    }
	
	/**
	 * Vrati uzivatela na zaklade daneho uzivatelskeho mena
	 * 
	 * @param String uzivatelske meno hladaneho uzivatela
	 * @return User
	 */
	@Override
	public User getUserByUsername(final String username) {
		return (User) sessionFactory.getCurrentSession()
				.createQuery("FROM User u WHERE u.email=:username")
				.setString("username", username)
				.setMaxResults(1)
				.uniqueResult();
	}

	/**
	 * Vrati zoznam uzivatelov, ktorych nazov, id, meno, priezvisko vyhovuje hladanemu vyrazu
	 * 
	 * @param String hladany term
	 * @return List<User> zoznam najdenych uzivatelov
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> autocomplateSearch(final String query) {
		StringBuilder hql = new StringBuilder("select u.id, u.firstName, u.lastName, u.email from User u WHERE ");
		hql.append(" u.firstName like CONCAT('%', :query , '%') OR");
		hql.append(" u.lastName like CONCAT('%', :query , '%') OR");
		hql.append(" u.email like CONCAT('%', :query , '%') ");
		List<User> users = new ArrayList<User>();
		users = sessionFactory.getCurrentSession()
				.createQuery(hql.toString())
				.setString("query", query)
				.setMaxResults(8)
				.list();
		return users;
	}
	
	/**
	 * Vrati zoznam uzivatelov s danou uzivatelskou rolou
	 * 
	 * @param String kod uzivatelskej role
	 * @return List<User> zoznam vyhovujucich uzivatelov
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersByRole(final String code) {
		return sessionFactory.getCurrentSession()
			.createQuery("FROM User u LEFT JOIN u.authoritySet authority WHERE authority.code = :code")
			.setString("code", code)
			.list();
	}

	
	/**
	 * Vrati zoznam vsetkych evidovanych, aktivovanych uzivatelskych roli
	 * @param  List<Authority> uzivatelske role
	 */
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

	/**
	 * Vrati stranku uzivatelov, vyhovujucich danym kriteriam
	 * 
	 * @param int cislo stranky
	 * @param Map<String, Object> kriteria
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserPage(final int pageNumber, Map<String, Object> criteria) {
		StringBuffer hql = new StringBuffer("from User u");
		hql.append(prepareHqlForQuery(criteria));
		if((Integer)criteria.get("orderBy") != null){
			hql.append(UserOrder.getSqlById((Integer)criteria.get("orderBy") ));
		}else{
			hql.append(UserOrder.getSqlById(1));
		}

		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		prepareHqlQueryParams(hqlQuery, criteria);
		hqlQuery.setFirstResult(Constants.ADMIN_PAGINATION_PAGE_SIZE * ( pageNumber -1));
		hqlQuery.setMaxResults(Constants.ADMIN_PAGINATION_PAGE_SIZE);
		hqlQuery.setCacheable(true);
		hqlQuery.setCacheRegion(CacheRegion.USER_CACHE);
		return hqlQuery.list();
	}

	
	/**
	 * Vrati pocet zaznamov/uzivatelov, vyhovujucich danym kriteriam
	 * 
	 * @param Map<String, Object> kriteria
	 * @return Long pocet zaznamov
	 */
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
				where.add(" (u.firstName like CONCAT('%', :query , '%') OR " +
						  " u.lastName like CONCAT('%', :query , '%') OR " +
						  " u.email like CONCAT('%', :query , '%')) ");
			}
			if((DateTime)criteria.get("createdFrom") != null){
				where.add(" u.created >= :createdFrom ");
			}
			if((DateTime)criteria.get("createdTo") != null){
				where.add(" u.created < :createdTo ");
			}
			Boolean enabled = (Boolean)criteria.get("enabled");
			if(enabled != null){
				where.add(" (u.enabled=:enabled)");
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
				hqlQuery.setTimestamp("createdTo", publishedUntil.plusDays(1).toDate());
			}
		   
			Boolean enabled = (Boolean)criteria.get("enabled");
			if(enabled != null){
				hqlQuery.setBoolean("enabled", enabled);
			}
		}
	}

	/**
	 * Skontroluje, ci je dane uzivatelske meno v ramci systemu jedinecne
	 * 
	 * @param Long id uzivatela
	 * @param String kontrolovane uzivatelske meno
	 */
	@Override
	public boolean isUserNameUniqe(final Long id, final String userName) {
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
