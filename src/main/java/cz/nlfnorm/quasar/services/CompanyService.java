package cz.nlfnorm.quasar.services;

import java.util.List;

import cz.nlfnorm.quasar.entities.Company;
import cz.nlfnorm.services.IdentifiableByLongService;

public interface CompanyService extends IdentifiableByLongService<Company>{
	
	List<Company> autocomplete(String term);
	
	List<Company> getAll();
	
	void create(Company company);
	
	Company findByName(String name);
}
