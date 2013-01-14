package sk.peterjurkovic.cpr.services;

import java.util.List;

import sk.peterjurkovic.cpr.entities.Csn;

public interface CsnService {

	void createCsn(Csn Csn);
	
	void updateCsn(Csn Csn);
	
	void deleteCsn(Csn Csn);
	
	Csn getCsnById(Long id);
	
	List<Csn> getAllCsns();
	
	void saveOrUpdate(Csn Csn);
	
	
}
