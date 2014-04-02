package sk.peterjurkovic.cpr.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.WebpageDao;
import sk.peterjurkovic.cpr.entities.Webpage;

@Repository("webpageDao")
public class WebpageDaoImpl extends BaseDaoImpl<Webpage, Long> implements WebpageDao{

	public WebpageDaoImpl(){
		super(Webpage.class);
	}

	
	/**
	 * Na zaklade jedinecneho identifikatoru kategorie sa vratia vsetky verejne sekcie.
	 * 
	 * @param Long ID kategorie
	 * @return List<Webpage> zoznam verejnych sekcii v danej kategorii
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Webpage> getPublicSection(final Long categoryId) {
		StringBuffer hql = new StringBuffer("from Webpage webpage ");
		hql.append("where webpage.webpageCategory.id = :categoryId AND webpage.enabled=true");
		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		hqlQuery.setLong("categoryId", categoryId);
		return hqlQuery.list();
	}

	/**
	 * Vrati nasledujuce iD, ktore bude priradene pri dalsom inserte.
	 * 
	 * @return Long 
	 */
	@Override
	public Long getNextIdValue() {
		Long nextId = (Long) sessionFactory.getCurrentSession()
				.createQuery("SELECT MAX(webpage.id) FROM Webpage webpage")
				.setCacheable(false)
				.setMaxResults(1)
				.uniqueResult();
        if (nextId == null) {
            return 1L;
        }
        return nextId + 1;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Webpage> getAllOrderedWebpages() {
		StringBuilder hql = new StringBuilder("from ");
		hql.append(Webpage.class.getName());
		hql.append(" w");
		hql.append(" order by w.order ");
		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		return hqlQuery.list();
	}
}
