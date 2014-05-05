package cz.nlfnorm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.dao.StandardCsnChangeDao;
import cz.nlfnorm.entities.StandardCsnChange;
import cz.nlfnorm.services.StandardCsnChangeService;

@Service("standardCsnChangeService")
@Transactional(propagation = Propagation.REQUIRED)
public class StandardCsnChangeServiceImpl implements StandardCsnChangeService{
	
	
	@Autowired
	private StandardCsnChangeDao standardCsnChangeDao;
	
	
	@Override
	public void delete(StandardCsnChange csn) {
		standardCsnChangeDao.refresh(csn);
	}


	@Override
	@Transactional(readOnly = true)
	public StandardCsnChange getById(Long id) {
		return standardCsnChangeDao.getByID(id);
		
	}
	
}
