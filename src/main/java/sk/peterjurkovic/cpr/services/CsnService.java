package sk.peterjurkovic.cpr.services;

import java.util.List;

import sk.peterjurkovic.cpr.entities.Csn;



public interface CsnService {

	void createCsn(Csn csn);
	
	
	void updateCsn(Csn csn);
	
	
	void deleteCsn(Csn csn);
	
	
	Csn getById(Long id);
	
	
	Csn getByCode(String code);
	
	
	List<Csn> getAll();
	
	
	void saveOrUpdate(Csn csn);
}
