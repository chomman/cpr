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
	

}
