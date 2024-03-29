package cz.nlfnorm.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.constants.Filter;
import cz.nlfnorm.dao.PortalOrderDao;
import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.PortalOrder;
import cz.nlfnorm.enums.PortalOrderOrder;

@Repository("portalOrderDao")
public class PortalOrderDaoImpl extends BaseDaoImpl<PortalOrder, Long> implements PortalOrderDao {

	public PortalOrderDaoImpl(){
		super(PortalOrder.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageDto getPortalOrderPage(int currentPage, Map<String, Object> criteria) {
		StringBuilder hql = new StringBuilder("from ");
		hql.append(PortalOrder.class.getName() )
			.append(" o")
		    .append(prepareHqlForQuery(criteria));
		Query hqlQuery = sessionFactory.getCurrentSession().createQuery("select count(*) " + hql.toString());
		hqlQuery.setCacheable(false);
		prepareHqlQueryParams(hqlQuery, criteria);
		PageDto items = new PageDto();
		Long countOfItems = (Long)hqlQuery.uniqueResult();
		if(countOfItems == null){
			items.setCount(0l);
		}else{
			items.setCount(countOfItems);
			if(items.getCount() > 0){
				setupOrdering(criteria, hql);
				hqlQuery = sessionFactory.getCurrentSession().createQuery(hql.toString());
				prepareHqlQueryParams(hqlQuery, criteria);
				hqlQuery.setCacheable(false);
				hqlQuery.setFirstResult(Constants.ADMIN_PAGINATION_PAGE_SIZE * ( currentPage -1));
				hqlQuery.setMaxResults(Constants.ADMIN_PAGINATION_PAGE_SIZE);
				hqlQuery.setCacheable(false);
				items.setItems(hqlQuery.list());
			}
		}
		return items;
	}
	
	private void setupOrdering(final Map<String, Object> criteria, StringBuilder hql){
		final Integer orderId = (Integer)criteria.get(Filter.ORDER);
		if(orderId != null && orderId > 0){
			hql.append(PortalOrderOrder.getById((Integer)criteria.get(Filter.ORDER) ).getSql());
		}else{
			hql.append(PortalOrderOrder.CREATE_DESC.getSql());
		}
	}
	
	private String prepareHqlForQuery(final Map<String, Object> criteria){
		List<String> where = new ArrayList<String>();
		where.add(" o.deleted = false ");
		if(criteria.size() != 0){
			if(StringUtils.isNotBlank((String)criteria.get("query"))){
				where.add(" (unaccent(lower(o.firstName)) like CONCAT('%', unaccent(lower(:query)) , '%') OR" +
						  "  unaccent(lower(o.lastName)) like CONCAT('%', unaccent(lower(:query)) , '%') )");
			}
			if((DateTime)criteria.get(Filter.CREATED_FROM) != null){
				where.add(" o.created >= :" + Filter.CREATED_FROM);
			}
			if((DateTime)criteria.get(Filter.CREATED_TO) != null){
				where.add(" o.created < :" + Filter.CREATED_TO);
			}
			if(StringUtils.isNotBlank((String)criteria.get(Filter.ORDER_STATUS) )){
				where.add(" o.orderStatus =:" + Filter.ORDER_STATUS);
			}
			if(StringUtils.isNotBlank((String)criteria.get("orderSource") )){
				where.add(" o.portalOrderSource = :orderSource");
			}
		}
		return (where.size() > 0 ? " WHERE " + StringUtils.join(where.toArray(), " AND ") : "");
	}
	
	
	private void prepareHqlQueryParams(final Query hqlQuery,final Map<String, Object> criteria){
		if(criteria.size() != 0){
			if(StringUtils.isNotBlank((String)criteria.get("query"))){
				hqlQuery.setString("query", ((String)criteria.get("query")).trim());
			}
			final DateTime createdFrom = (DateTime)criteria.get(Filter.CREATED_FROM);
			if(createdFrom != null){
				hqlQuery.setTimestamp(Filter.CREATED_FROM, createdFrom.toDate());
			}
			final DateTime createdTo = (DateTime)criteria.get(Filter.CREATED_TO);
			if(createdTo != null){
				hqlQuery.setTimestamp(Filter.CREATED_TO, createdTo.toDate());
			}
			String status = (String)criteria.get(Filter.ORDER_STATUS);
			if(StringUtils.isNotBlank( status )){
				hqlQuery.setString(Filter.ORDER_STATUS, status);
			}
			String orderSource = (String)criteria.get("orderSource");
			if(StringUtils.isNotBlank( orderSource )){
				hqlQuery.setString("orderSource", orderSource);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PortalOrder> getUserOrders(final Long userId) {
		StringBuilder hql = new StringBuilder("select o from ");
		hql.append(PortalOrder.class.getName());
		hql.append(" o join o.user u where u.id =:userId order by o.created desc ");
		Query hqlQuery = sessionFactory.getCurrentSession().createQuery(hql.toString());
		hqlQuery.setLong("userId", userId);
		hqlQuery.setCacheable(false);
		return hqlQuery.list();
	}

}
