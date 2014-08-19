package cz.nlfnorm.dao.impl;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.hibernate.Query;
import org.hibernate.type.LongType;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import cz.nlfnorm.constants.CacheRegion;
import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.dao.WebpageDao;
import cz.nlfnorm.dto.AutocompleteDto;
import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.Webpage;
import cz.nlfnorm.enums.WebpageModule;
import cz.nlfnorm.enums.WebpageType;

@Repository("webpageDao")
public class WebpageDaoImpl extends BaseDaoImpl<Webpage, Long> implements WebpageDao{

	@Value("#{config.moveToArchiveThreshold}")
	private int moveToArchiveThresholdInWeeks;
	
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
	public List<AutocompleteDto> autocomplete(final String term, final boolean enabledOnly, final Long excludeId) {
		StringBuilder hql = new StringBuilder();
		if(enabledOnly){
			hql.append("select w.id as id, l.title as name ")
				.append("from Webpage w ")
				.append("	join w.localized l ")
				.append("where w.enabled=true and ")
				.append("  	unaccent(lower(l.title)) like  CONCAT('', unaccent(lower(:query)) , '%'))  AND key(l) = 'cs' ");
			if(excludeId != null){
				hql.append(" and w.id != :excludeId ");
			}
			hql.append("group by w.id ")
				.append("order by l.title desc ");
		}else{
			hql.append("select w.id as id, l.name as name from Webpage w ")
				.append("  join w.localized l where ");
			if(excludeId != null){
				hql.append("w.id != :excludeId and ");
			}
			hql.append("  unaccent(lower(l.name)) like  CONCAT('', unaccent(lower(:query)) , '%'))  AND key(l) = 'cs' ")
				.append("group by w.id order by l.name desc");
		}
		
		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		hqlQuery.setString("query", term);
		if(excludeId != null){
			hqlQuery.setLong("excludeId", excludeId);
		}
		hqlQuery.setMaxResults(8);
		hqlQuery.setCacheable(false);
		return hqlQuery.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public PageDto search(final int pageNumber, final String term,final Long parentNodeId) { 	
		Query hqlQuery =  sessionFactory.getCurrentSession().createSQLQuery(buildSearchQuery(true))
		.addScalar("count", LongType.INSTANCE);
		hqlQuery.setMaxResults(1);
		hqlQuery.setCacheable(false);
		hqlQuery.setString("query", term);
		hqlQuery.setString("fullTextQuery", term.replace(" ", "&"));
		PageDto items = new PageDto();
		items.setCount((Long)hqlQuery.uniqueResult());
		if(items.getCount() > 0){
			hqlQuery =  sessionFactory.getCurrentSession().createSQLQuery(buildSearchQuery(false))
						.addEntity(Webpage.class);				
			hqlQuery.setCacheable(false);
			hqlQuery.setString("query", term);
			hqlQuery.setString("fullTextQuery", term.toLowerCase().replace(" ", "&"));
			hqlQuery.setFirstResult(Constants.ADMIN_PAGINATION_PAGE_SIZE * ( pageNumber -1));
			hqlQuery.setMaxResults(Constants.ADMIN_PAGINATION_PAGE_SIZE);
			items.setItems(hqlQuery.list());
		}
		return items;
	}
	
	private String buildSearchQuery(final boolean isCountQuery){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT "+ (isCountQuery ? " count(w) as count " : "w.* "))	
		   .append("FROM webpage w ")
		   .append("JOIN webpage_content wc ON w.id=wc.webpage_id ")
		   .append("WHERE w.enabled=true AND (")
		   .append(" 	unaccent(lower(wc.title)) like CONCAT('%', unaccent(lower(:query)) , '%') OR ")
		   .append(" 	wc.webpage_tsvector @@ to_tsquery( cast( wc.localized_key as regconfig), :fullTextQuery) ) ");
		if(!isCountQuery){
		   sql.append("GROUP BY w.id, wc.webpage_tsvector, wc.localized_key ")
		   	  .append("ORDER BY ts_rank(wc.webpage_tsvector, to_tsquery(cast( wc.localized_key as regconfig), :fullTextQuery )) DESC");
		}
		return sql.toString();
	}


	@Override
	public Webpage getHomePage() {
		StringBuilder hql = new StringBuilder("from ");
		hql.append(Webpage.class.getName());
		hql.append(" w where w.parent.id = null and w.enabled = true order by w.order ");		
		Query q = sessionFactory.getCurrentSession().createQuery(hql.toString());
		q.setMaxResults(1);
		q.setCacheable(true);
		q.setCacheRegion(CacheRegion.WEBPAGE_CACHE);
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
		final LocalDateTime now = new LocalDateTime();
		StringBuilder hql = new StringBuilder("from ");
		hql.append(Webpage.class.getName());
		hql.append(" w");
		hql.append(" where w.webpageType = :webpageType and w.enabled = true and (w.publishedSince is NULL or w.publishedSince < :now ) ");
		hql.append(" order by COALESCE(w.publishedSince, w.created) DESC ");
		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		hqlQuery.setParameter("webpageType", WebpageType.NEWS);
		hqlQuery.setTimestamp("now", now.toDate());
		hqlQuery.setMaxResults(limit);
		hqlQuery.setCacheRegion(CacheRegion.WEBPAGE_CACHE);
		hqlQuery.setCacheable(true);
		return hqlQuery.list();
	}


	@Override
	public void updatetsVector(final Webpage webpage) {
		Validate.notNull(webpage);
		Validate.notNull(webpage.getId());
		
		StringBuilder sql = new StringBuilder("UPDATE webpage_content ");
		sql.append("SET webpage_tsvector = ")
			.append(" to_tsvector(cast(localized_key as regconfig), lower(concat(title, ' ', description, ' ', content, ' ', unaccent(title), ' ', unaccent(content), ' ', unaccent(description), ' ', :tags ))) ")
			.append("WHERE webpage_id = :webpageId");
		Query hqlQuery = sessionFactory
					.getCurrentSession()
					.createSQLQuery(sql.toString());
		hqlQuery.setLong("webpageId", webpage.getId());
		hqlQuery.setString("tags", webpage.getJointedTags());
		hqlQuery.executeUpdate();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Webpage> getFooterWebpages() {
		StringBuilder sql = new StringBuilder();
			sql.append("select w.* from webpage w ")
				.append("where w.enabled=true and w.parent_id = ")
				.append("(select id from webpage where code = :code limit 1 )");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString())
					.addEntity(Webpage.class)
					.setString("code", Webpage.FOOTER_CODE)
					.setCacheable(true)
					.setCacheRegion(CacheRegion.WEBPAGE_CACHE);
		return query.list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Webpage> getOldNonArchivedNewsInNode(final Webpage node) {
		Validate.notNull(node);
		LocalDateTime threshold = new LocalDateTime().minusWeeks(moveToArchiveThresholdInWeeks);
		StringBuilder hql = new StringBuilder();
		hql.append("from Webpage w WHERE w.webpageType = :webpageType AND COALESCE(w.publishedSince, w.created) < :threshold ");
		hql.append(" AND w.parent.id = :parentId");
		Query query = sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setParameter("webpageType", WebpageType.NEWS);
		query.setTimestamp("threshold", threshold.toDate());
		query.setLong("parentId", node.getId());
		return query.list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Webpage> getSimilarWebpages(final Webpage webpage, final int limit, final WebpageType webpageType) {
		StringBuilder hql = new StringBuilder()
		.append("select w from Webpage w ")
		.append("join w.tags tag ")
		.append("where (w.webpageType = :newsWebpageType ");
		if(webpageType != null){
			hql.append(" OR w.webpageType=:webpageType ");
		}
		hql.append(") AND w.enabled=true AND w.id != :id AND tag.name IN (:mTags) ")
		.append("group by w.id order by w.created DESC ");
		
		final Query query = createQuery(hql)
				.setParameterList("mTags", webpage.getStringTags())
				.setLong("id", webpage.getId())
				.setString("newsWebpageType", WebpageType.NEWS.toString())
				.setMaxResults(limit);
		if(webpageType != null){
				query.setString("webpageType", webpageType.toString());
		}
		return query.list();
	}


}
