package cz.nlfnorm.quasar.dao;

import java.util.List;

import cz.nlfnorm.dao.BaseDao;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.EacCode;

/**
 * DAO interface for EAC code of QUASAR
 * 
 * @author Peter Jurkovic
 * @date Jun 11, 2014
 */
public interface EacCodeDao extends BaseDao<EacCode, Long>{

	List<EacCode> getAllForQsAuditor();

	List<EacCode> getAllNonAssociatedAuditorsEacCodes(Auditor auditor);
}
