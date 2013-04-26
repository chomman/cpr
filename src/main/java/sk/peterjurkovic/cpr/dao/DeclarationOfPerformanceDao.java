package sk.peterjurkovic.cpr.dao;

import java.util.List;
import java.util.Map;

import sk.peterjurkovic.cpr.entities.DeclarationOfPerformance;

/**
 * DAO rozhranie pre manipulaciu s DoP
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
public interface DeclarationOfPerformanceDao  extends BaseDao<DeclarationOfPerformance, Long> {
	
	/**
	 * Vrati DoP na zaklade daneho tokenu
	 * 
	 * @param String textovy retazec (token), daneho DoP
	 * @return
	 */
	DeclarationOfPerformance getByToken(String token);
	
	
	
	/**
	 * Odstrani DoP zo systemu
	 * 
	 * @param Long id
	 */
	void deleteEssentialCharacteristicByDopId(Long id);
	
	
	/**
	 * Vrati stranku (zoznam) DoPs, na zaklade danych kriterii
	 * @param pageNumber
	 * @param criteria
	 * @return List<DeclarationOfPerformace> dops
	 */
	List<DeclarationOfPerformance> getDopPage(int pageNumber, Map<String, Object> criteria);
	
	
	
	/**
	 * Vrati pocet DoPS, vyhovujuci danym kriteriam
	 * @param criteria
	 * @return Long pocet
	 */
	Long getCountOfDop(Map<String, Object> criteria);
	
}
