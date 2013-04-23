package sk.peterjurkovic.cpr.web.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.exceptions.PageNotFoundEception;
import sk.peterjurkovic.cpr.services.WebpageService;

@Controller
public class PublicBaseController {
	
	@Autowired
	private WebpageService webpageService;

	
	@RequestMapping(Constants.DEFAULT_WEBPAGE_URL_PREFIX + "{sectionCode}")
	public String baseSection(@PathVariable String sectionCode, ModelMap map) throws PageNotFoundEception{
		
		Webpage webpage = webpageService.getWebpageByCode(Constants.DEFAULT_WEBPAGE_URL_PREFIX +sectionCode);
		
		if(webpage == null || !webpage.getEnabled()){
			throw new PageNotFoundEception();
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webpage", webpage);
		if(webpage.getWebpageCategory().getId() == Constants.WEBPAGE_CATEGORY_CPR_SUBMENU){
			model.put("parentWebpage", webpageService.getWebpageByCode(PublicCprController.CPR_INDEX_URL));
			model.put("tab", 3);
			model.put("submenu", webpageService.getPublicSection(Constants.WEBPAGE_CATEGORY_CPR_SUBMENU));
			map.put("model", model);
			return "/public/cpr/cpr-base";
		}else{
			model.put("tab", webpage.getId());
			map.put("model", model);
			return "/public/base-view";
		}
	}
	
}
