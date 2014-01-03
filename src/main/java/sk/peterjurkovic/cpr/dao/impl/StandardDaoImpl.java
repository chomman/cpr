package sk.peterjurkovic.cpr.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.hibernate.Query;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.constants.CacheRegion;
import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.dao.StandardDao;
import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.entities.StandardCsn;
import sk.peterjurkovic.cpr.entities.StandardGroup;
import sk.peterjurkovic.cpr.enums.StandardOrder;

/**
 * Implementacia rozhrania {@link sk.peterjurkovic.cpr.dao.StandardDao}
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 */
@Repository("standardDao")
public class StandardDaoImpl extends BaseDaoImpl<Standard, Long> implements StandardDao {
	
	public StandardDaoImpl(){
		super(Standard.class);
	}

	
	/**
	 * Vrati pocet evidovanych noriem v danej skupine vyrobkov
	 * 
	 * @param StandardForm dana skupina vyrobkov
	 * @return Long pocet noriem, nachadzajucich sa v danej skupine
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Standard> getStandardPage(final int pageNumber,final Map<String, Object> criteria ){
		StringBuffer hql = new StringBuffer("select s from Standard s");
		hql.append(prepareHqlForQuery(criteria));
		if((Integer)criteria.get("orderBy") != null){
			hql.append(StandardOrder.getSqlById((Integer)criteria.get("orderBy") ));
		}else{
			hql.append(StandardOrder.getSqlById(6));
		}
		
		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		prepareHqlQueryParams(hqlQuery, criteria);
		hqlQuery.setFirstResult(Constants.ADMIN_PAGINATION_PAGE_SIZE * ( pageNumber -1));
		hqlQuery.setMaxResults(Constants.ADMIN_PAGINATION_PAGE_SIZE);
		hqlQuery.setCacheable(false);
		return (List<Standard>)hqlQuery.list();
	}
	
	
	
	/**
	 * Skontroluje ci je nazov skupiny jedinecny v ramci systemu
	 * 
	 * @param String kod skupiny code (seo url)
	 * @param Long ID skupiny
	 * @return TRUE, ak je jedinecna, inak FALSE
	 */
	public Long getCountOfSdandards(final Map<String, Object> criteria) {
		StringBuffer hql = new StringBuffer("SELECT count(*) FROM Standard s");
		hql.append(prepareHqlForQuery(criteria));
		Query hqlQuery = sessionFactory.getCurrentSession().createQuery(hql.toString());
		prepareHqlQueryParams(hqlQuery, criteria);
		hqlQuery.setCacheable(true);
		hqlQuery.setCacheRegion(CacheRegion.CPR_CACHE);
	    return (Long) hqlQuery.uniqueResult();

	}

	
	/**
	 * Vrati zoznam publikovanych skupin vyrobkov
	 * 
	 * @return List<StandardGroup> skupiny vyrobkov
	 */
	@Override
	public boolean isStandardIdUnique(final String standardId,final Long id) {
		StringBuilder hql = new StringBuilder("SELECT count(*) FROM Standard s WHERE s.standardId=:standardId");
		if(id != null && id != 0){
			hql.append(" AND s.id<>:id");
		}
		Long result = null;
		Query query = sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setString("standardId", standardId);
		if(id != null && id != 0){
			query.setLong("id", id);
		}
		result = (Long)query.uniqueResult();
		return (result == 0);
	}


