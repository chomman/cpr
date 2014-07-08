package cz.nlfnorm.quasar.dao;

import java.util.List;

import cz.nlfnorm.dao.BaseDao;
import cz.nlfnorm.quasar.entities.Company;

public interface CompanyDao extends BaseDao<Company, Long>{
	
	List<Company> autocomplete(String term);
	
	List<Company> getAll();
	
}
