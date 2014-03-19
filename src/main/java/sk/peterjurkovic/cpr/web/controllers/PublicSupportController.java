package sk.peterjurkovic.cpr.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.exceptions.PageNotFoundEception;
import sk.peterjurkovic.cpr.services.WebpageService;
import freemarker.log.Logger;

public class PublicSupportController {
	
	protected Logger logger = Logger.getLogger(getClass().getName());
	
	public final static String EN_PREFIX = "/en/";
	
	@Autowired
	protected WebpageService webpageService;
	
	
	
	public Webpage getWebpage(final String url) throws PageNotFoundEception{
		Webpage webpage = webpageService.getWebpageByCode(url);
		if(webpage == null || !webpage.isEnabled()){
			throw new PageNotFoundEception();
		}
		return webpage;
	}
	
	
}
