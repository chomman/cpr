package cz.nlfnorm.quasar.dao;

import java.util.List;
import java.util.Map;

import cz.nlfnorm.dao.BaseDao;
import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.NandoCode;
import cz.nlfnorm.quasar.entities.TrainingLog;

public interface TrainingLogDao extends BaseDao<TrainingLog, Long>{

	PageDto getPage(Map<String, Object> criteria, int pageNumber);
	
	List<Auditor> getAllUnassignedAuditorsToLog(long logId, long userId);
	
	List<Auditor> getAllUnassignedAuditorsToLog(TrainingLog log);
	
	List<NandoCode> getAllUnassignedNandoCodesForLog(TrainingLog log);
}
