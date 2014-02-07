package sk.peterjurkovic.cpr.dao;

import java.util.Map;

import sk.peterjurkovic.cpr.dto.PageDto;
import sk.peterjurkovic.cpr.entities.Csn;
import sk.peterjurkovic.cpr.entities.CsnTerminologyLog;

public interface CsnTerminologyLogDao extends BaseDao<CsnTerminologyLog, Long> {
	
	
	PageDto getLogPage(int currentPage, Map<String, Object> criteria);

	void removeCsnLogs(Csn csn);
}
