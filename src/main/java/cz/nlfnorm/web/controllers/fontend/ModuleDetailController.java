package cz.nlfnorm.web.controllers.fontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.constants.Filter;
import cz.nlfnorm.dto.ReportDto;
import cz.nlfnorm.entities.AssessmentSystem;
import cz.nlfnorm.entities.CsnTerminology;
import cz.nlfnorm.entities.NotifiedBody;
import cz.nlfnorm.entities.Report;
import cz.nlfnorm.entities.Standard;
import cz.nlfnorm.entities.StandardGroup;
import cz.nlfnorm.entities.Webpage;
import cz.nlfnorm.enums.CsnTerminologyLanguage;
import cz.nlfnorm.enums.WebpageModule;
import cz.nlfnorm.exceptions.PageNotFoundEception;
import cz.nlfnorm.services.AssessmentSystemService;
import cz.nlfnorm.services.BasicSettingsService;
import cz.nlfnorm.services.CsnService;
import cz.nlfnorm.services.CsnTerminologyService;
import cz.nlfnorm.services.NotifiedBodyService;
import cz.nlfnorm.services.PortalProductService;
import cz.nlfnorm.services.ReportService;
import cz.nlfnorm.services.StandardGroupService;
import cz.nlfnorm.services.StandardService;
import cz.nlfnorm.utils.RequestUtils;
import cz.nlfnorm.utils.UserUtils;

@Controller
public class ModuleDetailController extends WebpageControllerSupport{
	
	@Autowired
	private StandardService standardService;
	@Autowired
	private NotifiedBodyService notifiedBodyService;
	@Autowired
	private StandardGroupService standardGroupService;
	@Autowired
	private ReportService reportService;
	@Autowired
	private CsnTerminologyService csnTerminologyService;
	@Autowired
	private CsnService csnService;
	@Autowired
	private AssessmentSystemService assessmentSystemService;
	@Autowired
	private BasicSettingsService basicSettingsService;
	@Autowired
	private PortalProductService portalProductService;
	
	private static final String STANDARD_GROUP_DETAIL_URL = "/cpr/skupina/{code}";
	public static final String TERMINOLOGY_URL_MAPPING = "terminologicky-slovnik";

	
	@Value("#{config['nandourl']}")
	protected String ceEuropeNotifiedBodyDetailUrl;
	
	
	
