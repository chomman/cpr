package cz.nlfnorm.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

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
}
