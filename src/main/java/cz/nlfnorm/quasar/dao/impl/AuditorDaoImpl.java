package cz.nlfnorm.quasar.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.constants.Filter;
import cz.nlfnorm.dao.impl.BaseDaoImpl;
import cz.nlfnorm.dto.AutocompleteDto;
import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.Authority;
import cz.nlfnorm.quasar.constants.AuditorFilter;
import cz.nlfnorm.quasar.dao.AuditorDao;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.enums.AuditorOrder;
import cz.nlfnorm.quasar.views.ProductAssessorA;
import cz.nlfnorm.quasar.views.ProductAssessorR;
import cz.nlfnorm.quasar.views.ProductSpecialist;
import cz.nlfnorm.quasar.views.QsAuditor;

/**
 * QUASAR
 * 
 * Hibernate implementation of Auditor DAO interface
 * 
 * @author Peter Jurkovic
 * @date Jun 12, 2014
 */
@Repository("auditorDao")
public class AuditorDaoImpl extends BaseDaoImpl<Auditor, Long> implements AuditorDao{

	public AuditorDaoImpl() {
		super(Auditor.class);
	}

	@Override
	public boolean isItcIdUniqe(Integer itcId, Long auditorId) {
		final Query query = createQuery("select count(*) from Auditor a where a.itcId=:itcId and a.id<>:auditorId ")
			.setCacheable(false)
			.setMaxResults(1)
			.setInteger("itcId", itcId)
			.setLong("auditorId", auditorId);
		return (Long)query.uniqueResult() == 0l ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AutocompleteDto> autocomplete(String term, Boolean enabledObly, Boolean adminsOnly) {
		StringBuilder hql = new StringBuilder("select u.id as id, CONCAT(u.firstName, ' ', u.lastName) as name from User u  ");
		hql.append(" join u.authoritySet authority ")
			.append(" WHERE ( unaccent(lower(u.firstName)) like CONCAT('%', unaccent(:query) , '%') OR")
			.append(" unaccent(lower(u.lastName)) like CONCAT('%', unaccent(:query) , '%') )");
		if(enabledObly != null && enabledObly){
			hql.append(" and u.enabled = true ");
		}
		if(adminsOnly != null){
			hql.append(" and authority.code = :authority ");
		}
		hql.append(" group by u.id ");
		Query query = sessionFactory.getCurrentSession()
				.createQuery(hql.toString())
				.setString("query", term.toLowerCase())
				.setCacheable(false)
				.setMaxResults(8);
		if(adminsOnly != null){
			if(adminsOnly){
				query.setString("authority", Authority.ROLE_QUASAR_ADMIN);
			}else{
				query.setString("authority", Authority.ROLE_AUDITOR);
			}
		}
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<QsAuditor> getAllQsAuditors(){
		return getSessionFactory()
			  .getCurrentSession()
			  .createSQLQuery("select * from quasar_qs_auditor")
			  .setResultTransformer(Transformers.aliasToBean(QsAuditor.class))
			  .setReadOnly(true)
			  .list();
	}

	@SuppressWarnings("unchecked")
	public PageDto getAuditorPage(final int pageNumber, final Map<String, Object> criteria){
		StringBuilder hql = new StringBuilder("from Auditor a");
		hql.append(prepareHqlForQuery(criteria));
		Query hqlCountQuery = createQuery("select count(*) " + hql.toString());
		prepareHqlQueryParams(hqlCountQuery, criteria);
		PageDto items = new PageDto();
		hqlCountQuery.setMaxResults(1);
		items.setCount((Long)hqlCountQuery.uniqueResult());
		if(items.getCount() > 0){
			if((Integer)criteria.get(Filter.ORDER) != null){
				hql.append(AuditorOrder.getSqlById((Integer)criteria.get(Filter.ORDER) ));
			}else{
				hql.append(AuditorOrder.getSqlById(1));
			}
			Query query = createQuery(hql.toString());
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
			if(StringUtils.isNotBlank((String)criteria.get("query"))){
				where.add(" (unaccent(lower(a.firstName)) like unaccent(lower(:query)) OR " +
						  "  unaccent(lower(a.lastName))  like unaccent(lower(:query)) OR "
						  + "unaccent(lower(CONCAT(a.firstName,' ',a.lastName))) like unaccent(lower(:query))) ");
			}
			final Boolean enabled = (Boolean)criteria.get(Filter.ENABLED);
			if(enabled != null){
				where.add(" a.enabled=:"+Filter.ENABLED);
			}
			final Boolean inTraining = (Boolean)criteria.get(AuditorFilter.IN_TRAINING);
			if(inTraining != null){
				where.add(" a.inTraining=:"+AuditorFilter.IN_TRAINING);
			}
			final Boolean formalLegalReq = (Boolean)criteria.get(AuditorFilter.FORMAL_LEG_REQ);
			if(formalLegalReq != null){
				where.add(formalLegalReq ? 
						" (a.ecrCardSigned=true AND a.confidentialitySigned=true AND a.cvDelivered=true) " :
					    " (a.ecrCardSigned=false OR a.confidentialitySigned=false OR a.cvDelivered=false) ");
			}
			final Boolean internalOnly = (Boolean)criteria.get(AuditorFilter.INTERNAL_ONLY);
			if(internalOnly != null){
				// Auditor with itcId lower than 1000 is internal auditor
				where.add(" a.itcId" + (internalOnly ? "<" : ">") + "1000");
			}
			Long partnerId = (Long)criteria.get(AuditorFilter.PARNTER);
			if(partnerId != null && partnerId != 0l){
				where.add(" a.partner.id=:"+AuditorFilter.PARNTER);
			}
			if((DateTime)criteria.get(AuditorFilter.DATE_FROM) != null){
				where.add(" a.reassessmentDate >= :"+AuditorFilter.DATE_FROM);
			}
			if((DateTime)criteria.get(AuditorFilter.DATE_TO) != null){
				where.add(" a.reassessmentDate < :"+AuditorFilter.DATE_TO);
			}
		}
		return where.size() > 0 ? " WHERE " + StringUtils.join(where.toArray(), " AND ") : "";

	}
	
	private void prepareHqlQueryParams(final Query hqlQuery,final Map<String, Object> criteria){
		if(criteria.size() != 0){
			if(StringUtils.isNotBlank((String)criteria.get("query"))){
				hqlQuery.setString("query", (String)criteria.get("query"));
			}
			Boolean enabled = (Boolean)criteria.get(Filter.ENABLED);
			if(enabled != null){
				hqlQuery.setBoolean(Filter.ENABLED, enabled);
			}
			Boolean inTraining = (Boolean)criteria.get(AuditorFilter.IN_TRAINING);
			if(inTraining != null){
				hqlQuery.setBoolean(AuditorFilter.IN_TRAINING, inTraining);
			}
			Long partnerId = (Long)criteria.get(AuditorFilter.PARNTER);
			if(partnerId != null && partnerId != 0l){
				hqlQuery.setLong(AuditorFilter.PARNTER, partnerId);
			}
			DateTime dateFrom = (DateTime)criteria.get(AuditorFilter.DATE_FROM);
			if(dateFrom != null){
				hqlQuery.setTimestamp(AuditorFilter.DATE_FROM, dateFrom.toDate());
			}
			DateTime dateTo = (DateTime)criteria.get(Filter.CREATED_TO);
			if(dateTo != null){
				hqlQuery.setTimestamp(Filter.CREATED_TO, dateTo.plusDays(1).toDate());
			}
		}
	}
	

	public QsAuditor getQsAuditorById(final Long id){
		return (QsAuditor)createQuery("from QsAuditor where id=:id")
				  .setReadOnly(true)
				  .setLong("id", id)
				  .setMaxResults(1)
				  .uniqueResult();
	}

	@Override
	public ProductAssessorA getProductAssessorAById(Long id) {
		return (ProductAssessorA)createQuery("from ProductAssessorA where id=:id")
				  .setReadOnly(true)
				  .setLong("id", id)
				  .setMaxResults(1)
				  .uniqueResult();
	}

	@Override
	public ProductAssessorR getProductAssessorRById(Long id) {
		return (ProductAssessorR)createQuery("from ProductAssessorR where id=:id")
				  .setReadOnly(true)
				  .setLong("id", id)
				  .setMaxResults(1)
				  .uniqueResult();
	}

	@Override
	public ProductSpecialist getProductSpecialistById(Long id) {
		return (ProductSpecialist)createQuery("from ProductSpecialist where id=:id")
				  .setReadOnly(true)
				  .setLong("id", id)
				  .setMaxResults(1)
				  .uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Auditor> getAuditors(final Map<String, Object> criteria) {
		StringBuilder hql = new StringBuilder("from Auditor a");
		hql.append(prepareHqlForQuery(criteria));
		if((Integer)criteria.get(Filter.ORDER) != null){
			hql.append(AuditorOrder.getSqlById((Integer)criteria.get(Filter.ORDER) ));
		}else{
			hql.append(AuditorOrder.ITC_ID.getSql());
		}
		Query query = createQuery(hql.toString());
		prepareHqlQueryParams(query, criteria);
		return query.list();
	}
	
	
	@Override
	public Integer getCountOfAuditDaysInRecentYear(final Long auditorId){
		final String sql = "SELECT audit_days(a, s) as audit_days " + 
						   "FROM quasar_auditor a " + 
						   "CROSS JOIN  quasar_settings s " + 
						   "where a.id = :auditorId";
		return ((Integer)getSessionFactory().getCurrentSession()
				.createSQLQuery(sql)
				.setLong("auditorId", auditorId)
				.setMaxResults(1)
				.setReadOnly(true)
				.uniqueResult()).intValue();
	}
}
