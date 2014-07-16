package cz.nlfnorm.quasar.dao.impl;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.impl.BaseDaoImpl;
import cz.nlfnorm.quasar.dao.AuditorEacCodeDao;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.AuditorEacCode;

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
		String hql = "from AuditorEacCode c where c.eacCode.forQsAuditor=true and c.auditor.id=:id order by c.eacCode.id";
		return createQuery(hql)
				.setLong("id", auditor.getId())
				.list();
	}
	
		
	@Override
	public AuditorEacCode getByEacCode(final String code, final Long auditorId) {
		StringBuilder hql = new StringBuilder("from AuditorEacCode auditorCode");
		hql.append(" where auditorCode.auditor.id = :auditorId and auditorCode.eacCode.code = :code ");
		return (AuditorEacCode) createQuery(hql)
			.setLong("auditorId", auditorId)
			.setString("code", code)
			.setMaxResults(1)
			.uniqueResult();
	}
	
	
	@Override
	public void incrementAuditorEacCodeTotals(final Long eacCodeId, final Long auditorId, final int plusNbAudits, final int plusIso13485Audits) {
		StringBuilder hql = new StringBuilder("update AuditorEacCode code set ")
		.append(" 	code.numberOfNbAudits = code.numberOfNbAudits + :plusNbAudits, ")
		.append(" 	code.numberOfIso13485Audits = code.numberOfIso13485Audits + :plusIso13485Audits, ")
		.append("   code.changed = :changed ")
		.append(" where code.auditor.id = :auditorId and code.eacCode.id = :eacCodeId ");
		createQuery(hql)
			.setLong("auditorId", auditorId)
			.setLong("eacCodeId", eacCodeId)
			.setInteger("plusNbAudits", plusNbAudits)
			.setInteger("plusIso13485Audits", plusIso13485Audits)
			.setTimestamp("changed", new LocalDateTime().toDate())
			.executeUpdate();
	}
	
}
