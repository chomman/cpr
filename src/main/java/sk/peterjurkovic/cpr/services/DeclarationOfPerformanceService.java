package sk.peterjurkovic.cpr.services;

import java.util.List;
import java.util.Map;

import sk.peterjurkovic.cpr.entities.DeclarationOfPerformance;

/**
 * Rozhranie pre pracu s DoP
 * 
 * @author Peter Jurkovic
 *
 */
public interface DeclarationOfPerformanceService {
	
	/**
	 * Vytvori nove DeclarationOfPerformace
	 * 
	 * @param DeclarationOfPerformace
	 */
	void createDoP(DeclarationOfPerformance dop);
	
	/**
	 * Aktualizuje uz evidovane DeclarationOfPerformace
	 * 
	 * @param DeclarationOfPerformace
	 */
	void updateDop(DeclarationOfPerformance dop);
	
	
	/**
	 * Nenavratne odstrani evidovane DeclarationOfPerformace zo systemu
	 * 
	 * @param DeclarationOfPerformace
	 */
	void deleteDop(DeclarationOfPerformance dop);
	
	/**
	 * Vrati DeclarationOfPerformace na zkalade daneho identifikatora DoP
	 * 
	 * @param identifikator DeclarationOfPerformace, ktore ma byt vratene
	 * @return DeclarationOfPerformace
	 */
	DeclarationOfPerformance getDopById(Long id);
	
	/**
	 * Vrati DeclarationOfPerformace na zaklade kodu
	 * 
	 * @param kod DeclarationOfPerformace
	 * @return DeclarationOfPerformace
	 */
	DeclarationOfPerformance getDoPByCode(String code);
	
	
	/**
	 *  Vrati vsetky evidovane DeclarationOfPerformace
	 * @return zoznam DeclarationOfPerformace
	 */
	List<DeclarationOfPerformance> getAll();
	
	
	/**
	 * Vrati DeclarationOfPerformace na zaklade daneho tokenu
	 * 
	 * @param token
	 * @return DeclarationOfPerformace
	 */
	DeclarationOfPerformance getByToken(String token);
	
	
	/**
	 * Odstrani zo systemu DeclarationOfPerformace na zaklade daneho identifikatora
	 * @param id
	 */
	void deleteEssentialCharacteristicByDopId(Long id);
	
	/**
	 * Vrati pocet evidovancy DeclarationOfPerformace, vyhovujucim danym kriteriam
	 * 
	 * @param criteria
	 * @return Pocet DeclarationOfPerformace vyhovujuci danym kriteriam
	 */
	Long getCountOfDop(final Map<String, Object> criteria);
	
	
	/**	
	 * Vrati stranku evidovanych DeclarationOfPerformace, vyhovujucim danym kriteriam.
	 * 
	 * @param pageNumber, cislo stranky
	 * @param criteria
	 * @return nastrankovany zoznam DeclarationOfPerformace
	 */
	List<DeclarationOfPerformance> getDopPage(int pageNumber,Map<String, Object> criteria);
}
