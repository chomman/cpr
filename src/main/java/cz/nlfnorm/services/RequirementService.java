package cz.nlfnorm.services;

import java.util.List;

import cz.nlfnorm.entities.Country;
import cz.nlfnorm.entities.Requirement;
import cz.nlfnorm.entities.Standard;

/**
 * Rozhranie pre pracu s poziadavkami
 * 
 * @author peto
 *
 */
public interface RequirementService {
	
	/**
	 * Vyvori novy poziadavok
	 * 
	 * @param requirement
	 */
	void createRequirement(Requirement requirement);
	
	/**
	 * Aktualizuje dany, evidovany poziadavok
	 * 
	 * @param requirement
	 */
	void updateRequirement(Requirement requirement);
	
	
	/**
	 * Odstrani dany poziadavok
	 * 
	 * @param requirement
	 */
	void deleteRequirement(Requirement requirement);
	
	/**
	 * Vrati poziadavok na zaklade daneho identifikatora
	 * 
	 * @param identifikator 
	 * @return poziadavok
	 */
	Requirement getRequirementById(Long id);
	
	/**
	 * Vrati poziadavok na zaklade daneho kodu
	 * 
	 * @param code
	 * @return poziadavok
	 */
	Requirement getRequirementByCode(String code);
	
	/**
	 * Vrati vsetky evidovane poziadavky
	 * 
	 * @return
	 */
	List<Requirement> getAllRequirements();
	
	/**
	 * Vrati poziadavky na zaklade danej krajiny a normy
	 * 
	 * @param krajina
	 * @param norma
	 * @return zoznam najdenych poziadavkov
	 */
	List<Requirement> getRequirementsByCountryAndStandard(Country country, Standard standard);
	
	
	/**
	 * Aktualizuje, alebo vytvori novy poziadavok. V pripade ak je ID daneho poziadavku NULL
	 * vytvori sa novy, inak aktualizuje uz existujuci
	 * 
	 * @param requirement
	 */
	void saveOrUpdateRequirement(Requirement requirement);
	
	

}
