package sk.peterjurkovic.cpr.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.hibernate.Query;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.dao.CsnTerminologyLogDao;
import sk.peterjurkovic.cpr.dto.PageDto;
import sk.peterjurkovic.cpr.entities.Csn;
import sk.peterjurkovic.cpr.entities.CsnTerminologyLog;


@Repository("csnTerminologyLogDao")
public class CsnTerminologyLogDaoImpl extends BaseDaoImpl<CsnTerminologyLog, Long> implements CsnTerminologyLogDao{
	
	public CsnTerminologyLogDaoImpl(){
		super(CsnTerminologyLog.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageDto getLogPage(int currentPage, Map<String, Object> criteria) {
		StringBuilder hql = new StringBuilder("from " + CsnTerminologyLog.class.getName() + " l ");
		hql.append(prepareHqlForQuery(criteria));
		Query hqlQuery = sessionFactory.getCurrentSession().createQuery("select count(*) " + hql.toString());
		prepareHqlQueryParams(hqlQuery, criteria);
		PageDto items = new PageDto();
		Long countOfItems = (Long)hqlQuery.uniqueResult();
		if(countOfItems == null){
			items.setCount(0l);
		}else{
			items.setCount(countOfItems);
			if(items.getCount() > 0){
				hql.append(" order by l.created desc ");
				hqlQuery = sessionFactory.getCurrentSession().createQuery(hql.toString());
				prepareHqlQueryParams(hqlQuery, criteria);
				hqlQuery.setCacheable(false);
				hqlQuery.setFirstResult(Constants.ADMIN_PAGINATION_PAGE_SIZE * ( currentPage -1));
				hqlQuery.setMaxResults(Constants.ADMIN_PAGINATION_PAGE_SIZE);
				items.setItems(hqlQuery.list());
			}
		}
		return items;
	}
	
	
	private String prepareHqlForQuery(final Map<String, Object> criteria){
		List<String> where = new ArrayList<String>();
		if(criteria.size() != 0){
			
			if(StringUtils.isNotBlank((String)criteria.get("query"))){
				where.add(" l.csn.csnId like CONCAT('%', :query , '%') or l.csn.classificationSymbol like CONCAT('%', :query , '%') ");
			}
			
			if(StringUtils.isNotBlank((String)criteria.get("importStatus"))){
				where.add(" l.importStatus= :importStatus");
			}
			
			if((Boolean)criteria.get("success") != null){
				where.add(" l.success= :success");
			}
			
			DateTime createdFrom = (DateTime)criteria.get("createdFrom");
			if(createdFrom != null){
				where.add(" l.created >= :createdFrom");
			}
						
			DateTime createdTo = (DateTime)criteria.get("createdTo");
			if(createdTo != null){
				where.add(" l.created < :createdTo");
			}

		}
		return (where.size() > 0 ? " WHERE " + StringUtils.join(where.toArray(), " AND ") : "");
	}
	
	private void prepareHqlQueryParams(final Query hqlQuery,final Map<String, Object> criteria){
		if(criteria.size() != 0){
			if(StringUtils.isNotBlank((String)criteria.get("query"))){
				hqlQuery.setString("query", (String)criteria.get("query"));
			}
			
			if(StringUtils.isNotBlank((String)criteria.get("importStatus"))){
				hqlQuery.setString("importStatus", (String)criteria.get("importStatus"));
			}
			
			Boolean success = (Boolean)criteria.get("success");
			if(success != null){
				hqlQuery.setBoolean("success", success);
			}
			
			
			DateTime createdFrom = (DateTime)criteria.get("createdFrom");
			if(createdFrom != null){
				hqlQuery.setTimestamp("createdFrom", createdFrom.toDate());
			}
			
			DateTime createdTo = (DateTime)criteria.get("createdTo");
			if(createdTo != null){
				hqlQuery.setTimestamp("createdTo", createdTo.plusDays(1).toDate());
			}
			
			
		}
	}

	@Override
	public void removeCsnLogs(Csn csn) {
		Validate.notNull(csn);
		Query hqlQuery = sessionFactory.getCurrentSession()
					.createQuery("delete from " + CsnTerminologyLog.class.getName() + " l where l.csn.id=:id");
		hqlQuery.setLong("id", csn.getId());
		hqlQuery.executeUpdate();
	}
	
	
}
