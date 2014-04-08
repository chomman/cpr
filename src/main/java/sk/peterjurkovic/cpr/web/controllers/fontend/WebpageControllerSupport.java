package sk.peterjurkovic.cpr.web.controllers.fontend;

import org.springframework.beans.factory.annotation.Autowired;

import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.exceptions.PageNotFoundEception;
import sk.peterjurkovic.cpr.services.WebpageService;
import freemarker.log.Logger;

public class WebpageControllerSupport {
	
	public final static String EN_PREFIX = "/en/";
	protected Logger logger = Logger.getLogger(getClass().getName());

	@Autowired
	protected WebpageService webpageService;
	
	public Webpage getWebpage(final Long id) throws PageNotFoundEception{
		Webpage webpage = webpageService.getWebpageById(id);
		if(webpage == null || !webpage.isEnabled()){
			throw new PageNotFoundEception();
		}
		return webpage;
	}
	
	
}
