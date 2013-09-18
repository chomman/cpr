package sk.peterjurkovic.cpr.services;

import sk.peterjurkovic.cpr.entities.CsnTerminologyLog;

public interface CsnTerminologyLogService {
	
	
	public void create(CsnTerminologyLog log);
	
	public void delete(CsnTerminologyLog log);
	
	public void update(CsnTerminologyLog log);
	
	public CsnTerminologyLog getById(Long id);
	
	public void createWithUser(CsnTerminologyLog log);
}
