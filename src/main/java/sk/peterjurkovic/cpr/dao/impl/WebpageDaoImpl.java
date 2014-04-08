package sk.peterjurkovic.cpr.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.WebpageDao;
import sk.peterjurkovic.cpr.dto.AutocompleteDto;
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
		StringBuilder hql = new StringBuilder("from Webpage webpage ");
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


	@Override
	public int getMaxOrderInNode(Long nodeId) {
		StringBuilder hql = new StringBuilder("select max(w.order) from ");
		hql.append(Webpage.class.getName());
		hql.append(" w ");
		if(nodeId == null){
			hql.append(" where w.parent.id = null ");
		}else{
			hql.append(" where w.parent.id = :id");
		}
		
		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		if(nodeId != null){
			hqlQuery.setLong("id", nodeId );
		}
		
		Integer maxOrder = (Integer) hqlQuery.setMaxResults(1).uniqueResult();
		
		if(maxOrder != null){
			return maxOrder + 1;
		}
		return 0;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Webpage> getTopLevelWepages(final boolean enabledOnly) {
		StringBuilder hql = new StringBuilder("from ");
		hql.append(Webpage.class.getName());
		hql.append(" w");
		hql.append(" where w.parent = null ");
		if(enabledOnly){
			hql.append(" and w.enabled = true ");
		}
		hql.append(" order by w.order ");
		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		return hqlQuery.list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<AutocompleteDto> autocomplete(final String term) {
		StringBuilder hql = new StringBuilder("select w.id as id, l.name as name from ");
		hql.append(Webpage.class.getName())
		   .append(" w join w.localized l")
		   .append(" where unaccent(lower(l.name)) like  CONCAT('', unaccent(lower(:query)) , '%'))  AND key(l) = 'cs' ")
		   .append("group by w.id ");
		   
		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		hqlQuery.setString("query", term);
		hqlQuery.setMaxResults(8);
		hqlQuery.setCacheable(false);
		return hqlQuery.list();
	}


	@Override
	public Webpage getHomePage() {
		StringBuilder hql = new StringBuilder("from ");
		hql.append(Webpage.class.getName());
		hql.append(" w where w.parent.id = null and w.enabled = true order by w.order ");		
		Query q = sessionFactory.getCurrentSession().createQuery(hql.toString());
		q.setMaxResults(1);
		q.setCacheable(true);
		return (Webpage)q.uniqueResult();
	}
}
