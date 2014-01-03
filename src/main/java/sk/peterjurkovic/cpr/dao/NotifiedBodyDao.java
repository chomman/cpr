package sk.peterjurkovic.cpr.dao;

import java.util.List;

import sk.peterjurkovic.cpr.entities.NotifiedBody;

/**
 * Rozhranie datovej vrstvy entity sk.peterjurkovic.cpr.entities.NotifiedBody
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
public interface NotifiedBodyDao extends BaseDao<NotifiedBody, Long> {
	
	/**
	 * Skontroluje, ci moze byt dana notofikovana osoba odstranena zo systemu,
	 * resp. ci nie je pouziva v norme
	 * 
	 * @param notifiedBody
	 * @return TRUE, v pripade ak moze byt dostranena, inak FALSE
	 */
	boolean canBeDeleted(NotifiedBody notifiedBody);
	
	
	/**
	 * Skontroluje, ci je evidovana notifikovana osoba s danym nazvom
	 * 
	 * @param String nazov NO
	 * @param id danej osoby, alebo 0
	 * @return TRUE, ak nie je evidovana, inak FALSE
	 */
	boolean isNameUniqe(String code, Long id); 
	
	
	/**
	 * Vrati zoznam notifikovanych osob zoskupeny podla evidovanych krajin
	 * 
	 * @param Boolean enabled Ak je true, vratia sa len publikovane, resp. false nepublikovane
	 * @return List<NotifiedBody> zoznam notifkovanych osob
	 */
	List<NotifiedBody> getNotifiedBodiesGroupedByCountry(Boolean enabled);
	
	List<NotifiedBody> autocomplete(String term, Boolean enabled);
}
