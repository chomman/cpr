package cz.nlfnorm.dao;

import java.util.List;

import cz.nlfnorm.entities.Mandate;


/**
 * Rozhranie datevej vrstvy entity sk.peterjurkovic.cpr.entities.Mandate
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
public interface MandateDao extends BaseDao<Mandate, Long> {
	
	/**
	 * Vrati stranku mandatov
	 * 
	 * @param Long cislo stranky
	 * @return List<Mandate>
	 */
	List<Mandate> getMandatePage(int pageNumber);
	
	/**
	 * Vrati celkovy pocet evidovanych mandatov.
	 * 
	 * @return Long 
	 */
	Long getCountOfMandates();
	
	
	/**
	 * Skontroluje, ci moze byt dany mandat odstraneny, resp.
	 * ci sa nenachadza v norme.
	 * 
	 * @param Mandate dany mandat
	 * @return TRUE, ak moze byt odstraneny, inak FALSE
	 */
	boolean canBeDeleted(final Mandate mandate);
}
