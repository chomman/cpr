package sk.peterjurkovic.cpr.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.dao.CsnDao;
import sk.peterjurkovic.cpr.dto.PageDto;
import sk.peterjurkovic.cpr.entities.Csn;
import sk.peterjurkovic.cpr.entities.CsnTerminology;
import sk.peterjurkovic.cpr.enums.CsnOrderBy;
import sk.peterjurkovic.cpr.utils.CodeUtils;


@Repository("csnDao")
public class CsnDaoImpl extends BaseDaoImpl<Csn, Long> implements CsnDao{
	
	public CsnDaoImpl(){
		super(Csn.class);
	}

	@Override
	public boolean isCsnIdUniqe(final Long id,final String csnId) {
		String code = CodeUtils.toSeoUrl(csnId);
		StringBuilder hql = new StringBuilder("SELECT count(*) FROM Csn c WHERE c.code=:code");
		if(id != null && id != 0){
			hql.append(" AND c.id<>:id");
		}
		Long result = null;
		Query query = sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setString("code", code);
		if(id != null && id != 0){
			query.setLong("id", id);
		}
		result = (Long)query.uniqueResult();
		return (result == 0);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Csn> getCsnByTerminology(final String terminologyTitle) {
		StringBuilder hql = new StringBuilder("select t from CsnTerminology t ");
		hql.append(" where t.csn.enabled=true and t.title like CONCAT('%', :terminologyTitle , '%')");
		Query query =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setString("terminologyTitle", terminologyTitle);
		query.setMaxResults(50);
		return query.list();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public PageDto getCsnPage(final int pageNumber, final Map<String, Object> criteria){
		StringBuilder hql = new StringBuilder("from Csn csn");
		hql.append(prepareHqlForQuery(criteria));
		
		Session session = sessionFactory.getCurrentSession();
		Query hqlCountQuery = session.createQuery("select count(*) " + hql.toString());
		prepareHqlQueryParams(hqlCountQuery, criteria);
		PageDto items = new PageDto();
		hqlCountQuery.setMaxResults(1);
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
			query.setFirstResult(Constants.ADMIN_PAGINATION_PAGE_SIZE * ( pageNumber -1));
			query.setMaxResults(Constants.ADMIN_PAGINATION_PAGE_SIZE);
			items.setItems(query.list());
		}
	
		return items;
	}
	
	private String prepareHqlForQuery(final Map<String, Object> criteria){
		List<String> where = new ArrayList<String>();
		if(criteria.size() != 0){
			
			if(StringUtils.isNotBlank((String)criteria.get("query"))){
				where.add(" (unaccent(lower(csn.csnId)) like CONCAT('%', unaccent(lower(:query)) , '%') or unaccent(lower(csn.czechName)) like CONCAT('%', unaccent(lower(:query)) , '%') or csn.englishName like CONCAT('%', :query , '%')) ");
			}
			
			if(StringUtils.isNotBlank((String)criteria.get("catalogId"))){
				where.add(" csn.catalogId=:catalogId ");
			}
			
			if(StringUtils.isNotBlank((String)criteria.get("csnCategory"))){
				where.add(" csn.classificationSymbol like CONCAT('', :csnCategory , '%') ");
			}
		}
		return (where.size() > 0 ? " WHERE " + StringUtils.join(where.toArray(), " AND ") : "");
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
				hqlQuery.setString("catalogId", (String)criteria.get("catalogId"));
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CsnTerminology> getTerminologyByCsnAndLang(final Csn csn,final String languageCode) {
		StringBuilder hql = new StringBuilder("select t from CsnTerminology t ");
		hql.append(" where t.csn.id=:id and t.language=:lang");
		Query query =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setLong("id", csn.getId());
		query.setString("lang", languageCode.toUpperCase());
		return query.list();
	}

	@Override
	public void deleteAllTerminology(final Long id) {
		Query query =  sessionFactory.getCurrentSession()
				.createQuery("delete from CsnTerminology t where t.csn.id=:id");
		query.setLong("id", id);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Csn> autocompleteByClassificationSymbol(final String term) {
		StringBuilder hql = new StringBuilder("select c.csnId, c.classificationSymbol from Csn c");
		hql.append(" where c.classificationSymbol like CONCAT('', :term , '%')");
		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		hqlQuery.setParameter("term", term);
		hqlQuery.setMaxResults(8);
		return hqlQuery.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Csn> autocompleteByCsnId(final String term) {
		StringBuilder hql = new StringBuilder("select c.csnId from Csn c");
		hql.append(" where unaccent(lower(c.csnId)) like CONCAT('%', unaccent(lower(:term)) , '%')");
		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		hqlQuery.setParameter("term", term);
		hqlQuery.setMaxResults(8);
		return hqlQuery.list();
	}

	@Override
	public Csn getByClassificationSymbol(final String cs) {
		Query query =  sessionFactory.getCurrentSession().createQuery("from Csn c where c.classificationSymbol=:cs ");
		query.setParameter("cs", cs);
		query.setMaxResults(1);
		query.setCacheable(false);
		return (Csn)query.uniqueResult();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Csn> getCsnsByClassificationSymbol(final String cs) {
		Query query =  sessionFactory.getCurrentSession().createQuery("from Csn c where c.classificationSymbol=:cs ");
		query.setParameter("cs", cs);
		query.setCacheable(false);
		return query.list();
	}

	
	@Override
	public Csn getByCatalogId(final String catalogId) {
		Query query =  sessionFactory.getCurrentSession().createQuery("from Csn c where c.catalogId=:catalogId ");
		query.setParameter("catalogId", catalogId);
		query.setCacheable(false);
		query.setMaxResults(1);
		return (Csn)query.uniqueResult();
	}

	
	
}
