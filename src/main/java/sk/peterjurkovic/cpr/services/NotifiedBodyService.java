package sk.peterjurkovic.cpr.services;

import java.util.List;

import sk.peterjurkovic.cpr.entities.NotifiedBody;

/**
 * Rozhranie pre pracu s notifikovanymi osobami
 * 
 * @author Peter Jurkovic
 *
 */
public interface NotifiedBodyService {
	
	
	/**
	 * Zaeviduje novu notifikovanu osobu do systemu
	 * 
	 * @param notifiedBody
	 */
	void createNotifiedBody(NotifiedBody notifiedBody);
	
	
	/**
	 * Aktualizuje danu notifikovanu osobu
	 * 
	 * @param notifiedBody
	 */
	void updateNotifiedBody(NotifiedBody notifiedBody);
	
	/**
	 * Odstrani zo systemu danu notifikovanu osobu
	 * 
	 * @param notifiedBody
	 */
	void deleteNotifiedBody(NotifiedBody notifiedBody);
	
	
	/**
	 * Vrati notifikovanu osobu na zaklade daneho identifikatora
	 * 
	 * @param id
	 * @return notifikovana osoba
	 */
	NotifiedBody getNotifiedBodyById(Long id);
	
	
	/**
	 * Vrati notifikovanu osobu na zaklade daneho kodu
	 * 
	 * @param code
	 * @return notifikovana osoba
	 */
	NotifiedBody getNotifiedBodyByCode(String code);
	
	
	/**
	 * Vrati zoznam vsetkych evidovanych notifikovanych osob
	 * 
	 * @return zoznam notifikovanu osobu
	 */
	List<NotifiedBody> getAllNotifiedBodies();
	
	
	/**
	 * Aktualizuje, alebo vytvori notifikovanu osobu. V pripade ak je identifikator NULL, jedna sa 
	 * o akciu vytvorenia novej notifikovanej osoby. Inak sa jedna o aktualizaciu
	 * 
	 * @param notifiedBody
	 */
	void saveOrUpdateNotifiedBody(NotifiedBody notifiedBody);
	
	
	/**
	 * Skontroluje, ci dana notifikovana osoba moze byt odstranena zo systemu 
	 * V pripade ak je naviazana na nejake polozky, nemala by byt odstranena.
	 * 
	 * @param notifiedBody
	 * @return TRUE, ak moze byt odstranena 
	 */
	boolean canBeDeleted(NotifiedBody notifiedBody);
	
	/**
	 * Skontroluje ci notifikovana osoba s danym nazvom je uz v systeme evidovana.
	 * 
	 * @param nazov notifikovanej osoby
	 * @param v pripade aktualizacie je potrebny ID, na vyluzenie editovanej osoby
	 * @return TRUE< ak jej nazov je jedinecny, teda moze byt ulozeny
	 */
	boolean isNotifiedBodyNameUniqe(String name, Long id);
	
	
	/**
	 * Vrati zoznam notifikovanych osob danej krajiny
	 * 
	 * @param BOOLEAN, maju sa vratit len notifikovane osoby v publikovane, resp. nepublikovane
	 * @return zoznam notifikovanych osob
	 */
	List<NotifiedBody> getNotifiedBodiesGroupedByCountry(Boolean enabled);
}
