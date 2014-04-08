package sk.peterjurkovic.cpr.web.controllers.fontend;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.services.ArticleService;
import sk.peterjurkovic.cpr.services.StandardService;
import sk.peterjurkovic.cpr.web.controllers.PublicArticleController;

@Controller
public class HomepageController extends WebpageControllerSupport{
	
	@Autowired
	private ArticleService articleService;
	@Autowired
	private StandardService standardService;
	
	
	@RequestMapping(value = {"/", EN_PREFIX })
	public String showHomepage(ModelMap map){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webpage", webpageService.getHomePage());
		model.put("articleUrl", PublicArticleController.ARTICLE_URL);
		model.put("articles", articleService.getNewestArticles(3));
		model.put("standards", standardService.getLastEditedOrNewestStandards(6, Boolean.TRUE));
		map.put("webpageModel", model);
		return "/public/homepage";
	}
}
