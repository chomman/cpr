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
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.constants.Filter;
import sk.peterjurkovic.cpr.dto.PageDto;
import sk.peterjurkovic.cpr.entities.NotifiedBody;
import sk.peterjurkovic.cpr.entities.PortalCurrency;
import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.enums.StandardOrder;
import sk.peterjurkovic.cpr.enums.StandardStatus;
import sk.peterjurkovic.cpr.enums.WebpageType;
import sk.peterjurkovic.cpr.exceptions.PageNotFoundEception;
import sk.peterjurkovic.cpr.services.AssessmentSystemService;
import sk.peterjurkovic.cpr.services.CsnTerminologyService;
import sk.peterjurkovic.cpr.services.NotifiedBodyService;
import sk.peterjurkovic.cpr.services.PortalProductService;
import sk.peterjurkovic.cpr.services.ReportService;
import sk.peterjurkovic.cpr.services.StandardGroupService;
import sk.peterjurkovic.cpr.services.StandardService;
import sk.peterjurkovic.cpr.utils.ParseUtils;
import sk.peterjurkovic.cpr.utils.RequestUtils;
import sk.peterjurkovic.cpr.web.forms.portal.PortalUserForm;
import sk.peterjurkovic.cpr.web.pagination.PageLink;
import sk.peterjurkovic.cpr.web.pagination.PaginationLinker;

@Controller
public class ModuleController extends WebpageControllerSupport {

	public static final String CURRENCY_PARAM = "currency";
	
	private static final String STANDARDS_URL = 			"/m/harmonized-standards";
	private static final String STANDARD_GROUPS_URL =		"/m/cpr-groups";
	private static final String NOTIFIE_BODIES_URL = 		"/m/notifiedbodies";
	private static final String REPORS_URL = 				"/m/reports";
	private static final String CSN_TERMINOLOGY_URL =		"/m/terminology";
	private static final String ASSESMENTS_SYSTEMS_URL =	"/m/asessments-systems";
	private static final String PORTAL_REGISTATION_URL = 	"/m/portal-registration";
	 
	
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
	private AssessmentSystemService assessmentSystemService;
	@Autowired
	private PortalProductService portalProductService;
	
	@Value("#{config['nandourl']}")
	private String ceEuropeNotifiedBodyDetailUrl;
	
	@RequestMapping( STANDARDS_URL  )
	public String handleStandardList(ModelMap modelMap, HttpServletRequest request) throws PageNotFoundEception{
		validateRequest(request);
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> params = RequestUtils.getRequestParameterMap(request);
		final int currentPage = RequestUtils.getPageNumber(request);
		params.put(Filter.ENABLED, Boolean.TRUE);
		List<Standard> standards = standardService.getStandardPage(currentPage, params, Constants.PUBLIC_STANDARD_PAGE_SIZE);
		params.put(Filter.NOTIFIED_BODY, getNotifiedBody(params.get(Filter.NOTIFIED_BODY)));
		model.put("standards", standards);
		model.put("params", params);
		model.put("strParams", RequestUtils.getRequestParams(request, Constants.PAGE_PARAM_NAME));
		model.put("orders", StandardOrder.getAll());
		model.put("standardStatuses", StandardStatus.getAll());
		model.put("standardGroups", standardGroupService.getStandardGroupsForPublic());
		return appendModelAndGetView(model, modelMap, request);
	}
	
	
	private NotifiedBody getNotifiedBody(final Object id){
		Long nbid = ParseUtils.parseLongFromStringObject(id);
		if(nbid != null && nbid != 0){
			return notifiedBodyService.getNotifiedBodyById(nbid);
		}
		return null;
	}
	
	
	@RequestMapping( STANDARD_GROUPS_URL )
	public String handleStandardGroups(ModelMap modelMap, HttpServletRequest request) throws PageNotFoundEception {
		validateRequest(request);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("standardGroups", standardGroupService.getStandardGroupsForPublic());
		return appendModelAndGetView(model, modelMap, request);
	}
	
	
	@RequestMapping( NOTIFIE_BODIES_URL )
	public String handleNotifiedBodies(ModelMap modelMap, HttpServletRequest request) throws PageNotFoundEception {
		validateRequest(request);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("noaoUrl", ceEuropeNotifiedBodyDetailUrl);
		model.put("notifiedBodies", notifiedBodyService.getNotifiedBodiesGroupedByCountry(Boolean.TRUE));
		return appendModelAndGetView(model, modelMap, request);
	}
	
	
	
