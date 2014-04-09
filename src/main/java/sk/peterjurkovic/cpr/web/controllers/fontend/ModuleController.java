package sk.peterjurkovic.cpr.web.controllers.fontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.constants.Filter;
import sk.peterjurkovic.cpr.entities.NotifiedBody;
import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.enums.StandardOrder;
import sk.peterjurkovic.cpr.enums.StandardStatus;
import sk.peterjurkovic.cpr.exceptions.PageNotFoundEception;
import sk.peterjurkovic.cpr.services.NotifiedBodyService;
import sk.peterjurkovic.cpr.services.StandardGroupService;
import sk.peterjurkovic.cpr.services.StandardService;
import sk.peterjurkovic.cpr.utils.ParseUtils;
import sk.peterjurkovic.cpr.utils.RequestUtils;

@Controller
public class ModuleController extends WebpageControllerSupport {

	
	private static final String STANDARDS_URL = 	"/m/harmonized-standards";
	private static final String STANDARD_GROUP_URL ="/m/cpr-groups";
	private static final String NOTIFIE_BODY_URL = 	"/m/notifiedbodies";
	
	
	@Autowired
	private StandardService standardService;
	@Autowired
	private NotifiedBodyService notifiedBodyService;
	@Autowired
	private StandardGroupService standardGroupService;
	
	@Value("#{config['nandourl']}")
	private String ceEuropeNotifiedBodyDetailUrl;
	
	private String viewName = "/public/web/article";
	
	
	@RequestMapping( STANDARDS_URL  )
	public String handleStandardList(ModelMap modelmap, HttpServletRequest request){
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
		modelmap.put("model", model);
		return viewName;
	}
	
	
	private NotifiedBody getNotifiedBody(final Object id){
		Long nbid = ParseUtils.parseLongFromStringObject(id);
		if(nbid != null && nbid != 0){
			return notifiedBodyService.getNotifiedBodyById(nbid);
		}
		return null;
	}
	
	
	@RequestMapping( STANDARD_GROUP_URL )
	public String handleStandardGroups(ModelMap modelMap) throws PageNotFoundEception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("standardGroups", standardGroupService.getStandardGroupsForPublic());
		modelMap.put("model", model);
		return viewName;
	}
	
	
	@RequestMapping( NOTIFIE_BODY_URL )
	public String handleNotifiedBodies(ModelMap modelmap) throws PageNotFoundEception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("noaoUrl", ceEuropeNotifiedBodyDetailUrl);
		model.put("notifiedBodies", notifiedBodyService.getNotifiedBodiesGroupedByCountry(Boolean.TRUE));
		modelmap.put("model", model);
		return viewName;
	}
}
