package cz.nlfnorm.dao.impl;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.hibernate.Query;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.WebpageDao;
import cz.nlfnorm.dto.AutocompleteDto;
import cz.nlfnorm.entities.Webpage;
import cz.nlfnorm.enums.WebpageModule;
import cz.nlfnorm.enums.WebpageType;

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


	@Override
	public Webpage getWebpageByModule(final WebpageModule webpageModule) {
		Validate.notNull(webpageModule);
		StringBuilder hql = new StringBuilder("from ");
		hql.append(Webpage.class.getName());
		hql.append(" w where w.webpageModule = :module");		
		Query q = sessionFactory.getCurrentSession().createQuery(hql.toString());
		q.setMaxResults(1);
		q.setParameter("module", webpageModule);
		q.setCacheable(true);
		return (Webpage)q.uniqueResult();
	}


	@Override
	public Webpage getTopParentWebpage(final Webpage childrenNode) {
		Validate.notNull(childrenNode);
		StringBuilder sql = new StringBuilder();
		
		sql.append("with recursive tmp_webpage(id, parent) as ( ")
		   .append("	values(-1::BIGINT, :childrenNodeId::BIGINT) ")
		   .append(" union all ")
		   .append(" 	select w.id, w.parent_id ")
		   .append("    from tmp_webpage as tw, webpage as w ")
		   .append("    where w.id = parent ")
		   .append(" ) ")
		   .append(" select * from webpage w where id = ( select t.id from tmp_webpage as t where t.parent is NULL ) "); 	
			
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString())
				.addEntity(Webpage.class)
				.setMaxResults(1)
				.setLong("childrenNodeId", childrenNode.getId());
				
		return (Webpage)query.uniqueResult();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Webpage> getChildrensOfNode(final Long id, final boolean publishedOnly) {
		StringBuilder hql = new StringBuilder("from ");
		hql.append(Webpage.class.getName());
		hql.append(" w");
		hql.append(" where w.parent.id = :id ");
		if(publishedOnly){
			hql.append(" and w.enabled = true ");
		}
		hql.append(" order by w.order ");
		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		hqlQuery.setLong("id", id);
		return hqlQuery.list();
	}


	public void incrementOrder(final Webpage parentWebpage, final int threshold){
		updateOrder(parentWebpage, threshold, Boolean.TRUE);
	}
	public void decrementOrder(final Webpage parentWebpage, final int threshold){
		updateOrder(parentWebpage, threshold, Boolean.FALSE);
	}
	
	private void updateOrder(Webpage parentWebpage, int threshold, boolean isIncremetation) {
		StringBuilder hql = new StringBuilder("update Webpage w");
		if(isIncremetation){	
			hql.append(" set w.order = w.order + 1 ");
		}else{
			hql.append(" set w.order = w.order - 1 ");
		}
		hql.append(" where w.parent.id=:id and w.order > :threshold ");
		Query query = sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setLong("id", parentWebpage.getId());
		query.setInteger("threshold", threshold);
		query.executeUpdate();
	}


	
	@SuppressWarnings("unchecked")
	@Override
	public List<Webpage> getLatestPublishedNews(final int limit) {
		LocalDateTime now = new LocalDateTime();
		StringBuilder hql = new StringBuilder("from ");
		hql.append(Webpage.class.getName());
		hql.append(" w");
		hql.append(" where w.webpageType = :webpageType and w.enabled = true and (w.publishedSince is NULL or w.publishedSince < :now ) ");
		hql.append(" order by w.publishedSince, w.id DESC ");
		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		hqlQuery.setParameter("webpageType", WebpageType.NEWS);
		hqlQuery.setTimestamp("now", now.toDate());
		hqlQuery.setMaxResults(limit);
		return hqlQuery.list();
	}
}
