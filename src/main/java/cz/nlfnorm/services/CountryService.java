package cz.nlfnorm.services;

import java.util.List;

import cz.nlfnorm.entities.Country;

/**
 * Rozhranie pre pracu s censkou krajinou EU
 * 
 * @author peter.jurkovic
 *
 */
public interface CountryService extends IdentifiableByLongService<Country>{
	
	
	/**
	 * Vytvori danu clensku krajinu
	 * 
	 * @param country
	 */
	void createCountry(Country country);
	
	/**
	 * Aktualizuje danu clensku krajinu
	 * 
	 * @param country
	 */
	void updateCountry(Country country);
	
	
	/**
	 * Odstrani danu celsnku krajinu
	 * 
	 * @param country
	 */
	void deleteCountry(Country country);
	
	
	/**
	 * Vrati clensku krajinu na zaklade danoho identifikatora
	 * 
	 * 
	 * @param identifikator, danej clenskej krajiny
	 * @return
	 */
	Country getCountryById(Long id);
	
	/**
	 * Vrati vsetky evidovane clenske krajiny
	 * 
	 * @return zoznam celnskych krajin
	 */
	List<Country> getAllCountries();
	
	
	/**
	 * Vrati celnsku krajinu na zaklade daneho ID
	 * 
	 * @param code
	 * @return
	 */
	Country getCountryByCode(String code);
	
	void saveOrUpdate(Country country);
}
