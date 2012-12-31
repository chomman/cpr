package sk.peterjurkovic.cpr.services;

import java.util.List;

import sk.peterjurkovic.cpr.entities.Country;


public interface CountryService {
	
	void createCountry(Country country);
	
	void updateCountry(Country country);
	
	void deleteCountry(Country country);
	
	Country getCountryById(Long id);
	
	List<Country> getAllCountries();
	
	Country getCountryByCode(String code);
}
