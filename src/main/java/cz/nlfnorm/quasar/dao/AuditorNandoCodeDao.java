package cz.nlfnorm.quasar.dao;

import java.util.List;

import cz.nlfnorm.dao.BaseDao;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.AuditorNandoCode;

public interface AuditorNandoCodeDao extends BaseDao<AuditorNandoCode, Long>{
	
	List<AuditorNandoCode> getAllAuditorNandoCodes(Auditor auditor);
		
	AuditorNandoCode getByNandoCode(String code, Long auditorId);

	
}
