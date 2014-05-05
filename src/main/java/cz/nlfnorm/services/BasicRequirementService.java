package cz.nlfnorm.services;

import java.util.List;

import cz.nlfnorm.entities.BasicRequirement;

/**
 * Rozhranie pre pracu so zakladnym poziadavkom podla CPR
 * 
 * @author Peter Jurkovic
 *
 */
public interface  BasicRequirementService {
	
	/**
	 * Vytvori novy zkladny poziadovok podla CPR
	 * 
	 * @param basicRequirement
	 */
	void createBasicRequirement(BasicRequirement basicRequirement);
	
	
	/**
	 * 
	 * Aktualizuje zkladny poziadovok podla CPR
	 * 
	 * @param basicRequirement
	 */
	void updateBasicRequirement(BasicRequirement basicRequirement);
	
	
	/**
	 * Odstrani zkladny poziadovok podla CPR
	 * 
	 * @param basicRequirement
	 */
	void deleteBasicRequirement(BasicRequirement basicRequirement);
	
	
	/**
	 * 
	 * Vrati zkladny poziadovok podla CPR na zaklade daneho identifikatora
	 * 
	 * @param identifikator daneho zkladneho poziadovku podla CPR
	 * @return zakladny poziadavok
	 */
	BasicRequirement getBasicRequirementById(Long id);
	
	
	/**
	 * Vrati zoznam zakladnych poziadaviek podla CPR
	 * 
	 * @return zoznam zakladnych poziadavkov
	 */
	List<BasicRequirement> getAllBasicRequirements();
	
	/**
	 * Vrati zoznam zakladnych poziadaviek podla CPR na zaklade kodu
	 * 
	 * @return zakladny poziadavok
	 */
	BasicRequirement getBasicRequirementByCode(String code);
	
	
	
	/**
	 * Vytvori, alebo aktualizuje zakladny poziadavok podla CPR.
	 * V pripade, ak je identifikator NULL, bude vytvoreny onvy, inak sa jedna o kaciu
	 * aktualizacie
	 * 
	 * @param basicRequirement
	 */
	void saveOrUpdateBasicRequirement(BasicRequirement basicRequirement);
	
	
	/**
	 * Skontroluje, ci je zakladny poziadavok v systeme jedinecny, resp. jeho kod.
	 * 
	 * @param Nazov kod zakladne poziadavku
	 * @param identifikator
	 * @return TRUE, v pripade ak sa je jedinecny, inak FALSE
	 */
	boolean isBasicRequirementNameUniqe(final String code,final Long id);
	
	
	
	/**
	 * Vrati zoznam publikovanych zkladnych poziadavkov podla CPR
	 * 
	 * @return zoznam zakladnych poziadavkov
	 */
	List<BasicRequirement> getBasicRequirementsForPublic();
}
