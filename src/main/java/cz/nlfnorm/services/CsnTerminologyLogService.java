package cz.nlfnorm.services;

import java.util.Map;

import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.Csn;
import cz.nlfnorm.entities.CsnTerminologyLog;

public interface CsnTerminologyLogService {
	
	
	void create(CsnTerminologyLog log);
	
	void delete(CsnTerminologyLog log);
	
	void update(CsnTerminologyLog log);
	
	CsnTerminologyLog getById(Long id);
	
	void createWithUser(CsnTerminologyLog log);
	
	PageDto getLogPage(int currentPage, Map<String, Object> criteria);
	
	void removeCsnLogs(Csn csn);
}
