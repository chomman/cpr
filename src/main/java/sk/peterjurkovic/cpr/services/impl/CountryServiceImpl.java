package sk.peterjurkovic.cpr.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.CountryDao;
import sk.peterjurkovic.cpr.entities.Country;
import sk.peterjurkovic.cpr.services.CountryService;


@Service("countryService")
@Transactional(propagation = Propagation.REQUIRED)
public class CountryServiceImpl implements CountryService {
	
	@Autowired
	private CountryDao countryDao;
	
	@Override
	public void createCountry(Country country) {
		countryDao.save(country);
	}

	@Override
	public void updateCountry(Country country) {
		countryDao.update(country);
	}

	@Override
	public void deleteCountry(Country country) {
		countryDao.remove(country);
	}

	@Override
	@Transactional(readOnly = true)
	public Country getCountryById(Long id) {
		return countryDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Country> getAllCountries() {
		return countryDao.getAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Country getCountryByCode(String code) {
		return countryDao.getByCode(code);
	}

}