	@RequestMapping(value = { "/async/standards" , EN_PREFIX + "async/standards" })
	public ModelAndView   standards(HttpServletRequest request, ModelMap map){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> params = RequestUtils.getRequestParameterMap(request);
		final int currentPage = RequestUtils.getPageNumber(request);
		params.put(Filter.ENABLED, Boolean.TRUE);
		model.put("standards", standardService.getStandardPage(currentPage, params, Constants.PUBLIC_STANDARD_PAGE_SIZE));
		model.put("async", true);
		map.put("model", model);
		return new ModelAndView("/include/standard-table", map );
	}
	
	
	
	
	@RequestMapping(value = {"/ehn/{id}", EN_PREFIX + "ehn/{id}"})
	public String showEhn(@PathVariable Long id,  ModelMap modelMap) throws PageNotFoundEception{
		final Standard standard = standardService.getStandardById(id);
		if(standard == null || !standard.getEnabled()){
			throw new PageNotFoundEception();
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("standard", standard);
		model.put("noaoUrl", ceEuropeNotifiedBodyDetailUrl);
		modelMap.put("model", model);
		prepareWebpageModel(modelMap, webpageService.getWebpageByModule(WebpageModule.CPR_EHN_LIST));
		return getView("ehn-detail");
	}
	
		
	@RequestMapping(value = {STANDARD_GROUP_DETAIL_URL, EN_PREFIX + STANDARD_GROUP_DETAIL_URL})
	public String showCprGroupDetail(@PathVariable String code, ModelMap modelMap) throws PageNotFoundEception {
		final StandardGroup stadnardGroup = standardGroupService.getStandardGroupByCode(code);
		if(stadnardGroup == null || !stadnardGroup.getEnabled()){
			throw new PageNotFoundEception();
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("group", stadnardGroup);
		prepareWebpageModel(modelMap, webpageService.getWebpageByModule(WebpageModule.CPR_GROUP_LIST), true);
		model.put("standards", standardService.getStandardByStandardGroupForPublic(stadnardGroup));
		modelMap.put("model", model);
		return getView("standard-group-detail");
	}
		
	
	
	
	
	@RequestMapping(value = {"/subjekt/{id}", EN_PREFIX + "subjekt/{id}"})
	public String showBasicRequirementDetail(@PathVariable Long id, ModelMap modelMap) throws PageNotFoundEception {
		final NotifiedBody notifiedBody = notifiedBodyService.getNotifiedBodyById(id);
		if(notifiedBody == null || !notifiedBody.getEnabled()){
			throw new PageNotFoundEception();
		}
		Map<String, Object> model = new HashMap<String, Object>();
		prepareWebpageModel(modelMap, webpageService.getWebpageByModule(WebpageModule.NOAO_LIST));
		model.put("noaoUrl", ceEuropeNotifiedBodyDetailUrl);
		model.put("standards", standardService.getStandardsByNotifiedBody(notifiedBody));
		model.put("notifiedBody", notifiedBody);
		modelMap.put("model", model);
		return getView("notifiedbody-detail");
	}
	
	@RequestMapping(value = "/ehn/autocomplete", method = RequestMethod.GET)
	public @ResponseBody List<Standard>  searchInTags(@RequestBody @RequestParam("term") String query){
		return standardService.autocomplateSearch(query, Boolean.TRUE);
	}
	
	
	
	
	
	
	@RequestMapping( { "/report/{id}" ,  EN_PREFIX + "report/{id}" } )
	public String showReport(ModelMap map, @PathVariable Long id, @RequestParam(required = false, defaultValue = "false") Boolean isPreview ) throws PageNotFoundEception{
		 final Report report = reportService.getById(id);
		 if(isPreview == null){
			 isPreview = false;
		 }
		 if(report == null || 
		   (!report.isEnabled() && !isPreview)){
			 throw new PageNotFoundEception();
		 }else if(isPreview && !UserUtils.hasLoggedUserRightToEdit()){
			 logger.warn(String.format("Unauthorized access to report preview.[reportID=%s]", id));
			 throw new PageNotFoundEception();
		 }
		 final ReportDto dto = reportService.getItemsFor(report, true);
		 Map<String, Object> model = new HashMap<String, Object>();
		 Map<String, Object> webpageModel = new HashMap<String, Object>();
		 webpageModel.put("mainnav", webpageService.getTopLevelWepages(true));
		 appendModel(map, webpageModel);
		 model.put("report", report);
		 model.put("webpage", webpageService.getWebpageByModule(WebpageModule.REPORT_LIST));
		 model.put("standards", dto.getStandards() );
		 model.put("standardCsns", dto.getStandardCsns() );
		 map.put("model", model);
		return getView("report-detail");
	}
	
	
	
	
	@RequestMapping(value = { "/"+TERMINOLOGY_URL_MAPPING+"/{id}", EN_PREFIX + TERMINOLOGY_URL_MAPPING +"/{id}" })
	public String showTerminologyDetial(ModelMap modelMap, @PathVariable Long id, HttpServletRequest request) throws PageNotFoundEception{
		final CsnTerminology termonology = csnTerminologyService.getById(id);
		if(termonology == null || !termonology.getEnabled()){
			throw new PageNotFoundEception();
		}
		CsnTerminologyLanguage tTang = null;
		if(termonology.getLanguage().equals(CsnTerminologyLanguage.CZ)){
			tTang = CsnTerminologyLanguage.EN;
		}else{
			tTang = CsnTerminologyLanguage.CZ;
		}
		CsnTerminology terminology2 = csnTerminologyService.getBySectionAndLang(termonology.getCsn(), termonology.getSection(), tTang);
		String lang = RequestUtils.getLangParameter(request);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("terminology", termonology);
		if(terminology2 != null){
			model.put("terminology2", terminology2);
		}
		if(StringUtils.isNotBlank(lang)){
			model.put("lang", lang);
			model.put("terminologies", csnService.getTerminologyByCsnAndLang(termonology.getCsn(), termonology.getLanguage().getCode()));
		}
		final Webpage webpage = webpageService.getWebpageByModule(WebpageModule.TERMINOLOGY);
		if(webpage == null){
			throw new PageNotFoundEception();
		}
		model.put("csnOnlineUrl", basicSettingsService.getCsnOnlineUrl());
		prepareWebpageModel(modelMap, webpage, true);
		model.put("detailUrl", "/"  + TERMINOLOGY_URL_MAPPING );
		modelMap.put("model", model);
		return getView("terminology-detail");
	}
	
		
	@RequestMapping(value = {"/cpr/as/{id}", EN_PREFIX + "cpr/as/{id}"})
    public String showAssessmentSystemDetail(@PathVariable Long id, ModelMap modelMap) throws PageNotFoundEception {
            final AssessmentSystem assessmentSystem = assessmentSystemService.getAssessmentSystemById(id);
            if(assessmentSystem == null || !assessmentSystem.isEnabled()){
                    throw new PageNotFoundEception();
            }
            Map<String, Object> model = new HashMap<String, Object>();
            prepareWebpageModel(modelMap, webpageService.getWebpageByModule(WebpageModule.CPR_AS_LIST), true);
            model.put("assessmentSystem", assessmentSystem);
            modelMap.put("model", model);
            return getView("assessmentsystem-detail");
    }
	
	private void prepareWebpageModel(ModelMap modelMap, final Webpage webpage){
		prepareWebpageModel(modelMap, webpage, false);
	}
	
	private void prepareWebpageModel(ModelMap modelMap, final Webpage webpage, final boolean hideDecoratorBreadcrumb){
		Map<String, Object> webpageModel = new HashMap<String, Object>();
		webpageModel.put("webpage", webpage);
		webpageModel.put("breadcrumbDisabled", hideDecoratorBreadcrumb);
		webpageModel.put("mainnav", webpageService.getTopLevelWepages(true));
		appendModel(modelMap, webpageModel);
	}
		
	private String getView(String name){
		return "/module/detail/" + name;
	}
}
