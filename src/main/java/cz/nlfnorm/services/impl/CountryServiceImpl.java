package cz.nlfnorm.services.impl;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.dao.CountryDao;
import cz.nlfnorm.entities.Country;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.services.CountryService;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.utils.UserUtils;


@Service("countryService")
@Transactional(propagation = Propagation.REQUIRED)
public class CountryServiceImpl implements CountryService {
	
	@Autowired
	private CountryDao countryDao;
	@Autowired
	private UserService userService;
	
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

	@Override
	public void saveOrUpdateCountry(Country country) {
		User user = userService.getUserByUsername(UserUtils.getLoggedUser().getUsername());
		
		if(country.getId() == null){
			country.setCreatedBy(user);
			country.setCreated(new LocalDateTime());
			countryDao.save(country);
		}else{
			country.setChangedBy(user);
			country.setChanged(new LocalDateTime());
			countryDao.update(country);
		}
	}

	@Override
	public Country getById(final Long id) {
		return getCountryById(id);
	}

}
