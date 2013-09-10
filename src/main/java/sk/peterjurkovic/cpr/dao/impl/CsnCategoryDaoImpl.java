package sk.peterjurkovic.cpr.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.CsnCategoryDao;
import sk.peterjurkovic.cpr.entities.CsnCategory;

@Repository("csnCategoryDao")
public class CsnCategoryDaoImpl  extends BaseDaoImpl<CsnCategory, Long> implements CsnCategoryDao{
	
	public CsnCategoryDaoImpl(){
		super(CsnCategory.class);
	}

	@Override
	public Long getMaxId() {
		Query hqlQuery = sessionFactory.getCurrentSession().createQuery("SELECT max(id) FROM CsnCategory");
		hqlQuery.setCacheable(false);
	    Long max =  (Long) hqlQuery.uniqueResult();
	    if(max == null){
	    	return 0l;
	    }
	    return max;
	}

	
	
	@Override
	public Long getCountOfCsnInCategory(Long id) {
		Query hqlQuery = sessionFactory.getCurrentSession().createQuery("select count(*) from Csn c where c.csnCategory.id=:id");
		hqlQuery.setLong("id", id);
		return (Long) hqlQuery.uniqueResult();
	}

	
	@Override
	public CsnCategory findBySearchCode(String searchCode) {
		Query hqlQuery = sessionFactory.getCurrentSession().createQuery("from CsnCategory c where c.searchCode=:code");
		hqlQuery.setString("code", searchCode);
		hqlQuery.setMaxResults(1);
		return (CsnCategory) hqlQuery.uniqueResult();
	}

	
	

}
