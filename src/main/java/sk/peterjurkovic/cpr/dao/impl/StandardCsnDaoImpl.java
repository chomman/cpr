package sk.peterjurkovic.cpr.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.dao.StandardCsnDao;
import sk.peterjurkovic.cpr.dto.PageDto;
import sk.peterjurkovic.cpr.entities.StandardCsn;

/**
 * Implementacia rozhrania sk.peterjurkovic.cpr.dao.impl.StandardCsnDao
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Repository("standardCsnDao")
public class StandardCsnDaoImpl extends BaseDaoImpl<StandardCsn, Long> implements StandardCsnDao{
	
	
	public StandardCsnDaoImpl(){
		super(StandardCsn.class);
	}

	@Override
	public StandardCsn getByCatalogNo(final String catalogNumber) {
		return (StandardCsn)sessionFactory.getCurrentSession()
				.createQuery("FROM StandardCsn csn WHERE csn.csnOnlineId=:csnOnlineId ")
				.setString("csnOnlineId", catalogNumber.trim())
				.setMaxResults(1)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StandardCsn> autocomplete(final String term) {
		StringBuilder hql = new StringBuilder("select csn.id, csn.csnName from StandardCsn csn");
		hql.append(" where unaccent(lower(csn.csnName)) like unaccent(:query) ");
		Query hqlQuery = sessionFactory.getCurrentSession().createQuery(hql.toString());
		return hqlQuery.setString("query",  term.toLowerCase() + "%")
				.setMaxResults(8)
				.list();
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public PageDto getPage(final int pageNumber, final Map<String, Object> criteria){
		StringBuilder hql = new StringBuilder("from StandardCsn csn");
		hql.append(prepareHqlForQuery(criteria));
		Session session = sessionFactory.getCurrentSession();
		Query hqlCountQuery = session.createQuery("select count(*) " + hql.toString());
		prepareHqlQueryParams(hqlCountQuery, criteria);
		PageDto items = new PageDto();
		hqlCountQuery.setMaxResults(1);
		hqlCountQuery.setCacheable(false);
		items.setCount((Long)hqlCountQuery.uniqueResult());
		if(items.getCount() > 0){
			hql.append(" group by csn.id ");
			Query query = session.createQuery(hql.toString());
			prepareHqlQueryParams(query, criteria);
			query.setCacheable(false);
			query.setFirstResult(Constants.ADMIN_PAGINATION_PAGE_SIZE * ( pageNumber -1));
			query.setMaxResults(Constants.ADMIN_PAGINATION_PAGE_SIZE);
			items.setItems(query.list());
		}
	
		return items;
	}
	
	private void prepareHqlQueryParams(Query hqlQuery,final Map<String, Object> criteria){
		if(criteria.size() != 0){
			if(StringUtils.isNotBlank((String)criteria.get("query"))){
				hqlQuery.setString("query", (String)criteria.get("query"));
			}
			if(StringUtils.isNotBlank((String)criteria.get("csnCategory"))){
				hqlQuery.setString("csnCategory", (String)criteria.get("csnCategory"));
			}
			if(StringUtils.isNotBlank((String)criteria.get("catalogId"))){
				hqlQuery.setString("csnOnlineId", (String)criteria.get("csnOnlineId"));
			}
		}
	}

	
	private String prepareHqlForQuery(final Map<String, Object> criteria){
		List<String> where = new ArrayList<String>();
		if(criteria.size() != 0){
			
			if(StringUtils.isNotBlank((String)criteria.get("query"))){
				where.add(" (unaccent(lower(csn.csnName)) like CONCAT('%', unaccent(lower(:query)) , '%')  OR " + 
						  " csn.csnOnlineId = :query OR csn.classificationSymbol=:query ) " );
			}
			
			if(StringUtils.isNotBlank((String)criteria.get("csnOnlineId"))){
				where.add(" csn.csnOnlineId=:csnOnlineId ");
			}
			
			if(StringUtils.isNotBlank((String)criteria.get("csnCategory"))){
				where.add(" csn.classificationSymbol like CONCAT('', :csnCategory , '%') ");
			}
		}
		return (where.size() > 0 ? " WHERE " + StringUtils.join(where.toArray(), " AND ") : "");
	}

	@Override
	public boolean isStandardCsnUnique(final StandardCsn csn) {
		StringBuilder hql = new StringBuilder("SELECT count(*) FROM StandardCsn csn WHERE ( csn.csnOnlineId=:csnOnlineId");
		hql.append(" OR csn.csnName = :csnName ) ");
		if(csn.getId() != null && csn.getId() != 0){
			hql.append(" AND csn.id<>:id");
		}
		Long result = null;
		Query query = sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setString("csnOnlineId", csn.getCsnOnlineId().trim());
		query.setString("csnName", csn.getCsnName().trim());
		query.setCacheable(false);
		if(csn.getId() != null && csn.getId() != 0){
			query.setLong("id", csn.getId());
		}
		
		result = (Long)query.uniqueResult();
		return (result == 0);
	}

	
	
	@Override
	public void deleteStandardCsn(StandardCsn csn) {
		Query query = sessionFactory.getCurrentSession().createQuery("update StandardCsn csn set csn.replaceStandardCsn=NULL WHERE csn.replaceStandardCsn.id=:id " );
		query.setLong("id", csn.getId());
		query.executeUpdate();
		remove(csn);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StandardCsn> getChangedStandardCsn(final LocalDate dateFrom,final LocalDate dateTo,final boolean enabledOnly) {
		StringBuilder hql = new StringBuilder("select csn from StandardCsn csn ");
		hql.append(" left join csn.standardCsnChanges as csnChange ");
		hql.append(" where (csn.statusDate >= :dateFrom and csn.statusDate <= :dateTo) ");
		hql.append(" 	or (csn.released >= :dateFrom and csn.released <= :dateTo) ");
		hql.append(" 	or (csnChange.statusDate >= :dateFrom and csnChange.statusDate <= :dateTo) ");
		hql.append(" 	or (csnChange.released >= :dateFrom and csnChange.released <= :dateTo) ");
		if(enabledOnly){
			hql.append(" and csn.enabled = true");
		}
		hql.append(" group by csn.id ");
		Query query =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setTimestamp("dateFrom", dateFrom.toDate());
		query.setTimestamp("dateTo", dateTo.toDate());
		return query.list();
	}
	
}
