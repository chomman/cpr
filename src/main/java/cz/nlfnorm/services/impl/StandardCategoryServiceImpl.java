package cz.nlfnorm.services.impl;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.dao.StandardCategoryDao;
import cz.nlfnorm.entities.Regulation;
import cz.nlfnorm.entities.StandardCategory;
import cz.nlfnorm.services.StandardCategoryService;

@Transactional
@Service("standardCategoryService")
public class StandardCategoryServiceImpl implements StandardCategoryService{

	@Autowired
	private StandardCategoryDao standardCategoryDao;
	
	@Override
	public void create(final StandardCategory standardCategory) {
		standardCategoryDao.save(standardCategory);
	}

	@Override
	public void update(final StandardCategory standardCategory) {
		standardCategoryDao.update(standardCategory);
	}

	@Override
	@Transactional(readOnly = true)
	public List<StandardCategory> getAll() {
		return standardCategoryDao.getAll();
	}

	@Override
	@Transactional(readOnly = true)
	public StandardCategory getById(Long id) {
		return standardCategoryDao.getByID(id);
	}
	
	public void createOrUpdate(final StandardCategory standardCategory){
		Validate.notNull(standardCategory);
		if(standardCategory.getId() == null){
			create(standardCategory);
		}else{
			update(standardCategory);
		}
	}

	@Override
	public List<Regulation> getAllUnassignedRegulationFor(final StandardCategory standardCategory) {
		return standardCategoryDao.getAllUnassignedRegulationFor(standardCategory);
	}

}
