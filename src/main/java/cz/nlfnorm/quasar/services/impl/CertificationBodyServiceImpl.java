package cz.nlfnorm.quasar.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.quasar.dao.CertificationBodyDao;
import cz.nlfnorm.quasar.entities.CertificationBody;
import cz.nlfnorm.quasar.services.CertificationBodyService;

@Transactional
@Service("certificationBodyService")
public class CertificationBodyServiceImpl implements CertificationBodyService {

	@Autowired
	private CertificationBodyDao certificationBodyDao;
	
	@Override
	@Transactional(readOnly = true)
	public CertificationBody getById(final Long id) {
		return certificationBodyDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CertificationBody> autocomplete(final String term) {
		return certificationBodyDao.autocomplete(term);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CertificationBody> getAll() {
		return certificationBodyDao.getAll();
	}

	@Override
	public void create(final CertificationBody certificationBody) {
		certificationBodyDao.save(certificationBody);
	}

	@Override
	@Transactional(readOnly = true)
	public CertificationBody findByName(final String name) {
		return certificationBodyDao.findByName(name);
	}

}
