package sk.peterjurkovic.cpr.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.dao.StandardCsnDao;
import sk.peterjurkovic.cpr.dto.PageDto;
import sk.peterjurkovic.cpr.entities.StandardCsn;
import sk.peterjurkovic.cpr.enums.CsnOrderBy;

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
			if((Integer)criteria.get("orderBy") != null){
				hql.append(CsnOrderBy.getSqlById((Integer)criteria.get("orderBy") ));
			}else{
				hql.append(CsnOrderBy.getSqlById(1));
			}
			Query query = session.createQuery(hql.toString());
			prepareHqlQueryParams(query, criteria);
			query.setCacheable(false);
			query.setFirstResult(Constants.ADMIN_PAGINATION_PAGE_SIZE * ( pageNumber -1));
			query.setMaxResults(Constants.ADMIN_PAGINATION_PAGE_SIZE);
			items.setItems(query.list());
		}
	
		return items;
	}
	
	private void prepareHqlQueryParams(final Query hqlQuery,final Map<String, Object> criteria){
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
				where.add(" (unaccent(lower(csn.csnName)) like CONCAT('%', unaccent(lower(:query)) , '%')  ");
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
	
}
