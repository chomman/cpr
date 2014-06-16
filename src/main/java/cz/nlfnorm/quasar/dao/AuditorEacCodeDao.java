package cz.nlfnorm.quasar.dao;

import java.util.List;

import cz.nlfnorm.dao.BaseDao;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.AuditorEacCode;
import cz.nlfnorm.quasar.entities.EacCode;

/**
 * QUASAR component
 * Auditors EAC code data object layer
 * 
 * @author Peter Jurkovic
 * @date Jun 16, 2014
 */
public interface AuditorEacCodeDao extends BaseDao<AuditorEacCode, Long>{
	
	List<AuditorEacCode> getAllAuditorEacCodes(Auditor auditor);
	
	List<EacCode> getAllNonAssociatedCodesWith(Auditor auditor);
	
	AuditorEacCode getByEacCode(String code, Long auditorId);
}
