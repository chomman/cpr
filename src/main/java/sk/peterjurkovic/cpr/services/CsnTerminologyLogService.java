package sk.peterjurkovic.cpr.services;

import java.util.Map;

import sk.peterjurkovic.cpr.dto.PageDto;
import sk.peterjurkovic.cpr.entities.CsnTerminologyLog;

public interface CsnTerminologyLogService {
	
	
	void create(CsnTerminologyLog log);
	
	void delete(CsnTerminologyLog log);
	
	void update(CsnTerminologyLog log);
	
	CsnTerminologyLog getById(Long id);
	
	void createWithUser(CsnTerminologyLog log);
	
	PageDto getLogPage(int currentPage, Map<String, Object> criteria);
}
