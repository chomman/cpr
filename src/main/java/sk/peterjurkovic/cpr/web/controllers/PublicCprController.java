package sk.peterjurkovic.cpr.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.constants.Filter;
import sk.peterjurkovic.cpr.entities.AssessmentSystem;
import sk.peterjurkovic.cpr.entities.BasicRequirement;
import sk.peterjurkovic.cpr.entities.NotifiedBody;
import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.entities.StandardGroup;
import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.enums.StandardOrder;
import sk.peterjurkovic.cpr.enums.StandardStatus;
import sk.peterjurkovic.cpr.exceptions.PageNotFoundEception;
import sk.peterjurkovic.cpr.services.AssessmentSystemService;
import sk.peterjurkovic.cpr.services.BasicRequirementService;
import sk.peterjurkovic.cpr.services.NotifiedBodyService;
import sk.peterjurkovic.cpr.services.StandardGroupService;
import sk.peterjurkovic.cpr.services.StandardService;
import sk.peterjurkovic.cpr.services.WebpageService;
import sk.peterjurkovic.cpr.utils.ParseUtils;
import sk.peterjurkovic.cpr.utils.RequestUtils;
import sk.peterjurkovic.cpr.web.editors.LocalDateEditor;
import sk.peterjurkovic.cpr.web.editors.StandardGroupEditor;
import sk.peterjurkovic.cpr.web.editors.StandardPropertyEditor;


@Controller
public class PublicCprController extends PublicSupportController{
	
	private static final String CPR_INDEX_URL = "/cpr";
	public static final String STANDARDS_URL = "/harmonizovane-normy";
	private static final String EHN_DETAIL = "/detail-normy";
	private static final String STANDARD_GROUP_URL = "/cpr/skupiny-vyrobku";
	private static final String STANDARD_GROUP_DETAIL_URL = "/cpr/skupina/{code}";
	private static final String CPR_BASIC_REQUREMENT_URL = "/cpr/zakladni-pozadavky-podle-cpr";
	private static final String CPR_ASSESSMENT_SYSTEMS_URL = "/cpr/systemy-posudzovani-vlastnosti";
    private static final String INDEX_URL = "/normy";
    
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
	@Autowired
	private NotifiedBodyService notifiedBodyService;
	
