package cz.nlfnorm.services.impl;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.dao.RegulationDao;
import cz.nlfnorm.entities.Regulation;
import cz.nlfnorm.services.RegulationService;

@Transactional
@Service("regulationService")
public class RegulationServiceImpl implements RegulationService{

	@Autowired
	private RegulationDao regulationDao;
	
	@Override
	@Transactional(readOnly = true)
	public Regulation getById(final Long id) {
		return regulationDao.getByID(id);
	}

	@Override
	public void update(final Regulation regulation) {
		regulationDao.update(regulation);
	}

	@Override
	public void create(Regulation regulation) {
		regulationDao.save(regulation);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Regulation> getAll() {
		return regulationDao.getAll();
	}

	@Override
	public void createOrUpdate(final Regulation regulation) {
		Validate.notNull(regulation);
		if(regulation.getId() == null){
			create(regulation);
		}else{
			update(regulation);
		}
	}

}
