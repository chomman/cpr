package cz.nlfnorm.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.hibernate.Query;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

import cz.nlfnorm.constants.Filter;
import cz.nlfnorm.dao.StandardDao;
import cz.nlfnorm.entities.NotifiedBody;
import cz.nlfnorm.entities.Standard;
import cz.nlfnorm.entities.StandardCsn;
import cz.nlfnorm.entities.StandardGroup;
import cz.nlfnorm.entities.StandardNotifiedBody;
import cz.nlfnorm.enums.StandardOrder;
import cz.nlfnorm.utils.CodeUtils;

/**
 * Implementacia rozhrania {@link cz.nlfnorm.dao.StandardDao}
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
	public List<Standard> getStandardPage(final Map<String, Object> criteria, final int pageNumber, final int limit ){
		StringBuilder hql = new StringBuilder("select s from ");
		hql.append(Standard.class.getName());
		hql.append(" s ");
		hql.append(prepareHqlForQuery(criteria));
		hql.append(" group by s.id ");
		final Integer oid = (Integer)criteria.get(Filter.ORDER);
		if(oid != null && oid > 0){
			hql.append(StandardOrder.getSqlById((Integer)criteria.get(Filter.ORDER) ));
		}else{
			hql.append(StandardOrder.STANDARD_ID_INT.getSql());
		}
		
		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		prepareHqlQueryParams(hqlQuery, criteria);
		hqlQuery.setFirstResult( limit * ( pageNumber -1));
		hqlQuery.setMaxResults(limit);
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
		StringBuilder hql = new StringBuilder("SELECT count(*) FROM Standard s");
		hql.append(prepareHqlForQuery(criteria));
		Query hqlQuery = sessionFactory.getCurrentSession().createQuery(hql.toString());
		prepareHqlQueryParams(hqlQuery, criteria);
	    return (Long) hqlQuery.uniqueResult();

	}

	
	/**
	 * Vrati zoznam publikovanych skupin vyrobkov
	 * 
	 * @return List<StandardGroup> skupiny vyrobkov
	 */
	@Override
	public boolean isStandardIdUnique(final String standardId,final Long id) {
		StringBuilder hql = new StringBuilder("SELECT count(*) FROM Standard s WHERE s.code=:code");
		if(id != null && id != 0){
			hql.append(" AND s.id<>:id");
		}
		Long result = null;
		Query query = sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setString("code", CodeUtils.toSeoUrl(StringUtils.trim(standardId)));
		if(id != null && id != 0){
			query.setLong("id", id);
		}
		result = (Long)query.uniqueResult();
		return (result == 0);
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
		StringBuilder hql = new StringBuilder();
		appendJoinsClauses(hql, criteria);
		if(criteria.size() != 0){
			if(StringUtils.isNotBlank((String)criteria.get("query"))){
				where.add(" (unaccent(lower(s.standardId)) like CONCAT('%', unaccent(lower(:query)) , '%') " +
						" or unaccent(lower(s.czechName)) like CONCAT('%', unaccent(lower(:query)) , '%')" +
						" or unaccent(lower(s.englishName)) like CONCAT('%', unaccent(lower(:query)) , '%')) ");
			}
			if(isLongParamSet(criteria, Filter.STANDARD_CATEGORY)){
				where.add(" standardCategory.id=:standardCategory ");
			}
			if(isLongParamSet(criteria, Filter.REGULATION)){
				where.add(" regulation.id=:regulation ");
			}
			if(isLongParamSet(criteria, Filter.NOTIFIED_BODY)){
				where.add(" nb.id=:notifiedBodyId ");
			}
			if(StringUtils.isNotBlank((String)criteria.get(Filter.STANDARD_STAUTS))){
				where.add(" s.standardStatus=:standardStatus ");
			}
			if(isDateParamSet(criteria, Filter.CREATED_FROM)){
				where.add(" s.created>=:createdFrom");
			}
			if(isDateParamSet(criteria, Filter.CREATED_TO)){
				where.add(" s.created<:createdTo");
			}
			if(isLongParamSet(criteria, Filter.STANDARD_GROUP)){
				where.add(" standardGroup.id=:groupId ");
			}
			if(isLongParamSet(criteria, Filter.MANDATE)){
				where.add(" mandate.id = :mandateId ");
			}
			if(isLongParamSet(criteria, Filter.COMMISION_DECISION)){
				where.add(" standardGroup.commissionDecision.id=:commissionDecisionId ");
			}
			if(isLongParamSet(criteria, Filter.ASSESMENT_SYSTEM)){
				where.add(" assessmentSystem.id=:assessmentSystemId ");
			}
			if((Boolean)criteria.get(Filter.ENABLED) != null){
				where.add(" s.enabled=:enabled");
			}
		}
		return hql + (where.size() > 0 ? " WHERE " + StringUtils.join(where.toArray(), " AND ") : "");

	}
	
	private void appendJoinsClauses(StringBuilder hql, final Map<String, Object> criteria){
		final boolean isStandardGroupSet = isLongParamSet(criteria, Filter.STANDARD_GROUP);
		final boolean isMandateSet = isLongParamSet(criteria, Filter.MANDATE);
		final boolean isCommissionDecisionSet = isLongParamSet(criteria, Filter.COMMISION_DECISION);
		final boolean isAssessmentSystemSet = isLongParamSet(criteria, Filter.ASSESMENT_SYSTEM);
		final boolean isNotifiedBodySet = isLongParamSet(criteria, Filter.NOTIFIED_BODY);
		final boolean isStandardCategorySet = isLongParamSet(criteria, Filter.STANDARD_CATEGORY);
		final boolean isRegulationSet = isLongParamSet(criteria, Filter.REGULATION);
		
		if(isStandardCategorySet || isRegulationSet){
			hql.append(" join s.standardCategory as standardCategory ");
		}
		if(isRegulationSet){
			hql.append(" join standardCategory.regulations regulation ");
		}
		if(isStandardGroupSet || isMandateSet || isCommissionDecisionSet){
			hql.append(" left join s.standardGroups as standardGroup ");
		}
		if(isMandateSet){
			hql.append(" join standardGroup.mandates as mandate ");
		}
		if(isNotifiedBodySet){
			hql.append(" join s.notifiedBodies as snb ");
			hql.append(" join snb.notifiedBody as nb ");
		}
		if(isAssessmentSystemSet){
			hql.append(" join s.assessmentSystems as assessmentSystem ");
		}
	}
	
	private boolean isDateParamSet(final Map<String, Object> criteria, final String key){
		return (LocalDate)criteria.get(key) != null;
	}
	
	private boolean isLongParamSet(final Map<String, Object> criteria, final String key){
		final Long val = (Long)criteria.get(key);
		return val != null && val != 0;
	}
	
	private void prepareHqlQueryParams(final Query hqlQuery,final Map<String, Object> criteria){
		if(criteria.size() != 0){
			prepareBooleanParam(hqlQuery, criteria, "enabled", Filter.ENABLED);
			prepareStringParam(hqlQuery, criteria, "standardStatus", Filter.STANDARD_STAUTS);
			prepareStringParam(hqlQuery, criteria, "query", "query");
			prepareDateParam(hqlQuery, criteria, "createdTo", Filter.CREATED_TO);
			prepareDateParam(hqlQuery, criteria, "createdFrom", Filter.CREATED_FROM);
			prepareLongParam(hqlQuery, criteria, "groupId", Filter.STANDARD_GROUP);
			prepareLongParam(hqlQuery, criteria, "notifiedBodyId", Filter.NOTIFIED_BODY);
			prepareLongParam(hqlQuery, criteria, "assessmentSystemId", Filter.ASSESMENT_SYSTEM);
			prepareLongParam(hqlQuery, criteria, "commissionDecisionId", Filter.COMMISION_DECISION);
			prepareLongParam(hqlQuery, criteria, "mandateId", Filter.MANDATE);
			prepareLongParam(hqlQuery, criteria, "standardCategory", Filter.STANDARD_CATEGORY);
			prepareLongParam(hqlQuery, criteria, "regulation", Filter.REGULATION);
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
		return hqlQuery.list();
	}
	
	
	
	
	@Override
	public List<Standard> getStandardByStandardGroupForPublic(final StandardGroup standardGroup){
		Validate.notNull(standardGroup);
		Validate.notEmpty(standardGroup.getCode());
		return getStandardsByStandardGroupCode(standardGroup.getCode());
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Standard> getStandardsByStandardGroupCode(final String standardGroupCode){
		StringBuilder hql = new StringBuilder("select s from Standard s ");
		hql.append("join s.standardGroups as standardGroup ");
		hql.append("where standardGroup.code=:code and s.enabled=true ");
		hql.append("order by s.standardId");
		return sessionFactory.getCurrentSession()
				.createQuery(hql.toString())
				.setString("code", standardGroupCode)
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


	@SuppressWarnings("unchecked")
	@Override
	public List<Standard> getStandardsByCsn(final StandardCsn csn) {
		Validate.notNull(csn);
		StringBuilder hql = new StringBuilder("from Standard s ");
		hql.append(" WHERE :id in elements(s.standardCsns)");
		Query query =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setLong("id", csn.getId());
		return query.list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Standard> getStandardsByNotifiedBody(final NotifiedBody notifiedBody) {
		Validate.notNull(notifiedBody);
		StringBuilder hql = new StringBuilder("select standard from Standard standard ");
		hql.append(" LEFT JOIN standard.notifiedBodies as snb");
		hql.append(" where standard.enabled=true and snb.notifiedBody.id = :id ");
		Query query =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setLong("id", notifiedBody.getId());
		return query.list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Standard> getStandardsByReplaceStandard(Standard standard) {
		Validate.notNull(standard);
		StringBuilder hql = new StringBuilder("select s from Standard s ");
		hql.append(" LEFT JOIN s.replaceStandard as rs");
		hql.append(" where rs.id=:id ");
		Query query =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setLong("id", standard.getId());
		return query.list();
	}


	@Override
	public void unassignNotifiedBody(Long standardNotifiedBodyId) {
		Validate.notNull(standardNotifiedBodyId);
		StringBuilder hql = new StringBuilder("delete from ");
		hql.append(StandardNotifiedBody.class.getName() );
		hql.append(" as snb where snb.id = :id ");
		Query query =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setLong("id", standardNotifiedBodyId);
		query.setMaxResults(1);
		query.executeUpdate();
	}


	
	@SuppressWarnings("unchecked")
	@Override
	public List<Standard> getChangedStanards(final LocalDate dateFrom,final LocalDate dateTo,final Boolean enabledOnly) {
		StringBuilder hql = new StringBuilder()
		.append(" select s from Standard s ")
		.append(" left join s.notifiedBodies as snb ")
		.append(" left join s.standardCsns as csn ")
		.append(" left join csn.standardCsnChanges as csnChanges ")
		.append(" where (s.statusDate >= :dateFrom and s.statusDate <= :dateTo) ")
		.append(" 	or (s.released >= :dateFrom and s.released <= :dateTo) ")
		.append(" 	or (snb.assignmentDate >= :dateFrom and snb.assignmentDate <= :dateTo) ")
		.append(" 	or (csn.statusDate >= :dateFrom and csn.statusDate <= :dateTo) ")
		.append(" 	or (csn.released >= :dateFrom and csn.released <= :dateTo) ")
		.append(" 	or (csnChanges.statusDate >= :dateFrom and csnChanges.statusDate <= :dateTo) ")
		.append(" 	or (csnChanges.released >= :dateFrom and csnChanges.released <= :dateTo) ");
		if(enabledOnly){
			hql.append(" and s.enabled = true ");
		}
		hql.append(" group by s.id ")
			.append(StandardOrder.STANDARD_ID_INT.getSql());
		Query query =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setTimestamp("dateFrom", dateFrom.toDate());
		query.setTimestamp("dateTo", dateTo.toDate());
		return query.list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Standard> getStandardsForSitemap() {
		return createQuery("select s from Standard s where s.enabled = true ").list();
	}


	
}
