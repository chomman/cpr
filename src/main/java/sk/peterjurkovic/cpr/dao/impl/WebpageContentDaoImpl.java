package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.WebpageContentDao;
import sk.peterjurkovic.cpr.entities.WebpageContent;

/**
 * DAO rozhranie pre manipulaciu so zakladnym obsahom verejnych sekcii
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Repository("webpageContentDao")
public class WebpageContentDaoImpl extends BaseDaoImpl<WebpageContent, Long> implements WebpageContentDao{
	
	
	public WebpageContentDaoImpl(){
		super(WebpageContent.class);
	}
	
}
