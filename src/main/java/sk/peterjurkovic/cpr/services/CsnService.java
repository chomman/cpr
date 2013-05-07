package sk.peterjurkovic.cpr.services;

import java.util.List;

import sk.peterjurkovic.cpr.entities.Csn;


/**
 * Rozhranie pre pracu s Ceskou technickou normou
 * 
 * @author Peter Jurkovic
 *
 */
public interface CsnService {

	/**
	 * Vytvori novu CSN
	 * 
	 * @param Csn
	 */
	void createCsn(Csn Csn);
	
	/**
	 * Aktualizuje danu CSN
	 * 
	 * @param Csn
	 */
	void updateCsn(Csn Csn);
	
	
	/**
	 * Odstrani danu CSN
	 * 
	 * @param Csn
	 */
	void deleteCsn(Csn Csn);
	
	
	/**
	 * Vrati CSN na zaklade daneho identifikatora
	 * 
	 * @param id
	 * @return
	 */
	Csn getCsnById(Long id);
	
	
	/**
	 * Vrati evidovanych zoznam CSN
	 * 
	 * @return zoznam CSN
	 */
	List<Csn> getAllCsns();
	
	
	/**
	 * Aktualizuje, alebo vytvori novu CSN, v pripade ak je ID NULL, jedna sa o kaciu
	 * vytvorenia, inak o katualizaciu uz evidovanej polozky
	 * 
	 * @param Csn
	 */
	void saveOrUpdate(Csn Csn);
	
	
}
