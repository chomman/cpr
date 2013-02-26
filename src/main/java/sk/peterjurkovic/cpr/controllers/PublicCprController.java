package sk.peterjurkovic.cpr.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.entities.AssessmentSystem;
import sk.peterjurkovic.cpr.entities.BasicRequirement;
import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.entities.StandardGroup;
import sk.peterjurkovic.cpr.entities.Tag;
import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.enums.StandardOrder;
import sk.peterjurkovic.cpr.exceptions.PageNotFoundEception;
import sk.peterjurkovic.cpr.services.AssessmentSystemService;
import sk.peterjurkovic.cpr.services.BasicRequirementService;
import sk.peterjurkovic.cpr.services.StandardGroupService;
import sk.peterjurkovic.cpr.services.StandardService;
import sk.peterjurkovic.cpr.services.WebpageService;
import sk.peterjurkovic.cpr.utils.RequestUtils;
import sk.peterjurkovic.cpr.web.pagination.PageLink;
import sk.peterjurkovic.cpr.web.pagination.PaginationLinker;


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
	
	public static final String CPR_EHN_SEARCH_URL = "/cpr/vyhledavani-v-normach";
	
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
		return "/public/cpr/cpr-base";
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
		return "/public/cpr/cpr-base";
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
		return "/public/cpr/cpr-base";
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
	
	
	@RequestMapping(value = "/ehn/autocomplete", method = RequestMethod.GET)
	public @ResponseBody List<Standard>  searchInTags(@RequestBody @RequestParam("term") String query){
		return standardService.autocomplateSearch(query, Boolean.TRUE);
	}
	
	
	@RequestMapping(CPR_EHN_SEARCH_URL)
	public String searchInStandard(ModelMap modelMap, HttpServletRequest request) throws PageNotFoundEception{
		Webpage webpage = webpageService.getWebpageByCode(CPR_EHN_SEARCH_URL);
		if(webpage == null || !webpage.getEnabled()){
			throw new PageNotFoundEception();
		}
		Map<String, Object> model = prepareBaseModel(webpage);
		int currentPage = RequestUtils.getPageNumber(request);
		Map<String, Object> params = RequestUtils.getRequestParameterMap(request);
		params.put("enabled", Boolean.TRUE);
		List<PageLink>paginationLinks = getPaginationItems(request, params, currentPage);
		List<Standard> standards = standardService.getStandardPage(currentPage, params);
		model.put("standards", standards);
		model.put("paginationLinks", paginationLinks);
		model.put("params", params);
		model.put("url", CPR_EHN_SEARCH_URL);
		modelMap.put("model", model);
		return "/public/cpr/cpr-base";
	}
	
	
	private  List<PageLink> getPaginationItems(HttpServletRequest request, Map<String, Object> params,int currentPage){
		PaginationLinker paginger = new PaginationLinker(request, params);
		paginger.setUrl(CPR_EHN_SEARCH_URL);
		paginger.setCurrentPage(currentPage);
		paginger.setRowCount( standardService.getCountOfStandards(params).intValue() );
		return paginger.getPageLinks(); 
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
