package sk.peterjurkovic.cpr.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.entities.AssessmentSystem;
import sk.peterjurkovic.cpr.entities.BasicRequirement;
import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.entities.StandardGroup;
import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.exceptions.PageNotFoundEception;
import sk.peterjurkovic.cpr.services.AssessmentSystemService;
import sk.peterjurkovic.cpr.services.BasicRequirementService;
import sk.peterjurkovic.cpr.services.StandardGroupService;
import sk.peterjurkovic.cpr.services.StandardService;
import sk.peterjurkovic.cpr.services.WebpageService;


@Controller
public class PublicCprController {
	
	@Autowired
	private WebpageService webpageService;
	@Autowired
	private StandardService standardService;
	@Autowired
	private BasicRequirementService basicRequirementService;
	@Autowired
	private AssessmentSystemService assessmentSystemService;
	@Autowired
	private StandardGroupService standardGroupService;
	
	public static final String CPR_INDEX_URL = "/cpr";
	
	public static final String CPR_BASIC_REQUREMENT_URL = "/cpr/zakladni-pozadavky-podle-cpr";
	
	public static final String CPR_ASSESSMENT_SYSTEMS_URL = "/cpr/systemy-posudzovani-vlastnosti";
	
	public static final String CPR_GROUPS_URL = "/cpr/skupiny-vyrobku-podle-cpr";
	
	private Logger logger = Logger.getLogger(getClass());
	
	
	/**
	 * Zobrazi zoznam podsekcii CPR
	 * 
	 * @param modelmap
	 * @return
	 * @throws PageNotFoundEception
	 */
	@RequestMapping(CPR_INDEX_URL)
	public String home(ModelMap modelmap) throws PageNotFoundEception {
		
		Webpage webpage = webpageService.getWebpageByCode(CPR_INDEX_URL);
		if(webpage == null || !webpage.getEnabled()){
			throw new PageNotFoundEception();
		}
		Map<String, Object> model = prepareBaseModel(webpage);
		model.put("subtab", webpage.getId());
		modelmap.put("model", model);
		return "/public/cpr/index";
	}
	
	
	
	/**
	 * Zobrazi zoznam zakladnych poziadavkov
	 * 
	 * @param Modelmap model
	 * @return String view
	 * @throws PageNotFoundEception, ak je stranka deaktivovana, alebo sa v zaznav v DB nenachadza
	 */
	@RequestMapping(CPR_BASIC_REQUREMENT_URL)
	public String requirements(ModelMap modelmap) throws PageNotFoundEception {
		
		Webpage webpage = webpageService.getWebpageByCode(CPR_BASIC_REQUREMENT_URL);
		if(webpage == null || !webpage.getEnabled()){
			throw new PageNotFoundEception();
		}
		
		Map<String, Object> model = prepareBaseModel(webpage);
		model.put("subtab", webpage.getId());
		model.put("basicRequremets", basicRequirementService.getBasicRequirementsForPublic());
		modelmap.put("model", model);
		return "/public/cpr/basic-requirement";
	}
	
	
	
