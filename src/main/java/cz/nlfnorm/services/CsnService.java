package cz.nlfnorm.services;

import java.util.List;
import java.util.Map;

import cz.nlfnorm.dto.CsvImportLogDto;
import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.Csn;
import cz.nlfnorm.entities.CsnTerminology;



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
	
	List<CsnTerminology> getTerminologyByCsnAndLang(Csn csn, String languageCode);
	
	void deleteAllTerminology(Csn csn);
	
	void removeAll();
	
	List<Csn> autocompleteByClassificationSymbol(String term);
	
	List<Csn> autocompleteByCsnId(String term);
	
	Csn getByClassificationSymbol(String cs);
	
	List<Csn> getCsnsByClassificationSymbol(String cs);
	
	CsvImportLogDto saveList(List<Csn> csnList);
	
	Csn getByCatalogId(String catalogId);
	
	
}
