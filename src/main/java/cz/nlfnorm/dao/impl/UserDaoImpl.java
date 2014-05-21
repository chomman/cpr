package cz.nlfnorm.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Repository;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.constants.Filter;
import cz.nlfnorm.dao.UserDao;
import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.Authority;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.enums.UserOrder;


/**
 * Implementacia UserDao
 * 
 * @see {@link cz.nlfnorm.dao.UserDao}
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

	

	private void appendOrderBy(Map<String, Object> criteria, StringBuilder hql){
		if((Integer)criteria.get("orderBy") != null){
			hql.append(UserOrder.getSqlById((Integer)criteria.get("orderBy") ));
		}else{
			hql.append(UserOrder.getSqlById(1));
		}
	}
	
		
	private String prepareHqlForQuery(final Map<String, Object> criteria){
		List<String> where = new ArrayList<String>();
		if(criteria.size() != 0){
			if(StringUtils.isNotBlank((String)criteria.get("query"))){
				where.add(" (u.firstName like CONCAT('%', :query , '%') OR " +
						  " u.lastName like CONCAT('%', :query , '%') OR " +
						  " u.email like CONCAT('%', :query , '%')) ");
			}
			if((DateTime)criteria.get(Filter.CREATED_FROM) != null){
				where.add(" u.created >= :"+Filter.CREATED_FROM);
			}
			if((DateTime)criteria.get(Filter.CREATED_TO) != null){
				where.add(" u.created < :"+Filter.CREATED_TO);
			}
			Boolean enabled = (Boolean)criteria.get(Filter.ENABLED);
			if(enabled != null){
				where.add(" u.enabled=:"+Filter.ENABLED);
			}
			if(StringUtils.isNotBlank((String)criteria.get(Filter.AUHTORITY))){
				where.add(" a.code =:"+Filter.AUHTORITY);
			}
		}
		return (where.size() > 0 ? " WHERE " + StringUtils.join(where.toArray(), " AND ") : "");

	}
	
	
	
	private void prepareHqlQueryParams(final Query hqlQuery,final Map<String, Object> criteria){
		if(criteria.size() != 0){
			if(StringUtils.isNotBlank((String)criteria.get("query"))){
				hqlQuery.setString("query", (String)criteria.get("query"));
			}
			DateTime publishedSince = (DateTime)criteria.get(Filter.CREATED_FROM);
			if(publishedSince != null){
				hqlQuery.setTimestamp(Filter.CREATED_FROM, publishedSince.toDate());
			}
			DateTime publishedUntil = (DateTime)criteria.get(Filter.CREATED_TO);
			if(publishedUntil != null){
				hqlQuery.setTimestamp(Filter.CREATED_TO, publishedUntil.plusDays(1).toDate());
			}
		   
			Boolean enabled = (Boolean)criteria.get(Filter.ENABLED);
			if(enabled != null){
				hqlQuery.setBoolean(Filter.ENABLED, enabled);
			}
			String authorityCode = (String)criteria.get(Filter.AUHTORITY);
			if(StringUtils.isNotBlank(authorityCode)){
				hqlQuery.setString(Filter.AUHTORITY, authorityCode);
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

	
	
	@SuppressWarnings("unchecked")
	@Override
	public PageDto getUserPage(int currentPage, Map<String, Object> criteria) {
		StringBuilder hql = new StringBuilder("from User u ");
		hql.append(" left join u.authoritySet a ");
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
				hql.append(" group by u.id ");
				appendOrderBy(criteria, hql);
				hqlQuery = sessionFactory.getCurrentSession().createQuery("select u " + hql.toString());
				prepareHqlQueryParams(hqlQuery, criteria); 
				hqlQuery.setCacheable(false);
				hqlQuery.setFirstResult(Constants.ADMIN_PAGINATION_PAGE_SIZE * ( currentPage -1));
				hqlQuery.setMaxResults(Constants.ADMIN_PAGINATION_PAGE_SIZE);
				items.setItems(hqlQuery.list());
			}
		}
		return items;
		
	}

	@Override
	public User getUserByChangePasswordRequestToken(final String token) {
		final LocalDateTime now = new LocalDateTime();
		StringBuilder hql = new StringBuilder("from User u ");
		hql.append("where u.enabled=true AND u.changePasswordRequestToken=:token AND ")
			.append(" u.changePasswordRequestDate > :now" );
		Query query = sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setMaxResults(1);
		query.setCacheable(false);
		query.setString("token", token);
		query.setTimestamp("now", now.toDate());
		return (User)query.uniqueResult();
	}

	
	
}
