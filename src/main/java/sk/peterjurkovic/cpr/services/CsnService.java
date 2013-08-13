package sk.peterjurkovic.cpr.services;

import java.util.List;
import java.util.Map;

import sk.peterjurkovic.cpr.dto.PageDto;
import sk.peterjurkovic.cpr.entities.Csn;



public interface CsnService {

	void createCsn(Csn csn);
	
	
	void updateCsn(Csn csn);
	
	
	void deleteCsn(Csn csn);
	
	
	Csn getById(Long id);
	
	
	Csn getByCode(String code);
	
	
	List<Csn> getAll();
	
	
	void saveOrUpdate(Csn csn);
	
	
	boolean isCsnIdUniqe(Long id, String csnId);
	
	List<Csn> getCsnByTerminology(String terminologyTitle);
	
	PageDto getCsnPage(int pageNumber,Map<String, Object> criteria);
}
