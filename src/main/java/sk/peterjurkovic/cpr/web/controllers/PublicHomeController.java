package sk.peterjurkovic.cpr.web.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.exceptions.PageNotFoundEception;
import sk.peterjurkovic.cpr.services.ArticleService;
import sk.peterjurkovic.cpr.services.StandardService;
import sk.peterjurkovic.cpr.services.WebpageService;

/**
 * Handles requests for the application home page.
 */
//@Controller
public class PublicHomeController {
	
	private final int COUNT_OF_NEWEST_ARTICES_FOR_HOMEPAGE = 3;
	
	private final int COUNT_OF_LAST_EDITED_STANDARDS = 6;
	
	@Autowired
	private WebpageService webpageService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private StandardService standardService;
	
	/**
	 * Zobrazi uvodnu stranku verejnej sekcie IS
	 *  
	 * @param modelmap
	 * @return
	 * @throws PageNotFoundEception
	 */
	//@RequestMapping(value = {"/", "/en/"})
	public String home(ModelMap modelmap) throws PageNotFoundEception {
		
		Webpage webpage = webpageService.getWebpageByCode("/");
		if(webpage == null){
			throw new PageNotFoundEception();
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webpage", webpage);
		model.put("tab", webpage.getId());
		model.put("articleUrl", PublicArticleController.ARTICLE_URL);
		model.put("articles", articleService.getNewestArticles(COUNT_OF_NEWEST_ARTICES_FOR_HOMEPAGE));
		model.put("standards", standardService.getLastEditedOrNewestStandards(COUNT_OF_LAST_EDITED_STANDARDS, Boolean.TRUE));
		modelmap.put("model", model);
		return "/public/homepage";
	}
	
}
