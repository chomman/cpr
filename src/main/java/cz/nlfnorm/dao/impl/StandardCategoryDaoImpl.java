package cz.nlfnorm.dao.impl;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.StandardCategoryDao;
import cz.nlfnorm.entities.StandardCategory;

@Repository("standardCategoryDao")
public class StandardCategoryDaoImpl extends BaseDaoImpl<StandardCategory, Long> implements StandardCategoryDao{
	
	public StandardCategoryDaoImpl(){
		super(StandardCategory.class);
	}

}
