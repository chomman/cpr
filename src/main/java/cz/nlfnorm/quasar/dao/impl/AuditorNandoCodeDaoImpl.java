package cz.nlfnorm.quasar.dao.impl;

import java.util.List;

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
	public AuditorNandoCode getByNandoCode(String code, Long auditorId) {
		StringBuilder hql = new StringBuilder("from AuditorNandoCode c");
		hql.append(" where c.auditor.id = :auditorId and c.nandoCode.code = :code ");
		return (AuditorNandoCode) createQuery(hql)
			.setLong("auditorId", auditorId)
			.setString("code", code)
			.setMaxResults(1)
			.uniqueResult();
		
	}
	
	@SuppressWarnings("unchecked")
	private List<AuditorNandoCode> getCodesForType(final int type, Long id) {
		final String hql =  
				"select ac from AuditorNandoCode ac" + 
				"	inner join ac.nandoCode nandoCode " +
				"	where ac.auditor.id=:id and nandoCode." + determineAuditorType(type) + "= true " +
				"	order by coalesce(ac.nandoCode.parent.order, ac.nandoCode.order) ";
		return createQuery(hql)
				.setLong("id", id)
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
