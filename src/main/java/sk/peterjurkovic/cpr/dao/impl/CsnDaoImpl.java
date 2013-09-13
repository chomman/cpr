package sk.peterjurkovic.cpr.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.dao.CsnDao;
import sk.peterjurkovic.cpr.dto.PageDto;
import sk.peterjurkovic.cpr.entities.Csn;
import sk.peterjurkovic.cpr.entities.CsnTerminology;
import sk.peterjurkovic.cpr.enums.CsnOrderBy;
import sk.peterjurkovic.cpr.enums.StandardOrder;


@Repository("csnDao")
public class CsnDaoImpl extends BaseDaoImpl<Csn, Long> implements CsnDao{
	
	public CsnDaoImpl(){
		super(Csn.class);
	}

	@Override
	public boolean isCsnIdUniqe(final Long id,final String csnId) {
		StringBuilder hql = new StringBuilder("SELECT count(*) FROM Csn c WHERE c.csnId=:csnId");
		if(id != null && id != 0){
			hql.append(" AND c.id<>:id");
		}
		Long result = null;
		Query query = sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setString("csnId", csnId);
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
		hql.append(" group by csn.id ");
		if((Integer)criteria.get("orderBy") != null){
			hql.append(CsnOrderBy.getSqlById((Integer)criteria.get("orderBy") ));
		}else{
			hql.append(CsnOrderBy.getSqlById(1));
		}
		Query hqlQuery = sessionFactory.getCurrentSession().createQuery("select count(*) " + hql.toString());
		prepareHqlQueryParams(hqlQuery, criteria);
		PageDto items = new PageDto();
		items.setCount((Long)hqlQuery.uniqueResult());
		if(items.getCount() > 0){
			hqlQuery = sessionFactory.getCurrentSession().createQuery(hql.toString());
			prepareHqlQueryParams(hqlQuery, criteria);
			hqlQuery.setFirstResult(Constants.ADMIN_PAGINATION_PAGE_SIZE * ( pageNumber -1));
			hqlQuery.setMaxResults(Constants.ADMIN_PAGINATION_PAGE_SIZE);
			items.setItems(hqlQuery.list());
		}
	
		return items;
	}
	
	private String prepareHqlForQuery(final Map<String, Object> criteria){
		List<String> where = new ArrayList<String>();
		if(criteria.size() != 0){
			
			if(StringUtils.isNotBlank((String)criteria.get("query"))){
				where.add(" csn.csnId like CONCAT('%', :query , '%') or csn.czechName like CONCAT('%', :query , '%') or csn.englishName like CONCAT('%', :query , '%') ");
			}
		}
		return (where.size() > 0 ? " WHERE " + StringUtils.join(where.toArray(), " AND ") : "");
	}
	
	
	private void prepareHqlQueryParams(final Query hqlQuery,final Map<String, Object> criteria){
		if(criteria.size() != 0){
			if(StringUtils.isNotBlank((String)criteria.get("query"))){
				hqlQuery.setString("query", (String)criteria.get("query"));
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CsnTerminology> getTerminologyByCsnAndLang(Csn csn, String languageCode) {
		StringBuilder hql = new StringBuilder("select t from CsnTerminology t ");
		hql.append(" where t.csn.id=:id and t.language=:lang");
		Query query =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setLong("id", csn.getId());
		query.setString("lang", languageCode);
		return query.list();
	}

	@Override
	public void deleteAllTerminology(Long id) {
		Query query =  sessionFactory.getCurrentSession()
				.createQuery("delete from CsnTerminology t where t.csn.id=:id");
		query.setLong("id", id);
		query.executeUpdate();
	}

	
	
}
