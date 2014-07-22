package cz.nlfnorm.quasar.dao.impl;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.impl.BaseDaoImpl;
import cz.nlfnorm.quasar.dao.AuditorNandoCodeDao;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.AuditorNandoCode;

/**
 * QUASAR component
 * 
 * Represents Auditors Nando code dao layer
 * 
 * @author Peter Jurkovic
 * @date Jun 16, 2014
 */
@Repository("auditorNandoCodeDao")
public class AuditorNandoCodeDaoImpl extends BaseDaoImpl<AuditorNandoCode, Long> implements AuditorNandoCodeDao{
	
	public AuditorNandoCodeDaoImpl(){
		super(AuditorNandoCode.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuditorNandoCode> getAllAuditorNandoCodes(Auditor auditor) {
		final String hql = "from AuditorNandoCode ac" + 
					 "	where ac.enabled = true and ac.auditor.id = :id " + 
					 "	order by coalesce(ac.nandoCode.parent.order, ac.nandoCode.order) ";
		return createQuery(hql)
				.setLong("id", auditor.getId())
				.list();
	}

	@Override
	public AuditorNandoCode getByNandoCode(final String code, final Long auditorId) {
		StringBuilder hql = new StringBuilder("from AuditorNandoCode c");
		hql.append(" where c.auditor.id = :auditorId and c.nandoCode.code = :code ");
		return (AuditorNandoCode) createQuery(hql)
			.setLong("auditorId", auditorId)
			.setString("code", code)
			.setMaxResults(1)
			.uniqueResult();
		
	}
	
	@SuppressWarnings("unchecked")
	private List<AuditorNandoCode> getCodesForType(final int type, final Long auditorId) {
		final String hql =  
				"select ac from AuditorNandoCode ac" + 
				"	join ac.nandoCode nandoCode " +
				"	where ac.auditor.id=:id and nandoCode." + determineAuditorType(type) + "= true " +
				"	order by nandoCode.code ";
		return createQuery(hql)
				.setLong("id", auditorId)
				.list();
	}
	
	@Override
	public List<AuditorNandoCode> getForProductAssessorA(Auditor auditor) {
		return getCodesForType(Auditor.TYPE_PRODUCT_ASSESSOR_A, auditor.getId());
	}
	
	@Override
	public List<AuditorNandoCode> getForProductAssessorR(Auditor auditor) {
		return getCodesForType(Auditor.TYPE_PRODUCT_ASSESSOR_R, auditor.getId());
	}

	@Override
	public List<AuditorNandoCode> getForProductSpecialist(Auditor auditor) {
		return getCodesForType(Auditor.TYPE_PRODUCT_SPECIALIST, auditor.getId());
	}
	
	
	@Override
	public void incrementProductAssessorATotals(final Long nandoCodeId, final Long auditorId, final int plusNbAudits, final int plusIso13485Audits) {
		StringBuilder hql = new StringBuilder("update AuditorNandoCode code set ")
		.append(" 	code.numberOfNbAudits = code.numberOfNbAudits + :plusNbAudits, ")
		.append(" 	code.numberOfIso13485Audits = code.numberOfIso13485Audits + :plusIso13485Audits, ")
		.append("   code.changed = :changed ")
		.append(" where code.auditor.id = :auditorId and code.nandoCode.id = :nandoCodeId ");
		createQuery(hql)
			.setLong("auditorId", auditorId)
			.setLong("nandoCodeId", nandoCodeId)
			.setInteger("plusNbAudits", plusNbAudits)
			.setInteger("plusIso13485Audits", plusIso13485Audits)
			.setTimestamp("changed", new LocalDateTime().toDate())
			.executeUpdate();
	}
	
	@Override
	public void incrementProductAssessorRAndProductSpecialistTotals(
			final Long nandoCodeId, 
			final Long auditorId, 
			final int plusTfReviews, 
			final int plusDdReviews) {
		
		StringBuilder hql = new StringBuilder("update AuditorNandoCode code set ")
		.append(" 	code.numberOfTfReviews = code.numberOfTfReviews + :plusTfReviews, ")
		.append(" 	code.numberOfDdReviews = code.numberOfDdReviews + :plusDdReviews, ")
		.append("   code.changed = :changed ")
		.append(" where code.auditor.id = :auditorId and code.nandoCode.id = :nandoCodeId ");
		createQuery(hql)
			.setLong("auditorId", auditorId)
			.setLong("nandoCodeId", nandoCodeId)
			.setInteger("plusTfReviews", plusTfReviews)
			.setInteger("plusDdReviews", plusDdReviews)
			.setTimestamp("changed", new LocalDateTime().toDate())
			.executeUpdate();
	}
	
	
	private String determineAuditorType(final int type){
		switch(type){
			case Auditor.TYPE_PRODUCT_ASSESSOR_A :
				return "forProductAssesorA";
			case Auditor.TYPE_PRODUCT_ASSESSOR_R :
				return "forProductAssesorR";
			case Auditor.TYPE_PRODUCT_SPECIALIST :
				return "forProductSpecialist";
			default:
			throw new IllegalArgumentException("Unknown auditor type: " + type);	
		}
	}
	
	
	
}	
