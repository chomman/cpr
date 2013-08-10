package sk.peterjurkovic.cpr.web.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.exceptions.PageNotFoundEception;
import sk.peterjurkovic.cpr.services.WebpageService;

/**
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Controller
public class PublicCsnTerminologyController {

	public static final String PUBLIC_CSN_TERMINOLOGY_URL = "/terminologicky-slovnik";
	
	@Autowired
	private WebpageService webpageService;
	
	@RequestMapping(PUBLIC_CSN_TERMINOLOGY_URL)
	public String showSearchForm(ModelMap modelMap) throws PageNotFoundEception{
		
		Webpage webpage = webpageService.getWebpageByCode(PUBLIC_CSN_TERMINOLOGY_URL);
		if(webpage == null || !webpage.getEnabled()){
			throw new PageNotFoundEception();
		}
		
		Map<String, Object> model = prepareBaseModel(webpage);
		model.put("subtab", webpage.getId());
		modelMap.put("model", model);
		return "/public/csn/terminology-search";
	}
	
	
	/**
	 * Pripravi zakladny model pre view
	 * 
	 * @param Webpage sekcia systemu
	 * @return pripraveny model
	 */
	private Map<String, Object> prepareBaseModel(Webpage webpage){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webpage", webpage);
		return model;
	}
}
