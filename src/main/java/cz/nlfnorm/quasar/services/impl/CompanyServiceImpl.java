package cz.nlfnorm.quasar.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.quasar.dao.CompanyDao;
import cz.nlfnorm.quasar.entities.Company;
import cz.nlfnorm.quasar.services.CompanyService;

@Transactional
@Service("companyService")
public class CompanyServiceImpl implements CompanyService{
	
	@Autowired
	private CompanyDao companyDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Company> autocomplete(final String term) {
		if(StringUtils.isBlank(term)){
			return new ArrayList<>();
		}
		return companyDao.autocomplete(term);
	}

	@Override
	public void create(final Company company) {
		companyDao.save(company);	
	}


	@Override
	@Transactional(readOnly = true)
	public Company getById(Long id) {
		return companyDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Company> getAll() {
		return companyDao.getAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Company findByName(final String name) {
		return companyDao.findByName(name);
	}

}