	/**
	 * Zobrazi detail zakladneho pozadavku
	 * 
	 * @param String code
	 * @param Modelmap model
	 * @return String view
	 * @throws PageNotFoundEception ak webova sekce neexistuje, alebo je deaktivovana.
	 */
	@RequestMapping("/cpr/br/{code}")
	public String showBasicRequirementDetail(@PathVariable String code, ModelMap modelmap) throws PageNotFoundEception {
		
		BasicRequirement basicRequirement = basicRequirementService.getBasicRequirementByCode(code);
		Webpage webpage = webpageService.getWebpageByCode(CPR_BASIC_REQUREMENT_URL);
		if(basicRequirement == null || webpage == null || !webpage.getEnabled()){
			throw new PageNotFoundEception();
		}
		Map<String, Object> model = prepareBaseModel(webpage);
		model.put("basicRequirement", basicRequirement);
		modelmap.put("model", model);
		return "/public/cpr/basic-requirement-detail";
	}
	
	
	/**
	 * Zobrazi systemy posudzovania zhody.
	 * 
	 * @param ModelMap model
	 * @return String view
	 * @throws PageNotFoundEception, ak je verejna sekce deaktivovana, alebo neexistuje
	 */
	@RequestMapping(CPR_ASSESSMENT_SYSTEMS_URL)
	public String assessmentSystems(ModelMap modelmap) throws PageNotFoundEception {
		
		Webpage webpage = webpageService.getWebpageByCode(CPR_ASSESSMENT_SYSTEMS_URL);
		if(webpage == null || !webpage.getEnabled()){
			throw new PageNotFoundEception();
		}
		Map<String, Object> model = prepareBaseModel(webpage);
		model.put("subtab", webpage.getId());
		model.put("assessmentSystems", assessmentSystemService.getAssessmentSystemsForPublic());
		modelmap.put("model", model);
		return "/public/cpr/assessmentsystems";
	}
	
	
	/**
	 * Zobrazi detail systemu posudzovania zhody
	 * 
	 * @param Long assessmentSystemId
	 * @param Modelmap model
	 * @return String view
	 * @throws PageNotFoundEception, ak je system deaktivovany, alebo neexistuje
	 */
	@RequestMapping("/cpr/as/{assessmentSystemId}")
	public String showAssessmentSystemDetail(@PathVariable Long assessmentSystemId, ModelMap modelmap) throws PageNotFoundEception {
		logger.info("Showing assessment system detail .");
		AssessmentSystem assessmentSystem = assessmentSystemService.getAssessmentSystemById(assessmentSystemId);
		Webpage webpage = webpageService.getWebpageByCode(CPR_ASSESSMENT_SYSTEMS_URL);
		if(assessmentSystem == null || webpage == null){
			throw new PageNotFoundEception();
		}
		Map<String, Object> model = prepareBaseModel(webpage);
		model.put("assessmentSystem", assessmentSystem);
		modelmap.put("model", model);
		return "/public/cpr/assessmentSystem-detail";
	}
	
	
	/**
	 * Zobrazi skupiny vyrobkov
	 * 
	 * @param modelmap
	 * @return String view
	 * @throws PageNotFoundEception, ak je webova sekcia deaktivovana, alebo neexistuje
	 */
	@RequestMapping(CPR_GROUPS_URL)
	public String showCprGroups(ModelMap modelmap) throws PageNotFoundEception {
		Webpage webpage = webpageService.getWebpageByCode(CPR_GROUPS_URL);
		if(webpage == null || !webpage.getEnabled()){
			throw new PageNotFoundEception();
		}
		List<StandardGroup> groups = standardGroupService.getStandardGroupsForPublic();
		logger.info(groups.size());
		Map<String, Object> model = prepareBaseModel(webpage);
		model.put("groups", groups);
		modelmap.put("model", model);
		return "/public/cpr/groups";
	}
	
	
	
	/**
	 * Zobrazi skupiny vyrobkov
	 * 
	 * @param modelmap
	 * @return String view
	 * @throws PageNotFoundEception, ak je webova sekcia deaktivovana, alebo neexistuje
	 */
	@RequestMapping("/cpr/skupina/{groupCode}")
	public String showCprGroupDetail(@PathVariable String groupCode, ModelMap modelmap) throws PageNotFoundEception {
		Webpage webpage = webpageService.getWebpageByCode(CPR_GROUPS_URL);
		StandardGroup group = standardGroupService.getStandardGroupByCode(groupCode);
		if(webpage == null || group == null || !webpage.getEnabled()){
			throw new PageNotFoundEception();
		}
		Map<String, Object> model = prepareBaseModel(webpage);
		model.put("group", group);
		model.put("standards", standardService.getStandardByStandardGroupForPublic(group));
		modelmap.put("model", model);
		return "/public/cpr/group-detail";
	}
	
	

	/**
	 * Zobrazi detail normy
	 * 
	 * @param modelmap
	 * @return String view
	 * @throws PageNotFoundEception, ak je webova sekcia deaktivovana, alebo neexistuje
	 */
	@RequestMapping("/cpr/ehn/{standardCode}")
	public String showStandardDetail(@PathVariable String standardCode, ModelMap modelmap) throws PageNotFoundEception {
		Webpage webpage = webpageService.getWebpageByCode(CPR_GROUPS_URL);
		Standard standard = standardService.getStandardByCode(standardCode);
		if(webpage == null || standard == null || !webpage.getEnabled()){
			throw new PageNotFoundEception();
		}
		Map<String, Object> model = prepareBaseModel(webpage);
		model.put("standard", standard);
		modelmap.put("model", model);
		return "/public/cpr/group-detail";
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
		model.put("parentWebpage", webpageService.getWebpageByCode(CPR_INDEX_URL));
		model.put("tab", 3);
		model.put("submenu", webpageService.getPublicSection(Constants.WEBPAGE_CATEGORY_CPR_SUBMENU));
		return model;
	}
	
}
