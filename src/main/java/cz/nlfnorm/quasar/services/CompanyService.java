package cz.nlfnorm.quasar.services;

import java.util.List;

import cz.nlfnorm.quasar.entities.Company;

public interface CompanyService {
	
	Company getById(long id);

	List<Company> autocomplete(String term);
	
	void create(Company company);
}
