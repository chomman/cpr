package sk.peterjurkovic.cpr.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.constants.CacheRegion;
import sk.peterjurkovic.cpr.dao.CsnCategoryDao;
import sk.peterjurkovic.cpr.entities.CsnCategory;

@Repository("csnCategoryDao")
public class CsnCategoryDaoImpl  extends BaseDaoImpl<CsnCategory, Long> implements CsnCategoryDao{
	
	public CsnCategoryDaoImpl(){
		super(CsnCategory.class);
	}

	@Override
	public Long getMaxId() {
		final  Query hqlQuery = sessionFactory.getCurrentSession().createQuery("SELECT max(id) FROM CsnCategory");
		hqlQuery.setCacheable(false);
	    Long max =  (Long) hqlQuery.uniqueResult();
	    if(max == null){
	    	return 0l;
	    }
	    return max;
	}

	
	@Override
	public Long getCountOfCsnInCategory(Long id) {
		final  Query hqlQuery = sessionFactory.getCurrentSession().createQuery("select count(*) from Csn c where c.csnCategory.id=:id");
		hqlQuery.setLong("id", id);
		return (Long) hqlQuery.uniqueResult();
	}

	
	@Override
	public CsnCategory findBySearchCode(String searchCode) {
		final Query hqlQuery = sessionFactory.getCurrentSession().createQuery("from CsnCategory c where c.searchCode=:code");
		hqlQuery.setString("code", searchCode);
		hqlQuery.setMaxResults(1);
		hqlQuery.setCacheable(false);
		return (CsnCategory) hqlQuery.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CsnCategory> getSubRootCategories() {
		 final StringBuffer hql = new StringBuffer("FROM CsnCategory c");
         hql.append(" WHERE (c.parent IS NULL)");
         hql.append(" ORDER BY c.searchCode ASC");
         
         final Query query = sessionFactory.getCurrentSession().createQuery(hql.toString());
         query.setCacheable(true);
         query.setCacheRegion(CacheRegion.CSN_CACHE);
         return (List<CsnCategory>) query.list();
	}
	
		
	

}
