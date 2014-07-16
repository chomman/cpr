package cz.nlfnorm.quasar.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.Query;
import org.joda.time.DateTime;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.constants.Filter;
import cz.nlfnorm.dao.impl.BaseDaoImpl;
import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.quasar.constants.AuditorFilter;

public abstract class AbstractLogDaoImpl extends BaseDaoImpl<T, Long>{

	public AbstractLogDaoImpl(Class<T> persistentClass){
		super(persistentClass);
	}
	
	@SuppressWarnings("unchecked")
	public PageDto getPage(Map<String, Object> criteria, final int pageNumber) {
		StringBuilder hql = new StringBuilder("from "+ persistentClass.getName() +" al join al.auditor auditor");
		hql.append(" left join auditor.partner partner ");
		hql.append(prepareHqlForQuery(criteria));
		Query hqlCountQuery = createQuery("select count(*) " + hql.toString());
		prepareHqlQueryParams(hqlCountQuery, criteria);
		PageDto items = new PageDto();
		hqlCountQuery.setMaxResults(1);
		items.setCount((Long)hqlCountQuery.uniqueResult());
		if(items.getCount() > 0){
			hql.append(" order by al.status, al.created DESC ");
			Query query = createQuery("select al " + hql.toString());
			prepareHqlQueryParams(query, criteria);
			query.setFirstResult(Constants.ADMIN_PAGINATION_PAGE_SIZE * ( pageNumber -1));
			query.setMaxResults(Constants.ADMIN_PAGINATION_PAGE_SIZE);
			items.setItems(query.list());
		}
		return items;
	}
	
	private String prepareHqlForQuery(final Map<String, Object> criteria){
		List<String> where = new ArrayList<String>();
		if(criteria.size() > 0){
			Long auditorId = (Long)criteria.get(AuditorFilter.AUDITOR);
			if(auditorId != null && auditorId != 0l){
				where.add(" auditor.id=:"+AuditorFilter.AUDITOR);
			}
			Long partnerId = (Long)criteria.get(AuditorFilter.PARNTER);
			if(partnerId != null && partnerId != 0l){
				where.add(" partner.id=:"+AuditorFilter.PARNTER);
			}
			if((DateTime)criteria.get(AuditorFilter.DATE_FROM) != null){
				where.add(" al.created >= :"+AuditorFilter.DATE_FROM);
			}
			if((DateTime)criteria.get(AuditorFilter.DATE_TO) != null){
				where.add(" al.created < :"+AuditorFilter.DATE_TO);
			}
			Integer status = (Integer)criteria.get(AuditorFilter.STATUS);
			if(status != null && status != 0){
				where.add(" al.status = :"+AuditorFilter.STATUS);
			}
		}
		return where.size() > 0 ? " WHERE " + StringUtils.join(where.toArray(), " AND ") : "";

	}
	
	private void prepareHqlQueryParams(final Query query,final Map<String, Object> criteria){
		if(criteria.size() != 0){
			Long auditorId = (Long)criteria.get(AuditorFilter.AUDITOR);
			if(auditorId != null && auditorId != 0l){
				query.setLong(AuditorFilter.AUDITOR, auditorId);
			}
			Long partnerId = (Long)criteria.get(AuditorFilter.PARNTER);
			if(partnerId != null && partnerId != 0l){
				query.setLong(AuditorFilter.PARNTER, partnerId);
			}
			DateTime dateFrom = (DateTime)criteria.get(AuditorFilter.DATE_FROM);
			if(dateFrom != null){
				query.setTimestamp(AuditorFilter.DATE_FROM, dateFrom.toDate());
			}
			DateTime dateTo = (DateTime)criteria.get(Filter.CREATED_TO);
			if(dateTo != null){
				query.setTimestamp(Filter.CREATED_TO, dateTo.plusDays(1).toDate());
			}
			Integer status = (Integer)criteria.get(AuditorFilter.STATUS);
			if(status != null && status != 0){
				query.setInteger(AuditorFilter.STATUS, (Integer)criteria.get(AuditorFilter.STATUS));
			}
		}
	}
}
