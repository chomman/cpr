package cz.nlfnorm.quasar.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.dao.impl.BaseDaoImpl;
import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.quasar.constants.AuditorFilter;
import cz.nlfnorm.quasar.entities.AbstractLog;
import cz.nlfnorm.quasar.enums.LogStatus;
import cz.nlfnorm.utils.NlfStringUtils;

public abstract class AbstractLogDaoImpl<T extends AbstractLog> extends BaseDaoImpl<T, Long> {
	
	private final static int DEFAULT_PAGE_SIZE = 3;
	
	public AbstractLogDaoImpl(Class<T> persistentClass){
		super(persistentClass);
	}
	

	public LocalDate getEarliestPossibleDateForLog(final Long auditorId) {
		final String hql = "select max(item.auditDate) from "+persistentClass.getName()+"Item item " +
							" join item."+NlfStringUtils.firstCharacterDown(persistentClass.getSimpleName())+" log " +
							" join log.auditor auditor " +
						   " where auditor.id=:auditorId AND log.status = :status ";
		return (LocalDate) createQuery(hql)
						.setLong("auditorId", auditorId)
						.setInteger("status", LogStatus.APPROVED.getId())
						.setMaxResults(1)
						.uniqueResult();
		
	}
	
	@SuppressWarnings("unchecked")
	public PageDto getPage(Map<String, Object> criteria){
		StringBuilder hql = new StringBuilder("select al from "+ persistentClass.getName());
		hql.append(getAuditorJoinClouse());
		hql.append(prepareHqlForQuery(criteria));
		hql.append(getAuditorGroupByClouse());
		hql.append(" order by al.status, al.created DESC ");
		Query query = createQuery(hql);
		prepareHqlQueryParams(query, criteria);
		query.setMaxResults(DEFAULT_PAGE_SIZE);
		return new PageDto(query.list());
	}

	
	@SuppressWarnings("unchecked")
	public PageDto getPage(Map<String, Object> criteria, final int pageNumber) {
		StringBuilder hql = new StringBuilder("from "+ persistentClass.getName());
		hql.append(getAuditorJoinClouse());
		hql.append(prepareHqlForQuery(criteria));
		hql.append(getAuditorGroupByClouse());
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
	
	protected String getAuditorGroupByClouse() {
		return "";
	}
	
	protected String getAuditorJoinClouse(){
		return " al join al.auditor auditor left join auditor.partner partner ";
	}
	
	protected String prepareHqlForQuery(final Map<String, Object> criteria){
		List<String> where = new ArrayList<String>();
		if(criteria.size() > 0){
			prepareAuditor(where, criteria);
			preparePartner(where, criteria);
			prepareDateFrom(where, criteria);
			prepareDateTo(where, criteria);
			prepareLogStatus(where, criteria);
		}
		return where.size() > 0 ? " WHERE " + StringUtils.join(where.toArray(), " AND ") : "";

	}
	protected void prepareLogStatus(List<String> where, Map<String, Object> criteria) {
		Integer status = (Integer)criteria.get(AuditorFilter.STATUS);
		if(status != null && status != 0){
			where.add(" al.status = :"+AuditorFilter.STATUS);
		}
	}
	protected void prepareDateTo(List<String> where, Map<String, Object> criteria) {
		if((DateTime)criteria.get(AuditorFilter.DATE_TO) != null){
			where.add(" al.created < :"+AuditorFilter.DATE_TO);
		}
	}
	protected void prepareDateFrom(List<String> where, Map<String, Object> criteria) {
		if((DateTime)criteria.get(AuditorFilter.DATE_FROM) != null){
			where.add(" al.created >= :"+AuditorFilter.DATE_FROM);
		}
	}
	protected void prepareAuditor(List<String> where, Map<String, Object> criteria) {
		Long auditorId = (Long)criteria.get(AuditorFilter.AUDITOR);
		if(auditorId != null && auditorId != 0l){
			where.add(" auditor.id=:"+AuditorFilter.AUDITOR);
		}
	}
	protected void preparePartner(List<String> where, Map<String, Object> criteria) {
		Long partnerId = (Long)criteria.get(AuditorFilter.PARNTER);
		if(partnerId != null && partnerId != 0l){
			where.add(" partner.id=:"+AuditorFilter.PARNTER);
		}
	}
	
	
	protected void prepareHqlQueryParams(final Query query,final Map<String, Object> criteria){
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
			DateTime dateTo = (DateTime)criteria.get(AuditorFilter.DATE_TO);
			if(dateTo != null){
				query.setTimestamp(AuditorFilter.DATE_TO, dateTo.plusDays(1).toDate());
			}
			Integer status = (Integer)criteria.get(AuditorFilter.STATUS);
			if(status != null && status != 0){
				query.setInteger(AuditorFilter.STATUS, (Integer)criteria.get(AuditorFilter.STATUS));
			}
		}
	}
}
