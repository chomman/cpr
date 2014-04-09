package sk.peterjurkovic.cpr.web.controllers.fontend;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;

import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.enums.WebpageType;
import sk.peterjurkovic.cpr.exceptions.PageNotFoundEception;
import sk.peterjurkovic.cpr.services.WebpageService;
import freemarker.log.Logger;

public class WebpageControllerSupport {
	
	
	public final static String EN_PREFIX = "/en/";
	public final static String WEBPAGE_MODEL_KEY = "webpageModel";
	
	protected Logger logger = Logger.getLogger(getClass().getName());

	@Autowired
	protected WebpageService webpageService;
	
	
	public Webpage getWebpage(final Long id) throws PageNotFoundEception{
		return test( webpageService.getWebpageById(id) );
	}
	
	
	public Webpage getWebpage(final String code) throws PageNotFoundEception{
		return test( webpageService.getWebpageByCode(code) );
	}
	
	
	
	private Webpage test(Webpage webpage) throws PageNotFoundEception{
		if(webpage == null || !webpage.isEnabled()){
			throw new PageNotFoundEception();
		}
		return webpage;
	}
	
	
	protected String resolveViewFor(final Webpage webpage) {
		if(webpage.getWebpageModule() != null){
			return "forward:" + webpage.getWebpageModule().getFourwardUrl();
		}
		final WebpageType webpageType = webpage.getWebpageType();
		Validate.notNull(webpageType);
		return "/public/web/" + webpageType.getViewName();
	}
	
}
