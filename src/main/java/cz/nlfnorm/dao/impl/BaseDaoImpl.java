package cz.nlfnorm.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cz.nlfnorm.constants.Filter;
import cz.nlfnorm.dao.BaseDao;



public class BaseDaoImpl<T, ID extends Serializable> extends HibernateDaoSupport implements BaseDao<T, Long> { 

	@Autowired
	protected SessionFactory sessionFactory;
	
	protected transient final Log logger = LogFactory.getLog(getClass());
	protected Class<T> persistentClass;
	
	public BaseDaoImpl(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }
	
	@Override
	public void save(T entity) {
		sessionFactory.getCurrentSession().save(entity);
	}
	
	@Override
	public void update(T entity) {
		sessionFactory.getCurrentSession().update(entity);
	}
	
	@Override
	public void remove(T entity) {
		sessionFactory.getCurrentSession().delete(entity);
	}
	
	@Override
	public void refresh(T entity) {
		sessionFactory.getCurrentSession().refresh(entity);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T merge(T entity) {
		 return (T)sessionFactory.getCurrentSession().merge(entity);
	}
	
	@Override
	public void evict(T entity) {
		 sessionFactory.getCurrentSession().evict(entity);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() {
		return (List<T>)sessionFactory.getCurrentSession()
				.createQuery("from " + persistentClass.getName()).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T getByID(Long id) {
		return (T)sessionFactory.getCurrentSession()
				.createQuery("from " + persistentClass.getName() + " entity WHERE entity.id=:id")
				.setLong("id", id)
				.setMaxResults(1)
				.uniqueResult();	
	}
	
	
	@Override
	public void flush() {
		sessionFactory.getCurrentSession().flush();
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public T getByCode(String code) {
		return (T)sessionFactory.getCurrentSession()
				.createQuery("FROM " + persistentClass.getName() + " WHERE code=:code")
				.setString("code", code)
				.setMaxResults(1)
				.uniqueResult();
        
    }
	
	public Query createQuery(final StringBuilder hql){
		return createQuery(hql.toString());
	}
	
	public Query createQuery(final String hql){
		return sessionFactory.getCurrentSession().createQuery(hql);
	}
	
	
	protected void prepareDateParam(final Query hqlQuery, final Map<String, Object> criteria, final String queryKey,final String paramKey){
		LocalDate val = (LocalDate)criteria.get(paramKey);
		if(val != null){
			if(queryKey.equals(Filter.CREATED_TO)){
				val = val.plusDays(1);
			}
			hqlQuery.setTimestamp(queryKey, val.toDate());
		}
	}
	
	protected void prepareStringParam(final Query hqlQuery, final Map<String, Object> criteria, final String queryKey,final String paramKey){
		final String val = (String)criteria.get(paramKey);
		if(StringUtils.isNotBlank(val)){
			hqlQuery.setString(queryKey, val);
		}
	}
	
	protected void prepareLongParam(final Query hqlQuery, final Map<String, Object> criteria, final String queryKey,final String paramKey){
		final Long val = (Long)criteria.get(paramKey);
		if(val != null && val != 0){
			hqlQuery.setLong(queryKey, val);
		}
	}
	
	protected void prepareBooleanParam(final Query hqlQuery, final Map<String, Object> criteria, final String queryKey,final String paramKey){
		final Boolean enabled = (Boolean)criteria.get(paramKey);
		if(enabled != null){
			hqlQuery.setBoolean(queryKey, enabled);
		}
	}
}
