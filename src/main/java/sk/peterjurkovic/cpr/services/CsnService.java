package sk.peterjurkovic.cpr.services;

import java.util.List;

import sk.peterjurkovic.cpr.entities.Csn;

public interface CsnService {
	
	void createCsn(Csn csn);
	
	void updateCsn(Csn csn);
	
	void remove(Csn csn);
	
	List<Csn> getAllCsns();
	
	Csn getCsnById(Long id);
	
	Csn getCsnByCode(String code);
	
	void saveOrUpdate(Csn csn);
}
