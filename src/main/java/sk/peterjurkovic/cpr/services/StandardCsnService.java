package sk.peterjurkovic.cpr.services;

import java.util.List;
import java.util.Map;

import sk.peterjurkovic.cpr.dto.PageDto;
import sk.peterjurkovic.cpr.entities.StandardCsn;


/**
 * Rozhranie pre pracu s Ceskou technickou normou
 * 
 * @author Peter Jurkovic
 *
 */
public interface StandardCsnService {

	/**
	 * Vytvori novu CSN
	 * 
	 * @param StandardCsn
	 */
	void createCsn(StandardCsn StandardCsn);
	
	/**
	 * Aktualizuje danu CSN
	 * 
	 * @param StandardCsn
	 */
	void updateCsn(StandardCsn StandardCsn);
	
	
	/**
	 * Odstrani danu CSN
	 * 
	 * @param StandardCsn
	 */
	void deleteCsn(StandardCsn StandardCsn);
	
	
	/**
	 * Vrati CSN na zaklade daneho identifikatora
	 * 
	 * @param id
	 * @return
	 */
	StandardCsn getCsnById(Long id);
	
	
	/**
	 * Vrati evidovanych zoznam CSN
	 * 
	 * @return zoznam CSN
	 */
	List<StandardCsn> getAllCsns();
	
	
	/**
	 * Aktualizuje, alebo vytvori novu CSN, v pripade ak je ID NULL, jedna sa o kaciu
	 * vytvorenia, inak o katualizaciu uz evidovanej polozky
	 * 
	 * @param StandardCsn
	 */
	void saveOrUpdate(StandardCsn StandardCsn);
	
	
	StandardCsn getByCatalogNo(String catalogNumber);
	
	
	List<StandardCsn> autocomplete(String term);
	
	
	PageDto getPage(int pageNumber, Map<String, Object> criteria);
	
	
	boolean updateReferencedStandard(StandardCsn csn);
	
	
	boolean isStandardCsnUnique(StandardCsn csn);
	
	
	
	

}
