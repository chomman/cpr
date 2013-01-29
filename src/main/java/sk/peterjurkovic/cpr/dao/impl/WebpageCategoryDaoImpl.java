package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.WebpageCategoryDao;
import sk.peterjurkovic.cpr.entities.WebpageCategory;

@Repository("webpageCategoryDao")
public class WebpageCategoryDaoImpl extends BaseDaoImpl<WebpageCategory, Long> implements WebpageCategoryDao{
	
	public WebpageCategoryDaoImpl(){
		super(WebpageCategory.class);
	}
	
}
