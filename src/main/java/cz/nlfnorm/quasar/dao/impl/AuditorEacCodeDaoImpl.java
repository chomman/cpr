package cz.nlfnorm.quasar.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.impl.BaseDaoImpl;
import cz.nlfnorm.quasar.dao.AuditorEacCodeDao;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.AuditorEacCode;
import cz.nlfnorm.quasar.entities.EacCode;

/**
 * 
 * @author Peter Jurkovic
 * @date Jun 16, 2014
 */
@Repository("auditorEacCodeDao")
public class AuditorEacCodeDaoImpl extends BaseDaoImpl<AuditorEacCode, Long> implements AuditorEacCodeDao{
	
	public AuditorEacCodeDaoImpl(){
		super(AuditorEacCode.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuditorEacCode> getAllAuditorEacCodes(final Auditor auditor) {
		String hql = "from AuditorEacCode c where c.enabled=true and c.auditor.id=:id order by c.code";
		return createQuery(hql)
				.setLong("id", auditor.getId())
				.list();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EacCode> getAllNonAssociatedCodesWith(final Auditor auditor) {
		StringBuilder hql = new StringBuilder("select code from EacCode code ");
		hql.append(" where code.forQsAuditor = true and not EXISTS( ")
			.append(" select 1 from AuditorEacCode ac ")
			.append(" where ac.id = code.id and ec.auditor.id = :id )");
		return createQuery(hql)
					.setCacheable(false)
					.setLong("id", auditor.getId())
					.list();
	}

	
	@Override
	public AuditorEacCode getByEacCode(final String code, final Long auditorId) {
		StringBuilder hql = new StringBuilder("from AuditorEacCode auditorCode");
		hql.append(" join auditorCode.auditor.id = :auditorId and auditorCode.eacCode.code = :code ");
		return (AuditorEacCode) createQuery(hql)
			.setLong("auditorId", auditorId)
			.setString("code", code)
			.setMaxResults(1)
			.uniqueResult();
		
	}
	
}
