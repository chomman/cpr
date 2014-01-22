package sk.peterjurkovic.cpr.web.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.entities.NotifiedBody;
import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.exceptions.PageNotFoundEception;
import sk.peterjurkovic.cpr.services.NotifiedBodyService;
import sk.peterjurkovic.cpr.services.StandardService;
import sk.peterjurkovic.cpr.services.WebpageService;


@Controller
public class PublicNotifiedBodyController {
	
	@Autowired
	private NotifiedBodyService notifiedBodyService;
	@Autowired
	private WebpageService webpageService;
	@Autowired
	private StandardService standardService;
	
	@Value("#{config['ce.europe.aono']}")
	private String ceEuropeNotifiedBodyDetailUrl;
	
	public static final String NOTIFIE_BODY_URL = "/prehled-subjektu";
	
	
	/**
	 * Zobrazi notifikovane subjekty
	 * 
	 * @param Modelmap model
	 * @return String view
	 * @throws PageNotFoundEception, ak webova sekcia deaktivovana, alebo neexistuje
	 */
	@RequestMapping(NOTIFIE_BODY_URL)
	public String home(ModelMap modelmap) throws PageNotFoundEception {
		
		Webpage webpage = webpageService.getWebpageByCode(NOTIFIE_BODY_URL);
		if(webpage == null){
			throw new PageNotFoundEception();
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webpage", webpage);
		model.put("noaoUrl", ceEuropeNotifiedBodyDetailUrl);
		model.put("tab", webpage.getId());
		model.put("notifiedBodies", notifiedBodyService.getNotifiedBodiesGroupedByCountry(Boolean.TRUE));
		modelmap.put("model", model);
		return "/public/notifiedbodies";
	}
	
	
	/**
	 * Zobrazi detail subjektu
	 * 
	 * @param code, kod subjektu
	 * @param modelmap
	 * @return view
	 * @throws PageNotFoundEception
	 */
	@RequestMapping("/subjekt/{id}")
	public String showBasicRequirementDetail(@PathVariable Long id, ModelMap modelmap) throws PageNotFoundEception {
		
		NotifiedBody notifiedBody = notifiedBodyService.getNotifiedBodyById(id);
		Webpage webpage = webpageService.getWebpageByCode(NOTIFIE_BODY_URL);
		if(notifiedBody == null || webpage == null || !notifiedBody.getEnabled()){
			throw new PageNotFoundEception();
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webpage", webpage);
		model.put("noaoUrl", ceEuropeNotifiedBodyDetailUrl);
		model.put("standards", standardService.getStandardsByNotifiedBody(notifiedBody));
		model.put("tab", webpage.getId());
		model.put("notifiedBody", notifiedBody);
		modelmap.put("model", model);
		return "/public/notifiedbody-detail";
	}
}