	@Override
	public void clearStandardTags(final Standard standard) {
		StringBuilder hql = new StringBuilder("delete from Tag tag where tag.standard=:standard");
		sessionFactory.getCurrentSession()
		.createQuery(hql.toString())
		.setEntity("standard", standard)
		.executeUpdate();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Standard> autocomplateSearch(final String query, final Boolean enabled) {
		StringBuilder hql = new StringBuilder("select s.id, s.standardId, s.czechName from Standard s");
		hql.append(" where unaccent(lower(s.standardId)) like unaccent(lower(:query)) ");
		hql.append(" or unaccent(lower(s.czechName)) like unaccent(lower(:query)) ");
		if(enabled != null){
			hql.append(" AND s.enabled=:enabled");
		}
		Query hqlQuery = sessionFactory.getCurrentSession().createQuery(hql.toString());
		if(enabled != null){
			hqlQuery.setBoolean("enabled", enabled);
		}
		return hqlQuery.setString("query",  query + "%")
				.setMaxResults(8)
				.list();
	}
	
	
	private String prepareHqlForQuery(final Map<String, Object> criteria){
		List<String> where = new ArrayList<String>();
		StringBuilder hql = new StringBuilder(" left join s.standardGroups sg ");
		
		Long mandateId = (Long)criteria.get("mandateId");
		if(mandateId != null && mandateId != 0){
			hql.append(" left join sg.mandates m ");
		}
		
		if(StringUtils.isNotBlank((String)criteria.get("queryNb"))){
			hql.append(" left join s.notifiedBodies nb ");
		}
		Long assessmentSystemId = (Long)criteria.get("mandateId");
		if(assessmentSystemId != null && assessmentSystemId != 0){
			hql.append(" left join s.assessmentSystem as ");
		}
		if(criteria.size() != 0){
			if(StringUtils.isNotBlank((String)criteria.get("query"))){
				where.add(" (unaccent(lower(s.standardId)) like CONCAT('%', unaccent(lower(:query)) , '%') " +
						" or unaccent(lower(s.czechName)) like CONCAT('%', unaccent(lower(:query)) , '%')" +
						" or unaccent(lower(s.englishName)) like CONCAT('%', unaccent(lower(:query)) , '%')) ");
			}
			
			if(StringUtils.isNotBlank((String)criteria.get("queryNb"))){
				where.add(" (unaccent(lower(nb.name)) like CONCAT('%', unaccent(:queryNb) , '%') " +
						" or unaccent(lower(nb.aoCode)) like CONCAT('%', unaccent(:queryNb) , '%') " +
						" or unaccent(lower(nb.noCode)) like CONCAT('%', unaccent(:queryNb) , '%')) ");
	
			}
			
			if(StringUtils.isNotBlank((String)criteria.get("standardStatus"))){
				where.add(" s.standardStatus=:standardStatus ");
	
			}
			
			if((DateTime)criteria.get("createdFrom") != null){
				where.add(" s.created>=:createdFrom");
			}
			if((DateTime)criteria.get("createdTo") != null){
				where.add(" s.created<:createdTo");
			}
			Long groupId = (Long)criteria.get("groupId");
			if(groupId != null && groupId != 0){
				where.add(" sg.id=:groupId ");
			}
	
			if(mandateId != null && mandateId != 0){
				where.add(" m.id=:mandateId ");
			}
			Long commissionDecisionId = (Long)criteria.get("commissionDecisionId");
			if(commissionDecisionId != null && commissionDecisionId != 0){
				where.add(" sg.commissionDecision.id=:commissionDecisionId ");
			}
			
			if(assessmentSystemId != null && assessmentSystemId != 0){
				hql.append(" as.id=:assessmentSystemId ");
			}
			
			Boolean enabled = (Boolean)criteria.get("enabled");
			if(enabled != null){
				where.add(" s.enabled=:enabled");
			}
		}
		return hql + (where.size() > 0 ? " WHERE " + StringUtils.join(where.toArray(), " AND ") : "");

	}
	
	private void prepareHqlQueryParams(final Query hqlQuery,final Map<String, Object> criteria){
		if(criteria.size() != 0){
			if(StringUtils.isNotBlank((String)criteria.get("query"))){
				hqlQuery.setString("query", (String)criteria.get("query"));
			}
			DateTime createdFrom = (DateTime)criteria.get("createdFrom");
			if(createdFrom != null){
				hqlQuery.setTimestamp("createdFrom", createdFrom.toDate());
			}
			DateTime createdTo = (DateTime)criteria.get("createdTo");
			if(createdTo != null){
				hqlQuery.setTimestamp("createdTo", createdTo.plusDays(1).toDate());
			}
			Long groupId = (Long)criteria.get("groupId");
			if(groupId != null && groupId != 0){
				hqlQuery.setLong("groupId", groupId);
			}
			Long commissionDecisionId = (Long)criteria.get("commissionDecisionId");
			if(commissionDecisionId != null && commissionDecisionId != 0){
				hqlQuery.setLong("commissionDecisionId", commissionDecisionId);
			}
			Long mandateId = (Long)criteria.get("mandateId");
			if(mandateId != null && mandateId != 0){
				hqlQuery.setLong("mandateId", mandateId);
			}
			Long assessmentSystemId = (Long)criteria.get("assessmentSystemId");
			if(assessmentSystemId != null && assessmentSystemId != 0){
				hqlQuery.setLong("assessmentSystemId", assessmentSystemId);
			}
			if(StringUtils.isNotBlank((String)criteria.get("standardStatus"))){
				hqlQuery.setString("standardStatus", (String)criteria.get("standardStatus"));
			}
			if(StringUtils.isNotBlank((String)criteria.get("queryNb"))){
				hqlQuery.setString("queryNb", (String)criteria.get("queryNb"));
			}
			Boolean enabled = (Boolean)criteria.get("enabled");
			if(enabled != null){
				hqlQuery.setBoolean("enabled", enabled);
			}
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Standard> getLastEditedOrNewestStandards(final int count, final Boolean enabled) {
		StringBuilder hql = new StringBuilder("from Standard s ");
		if(enabled != null){
			hql.append("where s.enabled=:enabled ");
		}
		hql.append(" order by s.changed desc");
		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		if(enabled != null){
			hqlQuery.setBoolean("enabled", enabled);
		}
		hqlQuery.setMaxResults(count);
		hqlQuery.setCacheable(true);
		hqlQuery.setCacheRegion(CacheRegion.CPR_CACHE);
		return hqlQuery.list();
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Standard> getStandardByStandardGroupForPublic(final StandardGroup standardGroup){
		return sessionFactory.getCurrentSession()
				.createQuery("from Standard s where s.enabled=true and s.standardGroup.id=:id order by s.standardId")
				.setLong("id", standardGroup.getId())
				.setCacheable(true)
				.setCacheRegion(CacheRegion.CPR_CACHE)
				.list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Standard> getStandardsByTagName(final String tagName) {
		StringBuilder hql = new StringBuilder("select standard from Standard standard ");
		hql.append(" LEFT JOIN standard.tags as tag");
		hql.append(" where standard.enabled=true and  tag.name like CONCAT('%', :tagName , '%')");
		 hql.append(" and size(standard.requirements) > 0");
		Query query =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setParameter("tagName", tagName);
		query.setMaxResults(50);
		return query.list();
	}


	@Override
	public Standard getStandardByCsn(final StandardCsn csn) {
		Validate.notNull(csn);
		StringBuilder hql = new StringBuilder("from Standard s ");
		hql.append(" WHERE :id in elements(s.standardCsns)");
		Query query =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setLong("id", csn.getId());
		query.setMaxResults(1);
		return (Standard)query.uniqueResult();
	}
	
}
