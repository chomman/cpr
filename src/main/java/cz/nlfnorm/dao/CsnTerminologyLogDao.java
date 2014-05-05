package cz.nlfnorm.dao;

import java.util.Map;

import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.Csn;
import cz.nlfnorm.entities.CsnTerminologyLog;

public interface CsnTerminologyLogDao extends BaseDao<CsnTerminologyLog, Long> {
	
	
	PageDto getLogPage(int currentPage, Map<String, Object> criteria);

	void removeCsnLogs(Csn csn);
}
