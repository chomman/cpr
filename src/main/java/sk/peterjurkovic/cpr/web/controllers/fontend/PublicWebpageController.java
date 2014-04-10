package sk.peterjurkovic.cpr.web.controllers.fontend;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.exceptions.PageNotFoundEception;
import sk.peterjurkovic.cpr.services.ArticleService;
import sk.peterjurkovic.cpr.services.StandardService;

@Controller
public class PublicWebpageController extends WebpageControllerSupport {
		
	@Autowired
	private ArticleService articleService;
	@Autowired
	private StandardService standardService;
	
	
	@RequestMapping(value = {"/", EN_PREFIX })
	public String handleHomepage(ModelMap map){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webpage", webpageService.getHomePage());
		model.put("articleUrl", "/article/");
		model.put("articles", articleService.getNewestArticles(3));
		model.put("standards", standardService.getLastEditedOrNewestStandards(6, Boolean.TRUE));
		map.put( WEBPAGE_MODEL_KEY , model);
		return "/public/homepage";
	}
		
	
	@RequestMapping( value = { "/{code}" , EN_PREFIX + "{code}"} )
	public String handleFirstLevel(@PathVariable String code, ModelMap modelMap) throws PageNotFoundEception{
		Webpage webpage = getWebpage(code);
		modelMap.put(WEBPAGE_MODEL_KEY, prepareModel(webpage));
		return resolveViewFor(webpage);
	}
	
	
	
	@RequestMapping( value = { "/{parentCode}/{id}/*" , EN_PREFIX + "{parentCode}/{id}/*"} )
	public String handleChildPages(@PathVariable Long id, ModelMap modelMap) throws PageNotFoundEception{
		Webpage webpage = getWebpage(id);
		modelMap.put(WEBPAGE_MODEL_KEY, prepareModel(webpage));
		return resolveViewFor(webpage);
	}
	
	
	private Map<String, Object> prepareModel(Webpage webpage){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webpage", webpage);
		return model;
	}
}