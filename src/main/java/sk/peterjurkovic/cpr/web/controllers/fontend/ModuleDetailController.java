package sk.peterjurkovic.cpr.web.controllers.fontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import sk.peterjurkovic.cpr.entities.NotifiedBody;
import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.entities.StandardGroup;
import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.enums.WebpageModule;
import sk.peterjurkovic.cpr.exceptions.PageNotFoundEception;
import sk.peterjurkovic.cpr.services.NotifiedBodyService;
import sk.peterjurkovic.cpr.services.StandardGroupService;
import sk.peterjurkovic.cpr.services.StandardService;
import sk.peterjurkovic.cpr.utils.RequestUtils;

@Controller
public class ModuleDetailController extends WebpageControllerSupport{
	
	@Autowired
	private StandardService standardService;
	@Autowired
	private NotifiedBodyService notifiedBodyService;
	@Autowired
	private StandardGroupService standardGroupService;
	
	private static final String STANDARD_GROUP_DETAIL_URL = "/cpr/skupina/{code}";
	
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
		
	
	@RequestMapping(value = {"/subjekt/{id}", EN_PREFIX + "/subjekt/{id}"})
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
	
}