	@RequestMapping( REPORS_URL  )
	public String handleReports(ModelMap modelMap, HttpServletRequest request) throws PageNotFoundEception{
		validateRequest(request);
		 Map<String, Object> model = new HashMap<String, Object>();
		 model.put("reports", reportService.getReportsForPublic());
		 return appendModelAndGetView(model, modelMap, request);
	}
	
	
	

	@RequestMapping( CSN_TERMINOLOGY_URL )
	public String showSearchForm(ModelMap modelMap, HttpServletRequest request) throws PageNotFoundEception{
		validateRequest(request);
		int currentPage = RequestUtils.getPageNumber(request);
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> params = RequestUtils.getRequestParameterMap(request);
		PageDto page = null;
		if(StringUtils.isNotBlank((String)params.get("query"))){
			page = csnTerminologyService.getCsnTerminologyPage(currentPage, params);
			if(page.getCount() > 0){
				model.put("paginationLinks", getPaginationItems(request, params, currentPage, page.getCount()));
				model.put("page", page.getItems() );
			}
			model.put("detailUrl", "/"+ModuleDetailController.TERMINOLOGY_URL_MAPPING);
			model.put("params", params );
		}
		return appendModelAndGetView(model, modelMap, request);
	}
	
	
	
	@RequestMapping( ASSESMENTS_SYSTEMS_URL )
    public String assessmentSystems(ModelMap modelMap, HttpServletRequest request) throws PageNotFoundEception {
		validateRequest(request);
		Map<String, Object> model = new HashMap<String, Object>();
        model.put("assessmentSystems", assessmentSystemService.getAssessmentSystemsForPublic());
        return appendModelAndGetView(model, modelMap, request);
    }
	
	
	
	@RequestMapping( PORTAL_REGISTATION_URL )
    public String portalRegistrationForm(ModelMap modelMap, HttpServletRequest request) throws PageNotFoundEception {
		validateRequest(request);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("portalRegistrations", portalProductService.getAllRegistrations(true));
		model.put("portalOnlinePublications", portalProductService.getAllOnlinePublications(true));
		model.put("vat", Constants.VAT);
		final String currency = request.getParameter(CURRENCY_PARAM);
		 PortalUserForm form = new PortalUserForm();
		 model.put("useEuro", false);
		if(StringUtils.isNotBlank(currency) && currency.toUpperCase().equals(PortalCurrency.EUR.getCode())){
			model.put("useEuro", true);
			form = new PortalUserForm(PortalCurrency.EUR);
		}
		modelMap.addAttribute("user", form);
        return appendModelAndGetView(model, modelMap, request);
    }
	
	
    
	private String appendModelAndGetView(Map<String, Object> model, ModelMap map, HttpServletRequest request){
		map.put("model", model);
		@SuppressWarnings("unchecked")
		Map<String, Object> defaultModel = (Map<String, Object>)request.getAttribute(WEBPAGE_MODEL_KEY);
		if(defaultModel != null && defaultModel.get(PORTAL_MODEL_KEY) != null){
			return "/portal/web/" + WebpageType.ARTICLE.getViewName();
		}
		return "/public/web/" + WebpageType.ARTICLE.getViewName();
	}
	
	
	
	private void validateRequest(HttpServletRequest request) throws PageNotFoundEception{
		if(request.getAttribute(WEBPAGE_MODEL_KEY) == null){;
			throw new PageNotFoundEception();
		}
	}
	
	
	
	private List<PageLink> getPaginationItems(HttpServletRequest request, Map<String, Object> params, int currentPage, int count){
		PaginationLinker paginger = new PaginationLinker(request, params);
		paginger.setCurrentPage(currentPage);
		paginger.setRowCount(count);
		return paginger.getPageLinks(); 
	}

	
}
