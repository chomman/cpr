package sk.peterjurkovic.cpr.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.constants.CacheRegion;
import sk.peterjurkovic.cpr.dao.WebpageDao;
import sk.peterjurkovic.cpr.entities.Webpage;

@Repository("webpageDao")
public class WebpageDaoImpl extends BaseDaoImpl<Webpage, Long> implements WebpageDao{

	public WebpageDaoImpl(){
		super(Webpage.class);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Webpage> getPublicSection(final Long categoryId) {
		StringBuffer hql = new StringBuffer("from Webpage webpage ");
		hql.append("where webpage.webpageCategory.id = :categoryId AND webpage.enabled=true");
		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		hqlQuery.setLong("categoryId", categoryId);
		hqlQuery.setCacheable(true);
		hqlQuery.setCacheRegion(CacheRegion.WEBPAGE_CACHE);
		return hqlQuery.list();
	}
}