	// editors
	@Autowired
	private StandardGroupEditor standardGroupEditor;
	@Autowired
	private LocalDateEditor localDateEditor;
	@Autowired
	private StandardPropertyEditor standardPropertyEditor;
	

	
	@Value("#{config['nandourl']}")
	private String ceEuropeNotifiedBodyDetailUrl;
	
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(StandardGroup.class, this.standardGroupEditor);
		binder.registerCustomEditor(LocalDate.class, this.localDateEditor);
		binder.registerCustomEditor(Standard.class, this.standardPropertyEditor);
    }
	
	
	
	@RequestMapping(value = { CPR_INDEX_URL , EN_PREFIX + CPR_INDEX_URL })
	public String cprIndex(ModelMap modelmap) throws PageNotFoundEception{
		final Webpage webpage = getWebpage(CPR_INDEX_URL);
		Map<String, Object> model = prepareBaseModel(webpage);
		modelmap.put("model", model);
		return "/public/cpr/index";
	}
		
	/**
	 * Zobrazi zoznam podsekcii CPR
	 * 
	 * @param modelmap
	 * @return
	 * @throws PageNotFoundEception
	 */
	@RequestMapping( value = { STANDARDS_URL ,  EN_PREFIX + STANDARDS_URL } )
	public String home(ModelMap modelmap, HttpServletRequest request) throws PageNotFoundEception {
		final Webpage webpage = getWebpage(STANDARDS_URL);
		Map<String, Object> model = prepareBaseModel(webpage);
		Map<String, Object> params = RequestUtils.getRequestParameterMap(request);
		final int currentPage = RequestUtils.getPageNumber(request);
		params.put(Filter.ENABLED, Boolean.TRUE);
		List<Standard> standards = standardService.getStandardPage(currentPage, params, Constants.PUBLIC_STANDARD_PAGE_SIZE);
		params.put(Filter.NOTIFIED_BODY, getNotifiedBody(params.get(Filter.NOTIFIED_BODY)));
		model.put("standards", standards);
		model.put("params", params);
		model.put("strParams", RequestUtils.getRequestParams(request, Constants.PAGE_PARAM_NAME));
		model.put("parentWebpage", webpageService.getWebpageByCode(CPR_INDEX_URL));
		model.put("orders", StandardOrder.getAll());
		model.put("standardStatuses", StandardStatus.getAll());
		model.put("standardGroups", standardGroupService.getStandardGroupsForPublic());
		model.put("webpage", webpage);
		modelmap.put("model", model);
		return "/public/cpr/harmonized-standards";
	}
	
	@RequestMapping(value = { "/async/standards" , EN_PREFIX + "async/standards" })
	public ModelAndView   standards(HttpServletRequest request, ModelMap map){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> params = RequestUtils.getRequestParameterMap(request);
		final int currentPage = RequestUtils.getPageNumber(request);
		params.put(Filter.ENABLED, Boolean.TRUE);
		model.put("standards", standardService.getStandardPage(currentPage, params, Constants.PUBLIC_STANDARD_PAGE_SIZE));
		model.put("async", true);
		map.put("model", model);
		return new ModelAndView("/public/cpr/include/standard-table",map);
	}
	
	/**
     * Zobrazi detail zakladneho pozadavku
     *
     * @param String code
     * @param Modelmap model
     * @return String view
     * @throws PageNotFoundEception ak webova sekce neexistuje, alebo je deaktivovana.
     */
    @RequestMapping(value = {"/cpr/br/{code}" , EN_PREFIX + "cpr/br/{code}" })
    public String showBasicRequirementDetail(@PathVariable String code, ModelMap modelmap) throws PageNotFoundEception {
            
            BasicRequirement basicRequirement = basicRequirementService.getBasicRequirementByCode(code);
            Webpage webpage = webpageService.getWebpageByCode(CPR_BASIC_REQUREMENT_URL);
            if(basicRequirement == null || webpage == null || !webpage.getEnabled()){
                    throw new PageNotFoundEception();
            }
            Map<String, Object> model = prepareBaseModel(webpage);
            model.put("basicRequirement", basicRequirement);
            model.put("parentWebpage", webpageService.getWebpageByCode(CPR_INDEX_URL));
            modelmap.put("model", model);
            return "/public/cpr/basic-requirement-detail";
    }
    
    
    /**
     * Zobrazi zoznam zakladnych poziadavkov
     *
     * @param Modelmap model
     * @return String view
     * @throws PageNotFoundEception, ak je stranka deaktivovana, alebo sa v zaznav v DB nenachadza
     */
    @RequestMapping(value = {CPR_BASIC_REQUREMENT_URL, EN_PREFIX + CPR_BASIC_REQUREMENT_URL})
    public String requirements(ModelMap modelmap) throws PageNotFoundEception {
            
            Webpage webpage = webpageService.getWebpageByCode(CPR_BASIC_REQUREMENT_URL);
            if(webpage == null || !webpage.getEnabled()){
                    throw new PageNotFoundEception();
            }
            
            Map<String, Object> model = prepareBaseModel(webpage);
            model.put("subtab", webpage.getId());
            model.put("parentWebpage", webpageService.getWebpageByCode(CPR_INDEX_URL));
            model.put("basicRequremets", basicRequirementService.getBasicRequirementsForPublic());
            modelmap.put("model", model);
            return "/public/cpr/cpr-base";
    }
    
    /**
     * Zobrazi systemy posudzovania zhody.
     *
     * @param ModelMap model
     * @return String view
     * @throws PageNotFoundEception, ak je verejna sekce deaktivovana, alebo neexistuje
     */
    @RequestMapping(value = {CPR_ASSESSMENT_SYSTEMS_URL, EN_PREFIX + CPR_ASSESSMENT_SYSTEMS_URL})
    public String assessmentSystems(ModelMap modelmap) throws PageNotFoundEception {
            
            Webpage webpage = webpageService.getWebpageByCode(CPR_ASSESSMENT_SYSTEMS_URL);
            if(webpage == null || !webpage.getEnabled()){
                    throw new PageNotFoundEception();
            }
            Map<String, Object> model = prepareBaseModel(webpage);
            model.put("subtab", webpage.getId());
            model.put("parentWebpage", webpageService.getWebpageByCode(CPR_INDEX_URL));
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
    @RequestMapping(value = {"/cpr/as/{assessmentSystemId}", EN_PREFIX + "cpr/as/{assessmentSystemId}"})
    public String showAssessmentSystemDetail(@PathVariable Long assessmentSystemId, ModelMap modelmap) throws PageNotFoundEception {
            AssessmentSystem assessmentSystem = assessmentSystemService.getAssessmentSystemById(assessmentSystemId);
            Webpage webpage = webpageService.getWebpageByCode(CPR_ASSESSMENT_SYSTEMS_URL);
            if(assessmentSystem == null || webpage == null){
                    throw new PageNotFoundEception();
            }
            Map<String, Object> model = prepareBaseModel(webpage);
            model.put("assessmentSystem", assessmentSystem);
            model.put("parentWebpage", webpageService.getWebpageByCode(CPR_INDEX_URL));
            modelmap.put("model", model);
            return "/public/cpr/assessmentSystem-detail";
    }
    
    

	
	@RequestMapping(value = { INDEX_URL , EN_PREFIX + INDEX_URL })
	public String index(ModelMap modelmap) throws PageNotFoundEception{
		final Webpage webpage = getWebpage(INDEX_URL);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webpage", webpage);
		model.put("tab", 3);
		model.put("submenu", webpageService.getPublicSection(Constants.WEBPAGE_CATEGORY_EHN_SUBMENU));
		modelmap.put("model", model);
		return "/public/cpr/index";
	}
	
    
	private NotifiedBody getNotifiedBody(final Object id){
		Long nbid = ParseUtils.parseLongFromStringObject(id);
		if(nbid != null && nbid != 0){
			return notifiedBodyService.getNotifiedBodyById(nbid);
		}
		return null;
	}
	
	
	/**
	 * Zobrazi skupiny vyrobkov
	 * 
	 * @param modelmap
	 * @return String view
	 * @throws PageNotFoundEception, ak je webova sekcia deaktivovana, alebo neexistuje
	 */
	@RequestMapping(value =  {STANDARD_GROUP_URL, EN_PREFIX + STANDARD_GROUP_URL})
	public String showCprGroups(ModelMap modelmap) throws PageNotFoundEception {
		Webpage webpage = getWebpage(STANDARD_GROUP_URL);
		List<StandardGroup> groups = standardGroupService.getStandardGroupsForPublic();
		Map<String, Object> model = prepareBaseModel(webpage);
		model.put("standardGroups", groups);
		model.put("parentWebpage", webpageService.getWebpageByCode(CPR_INDEX_URL));
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
	@RequestMapping(value = {STANDARD_GROUP_DETAIL_URL, EN_PREFIX + STANDARD_GROUP_DETAIL_URL})
	public String showCprGroupDetail(@PathVariable String code, ModelMap modelmap) throws PageNotFoundEception {
		Webpage webpage = webpageService.getWebpageByCode(STANDARD_GROUP_URL);
		StandardGroup stadnardGroup = standardGroupService.getStandardGroupByCode(code);
		if(stadnardGroup == null){
			throw new PageNotFoundEception();
		}
		Map<String, Object> model = prepareBaseModel(webpage);
		model.put("group", stadnardGroup);
		model.put("parentWebpage", webpageService.getWebpageByCode(CPR_INDEX_URL));
		model.put("standards", standardService.getStandardByStandardGroupForPublic(stadnardGroup));
		modelmap.put("model", model);
		return "/public/cpr/standard-group-detail";
	}
	
	
	
	@RequestMapping(value = {"/ehn/{id}", EN_PREFIX + "ehn/{id}"})
	public String showEhn(@PathVariable Long id,  ModelMap modelMap) throws PageNotFoundEception{
		final Webpage webpage = getWebpage(STANDARDS_URL);
		final Standard standard = standardService.getStandardById(id);
		if(standard == null || !standard.getEnabled() || webpage == null || standard == null || !webpage.getEnabled()){
			throw new PageNotFoundEception();
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("standard", standard);
		model.put("parentWebpage", webpage);
		model.put("webpage", webpageService.getWebpageByCode(EHN_DETAIL));
		model.put("noaoUrl", ceEuropeNotifiedBodyDetailUrl);
		modelMap.put("model", model);
		return "public/ehn";
	}
	
	@RequestMapping(value = "/ehn/autocomplete", method = RequestMethod.GET)
	public @ResponseBody List<Standard>  searchInTags(@RequestBody @RequestParam("term") String query){
		return standardService.autocomplateSearch(query, Boolean.TRUE);
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
		model.put("noaoUrl", ceEuropeNotifiedBodyDetailUrl);
		model.put("submenu", webpageService.getPublicSection(Constants.WEBPAGE_CATEGORY_CPR_SUBMENU));
		return model;
	}
	
}
