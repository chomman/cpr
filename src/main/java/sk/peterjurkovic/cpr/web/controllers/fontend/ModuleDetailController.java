package sk.peterjurkovic.cpr.web.controllers.fontend;

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

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.constants.Filter;
import sk.peterjurkovic.cpr.dto.ReportDto;
import sk.peterjurkovic.cpr.entities.AssessmentSystem;
import sk.peterjurkovic.cpr.entities.CsnTerminology;
import sk.peterjurkovic.cpr.entities.NotifiedBody;
import sk.peterjurkovic.cpr.entities.Report;
import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.entities.StandardGroup;
import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.enums.CsnTerminologyLanguage;
import sk.peterjurkovic.cpr.enums.WebpageModule;
import sk.peterjurkovic.cpr.exceptions.PageNotFoundEception;
import sk.peterjurkovic.cpr.services.AssessmentSystemService;
import sk.peterjurkovic.cpr.services.CsnService;
import sk.peterjurkovic.cpr.services.CsnTerminologyService;
import sk.peterjurkovic.cpr.services.NotifiedBodyService;
import sk.peterjurkovic.cpr.services.ReportService;
import sk.peterjurkovic.cpr.services.StandardGroupService;
import sk.peterjurkovic.cpr.services.StandardService;
import sk.peterjurkovic.cpr.utils.RequestUtils;
import sk.peterjurkovic.cpr.utils.UserUtils;

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
		model.put("webpage", webpageService.getWebpageByModule(WebpageModule.CPR_EHN_LIST));
		model.put("noaoUrl", ceEuropeNotifiedBodyDetailUrl);
		modelMap.put("model", model);
		return "public/detail/ehn-detail";
	}
	
	
	
	
	
	
	@RequestMapping(value = {STANDARD_GROUP_DETAIL_URL, EN_PREFIX + STANDARD_GROUP_DETAIL_URL})
	public String showCprGroupDetail(@PathVariable String code, ModelMap modelmap) throws PageNotFoundEception {
		final StandardGroup stadnardGroup = standardGroupService.getStandardGroupByCode(code);
		if(stadnardGroup == null || !stadnardGroup.getEnabled()){
			throw new PageNotFoundEception();
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("group", stadnardGroup);
		model.put("webpage", webpageService.getWebpageByModule(WebpageModule.CPR_GROUP_LIST));
		model.put("standards", standardService.getStandardByStandardGroupForPublic(stadnardGroup));
		modelmap.put("model", model);
		return "/public/detail/standard-group-detail";
	}
		
	
	
	
	
	@RequestMapping(value = {"/subjekt/{id}", EN_PREFIX + "subjekt/{id}"})
	public String showBasicRequirementDetail(@PathVariable Long id, ModelMap modelmap) throws PageNotFoundEception {
		final NotifiedBody notifiedBody = notifiedBodyService.getNotifiedBodyById(id);
		if(notifiedBody == null || !notifiedBody.getEnabled()){
			throw new PageNotFoundEception();
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webpage", webpageService.getWebpageByModule(WebpageModule.NOAO_LIST));
		model.put("noaoUrl", ceEuropeNotifiedBodyDetailUrl);
		model.put("standards", standardService.getStandardsByNotifiedBody(notifiedBody));
		model.put("notifiedBody", notifiedBody);
		modelmap.put("model", model);
		return "/public/detail/notifiedbody-detail";
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
		 ReportDto dto = reportService.getItemsFor(report);
		 Map<String, Object> model = new HashMap<String, Object>();
		 model.put("report", report);
		 model.put("webpage", webpageService.getWebpageByModule(WebpageModule.REPORT_LIST));
		 model.put("standards", dto.getStandards() );
		 model.put("standardCsns", dto.getStandardCsns() );
		 map.put("model", model);
		return "public/detail/report-detail";
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
		model.put("webpage", webpage );
		model.put("detailUrl", "/"  + TERMINOLOGY_URL_MAPPING );
		modelMap.put("model", model);
		return "/public/detail/terminology-detail";
	}
	
	
	
	
	
	@RequestMapping(value = {"/cpr/as/{id}", EN_PREFIX + "cpr/as/{id}"})
    public String showAssessmentSystemDetail(@PathVariable Long id, ModelMap modelmap) throws PageNotFoundEception {
            final AssessmentSystem assessmentSystem = assessmentSystemService.getAssessmentSystemById(id);
            if(assessmentSystem == null || !assessmentSystem.isEnabled()){
                    throw new PageNotFoundEception();
            }
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("webpage", webpageService.getWebpageByModule(WebpageModule.CPR_AS_LIST));
            model.put("assessmentSystem", assessmentSystem);
            modelmap.put("model", model);
            return "/public/detail/assessmentsystem-detail";
    }
}
