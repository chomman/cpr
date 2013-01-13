package sk.peterjurkovic.cpr.services;

import java.util.List;

import sk.peterjurkovic.cpr.entities.StandardCsn;

public interface StandardCsnService {

	void createStandardCsn(StandardCsn standardCsn);
	
	void updateStandardCsn(StandardCsn standardCsn);
	
	void deleteStandardCsn(StandardCsn standardCsn);
	
	StandardCsn getStandardCsnById(Long id);
	
	List<StandardCsn> getAllStandardCsns();
	
	void saveOrUpdate(StandardCsn standardCsn);
	
	
}
