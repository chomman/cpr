package cz.nlfnorm.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cz.nlfnorm.constants.CacheRegion;
import cz.nlfnorm.dao.CsnCategoryDao;
import cz.nlfnorm.entities.CsnCategory;

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
		 final StringBuffer hql = new StringBuffer("select c from CsnCategory c");
         hql.append(" WHERE (c.parent IS NULL)");
         hql.append(" ORDER BY c.searchCode ASC");
         final Query query = sessionFactory.getCurrentSession().createQuery(hql.toString());
         query.setCacheable(true);
         query.setCacheRegion(CacheRegion.CSN_CACHE);
         return (List<CsnCategory>) query.list();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<CsnCategory> autocomplete(String term) {
		StringBuilder hql = new StringBuilder("select c.name, c.searchCode from CsnCategory c");
		hql.append(" where c.searchCode like CONCAT('', :term , '%') order by c.searchCode asc");
		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		hqlQuery.setParameter("term", term);
		hqlQuery.setMaxResults(8);
		return hqlQuery.list();
	}
	
		
	

}
