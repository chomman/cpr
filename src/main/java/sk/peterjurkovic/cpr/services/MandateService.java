package sk.peterjurkovic.cpr.services;

import java.util.List;

import sk.peterjurkovic.cpr.entities.Mandate;
import sk.peterjurkovic.cpr.entities.StandardGroup;

/**
 * Rozhranie pre pracu s mandatom
 * 
 * @author peto
 *
 */
public interface MandateService {
	
	/**
	 * Vytvori mandat
	 * 
	 * @param mandate
	 */
	void createMandate(Mandate mandate);
	
	/**
	 * Aktualizuje uz evidovany, dany mandat
	 * 
	 * @param mandate
	 */
	void updateMandate(Mandate mandate);
	
	/**
	 * Odstrani zo systemu dany mandat
	 * 
	 * @param mandate
	 */
	void deleteMandate(Mandate mandate);
	
	/**
	 * Vrati zoznam vsetkych mandatov
	 * 
	 * @return
	 */
	List<Mandate> getAllMandates();
	
	/**
	 * Vrati mandat na zaklade daneho identifikatora
	 * 
	 * @param id
	 * @return
	 */
	Mandate getMandateById(Long id);
	
	/**
	 * Vrati mandat na zaklade kodu
	 * 
	 * @param code
	 * @return mandat
	 */
	Mandate getMandateByCode(String code);
	
	/**
	 * Vrato nastrankovany zoznam mandatov
	 * 
	 * @param pageNumber
	 * @return zoznam mandatov
	 */
	List<Mandate> getMandatePage(int pageNumber);
	
	/**
	 * Vytvori, alebo aktualizuje mandat. V pripade ak je identifikator daneho mandatu
	 * NULL, jedna sa o kaciu vytvorenia noveho mandat, inak o katualizaciu
	 * 
	 * @param mandate
	 */
	void saveOrUpdateMandate(Mandate mandate);
	
	
	/**
	 * Vrati pocete evidovanych mandatov
	 * 
	 * @return pocet evidovanych mandatov v systeme
	 */
	Long getCountOfMandates();
	
	/**
	 * Skontroluje, ci dany mandat moze byt odstraneny zo systemu, resp.
	 * zisti ci nie je naviazany na nejake polozky
	 * 
	 * @param mandate
	 * @return TURE, v pripade ak moze byt odstraneny
	 */
	boolean canBeDeleted(final Mandate mandate);
	
	
	/**
	 * Vrati vsetky mandaty, okrem mandatov pripradenych k danej skupine
	 * 
	 * @param standardGroup
	 * @return 
	 */
	List<Mandate> getFiltredMandates(StandardGroup standardGroup);
}
