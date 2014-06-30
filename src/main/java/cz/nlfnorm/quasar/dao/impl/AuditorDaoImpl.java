package cz.nlfnorm.quasar.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.impl.BaseDaoImpl;
import cz.nlfnorm.dto.AutocompleteDto;
import cz.nlfnorm.entities.Authority;
import cz.nlfnorm.quasar.dao.AuditorDao;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.views.ProductAssessorA;
import cz.nlfnorm.quasar.views.ProductAssessorR;
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
}
