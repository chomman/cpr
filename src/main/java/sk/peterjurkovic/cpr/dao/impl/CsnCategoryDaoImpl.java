package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.CsnCategoryDao;
import sk.peterjurkovic.cpr.entities.CsnCategory;

@Repository("csnCategoryDao")
public class CsnCategoryDaoImpl  extends BaseDaoImpl<CsnCategory, Long> implements CsnCategoryDao{
	
	public CsnCategoryDaoImpl(){
		super(CsnCategory.class);
	}

}
