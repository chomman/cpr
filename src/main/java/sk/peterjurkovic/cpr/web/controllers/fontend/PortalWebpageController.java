package sk.peterjurkovic.cpr.web.controllers.fontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.enums.WebpageType;
import sk.peterjurkovic.cpr.exceptions.ItemNotFoundException;
import sk.peterjurkovic.cpr.exceptions.PageNotFoundEception;
import sk.peterjurkovic.cpr.exceptions.PortalAccessDeniedException;
import sk.peterjurkovic.cpr.services.PortalUserService;
import sk.peterjurkovic.cpr.utils.UserUtils;
import sk.peterjurkovic.cpr.validators.forntend.PortalUserValidator;
import sk.peterjurkovic.cpr.web.forms.portal.PortalUserForm;
import sk.peterjurkovic.cpr.web.json.JsonResponse;
import sk.peterjurkovic.cpr.web.json.JsonStatus;



@Controller
public class PortalWebpageController extends WebpageControllerSupport {

	private final static Long MAIN_NAV_ID = 75l;
	private final static Long SUB_NAV_ID = 84l;
	private final static Long SCOPE_ID = 104l;
	
	@Autowired
	private PortalUserValidator portalUserValidator;
	@Autowired
	private PortalUserService portalUserService;
	
	public PortalWebpageController(){
		setViewDirectory("/portal/");
	}
	
	@InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(portalUserValidator);
    }

		
	
	@RequestMapping(value = { "/"+ Constants.PORTAL_URL,  EN_PREFIX + Constants.PORTAL_URL })
	public String handlePortalHmepage(ModelMap modelMap, HttpServletRequest request) throws PageNotFoundEception, PortalAccessDeniedException{
		appendModel(modelMap, getWebpage( Constants.PORTAL_URL ) );
		modelMap.put("scopes", webpageService.getWebpageById(SCOPE_ID));
		modelMap.put("publications", webpageService.getWebpageById(279l));
		if(StringUtils.isNotBlank(request.getParameter(Constants.FAILURE_LOGIN_PARAM_KEY))){
			modelMap.put(Constants.FAILURE_LOGIN_PARAM_KEY, true);
		}
		return getViewDirectory() + "index";
	}
	
	
	
	@RequestMapping( value = { "/"+ Constants.PORTAL_URL + "/{id}/*", EN_PREFIX+ Constants.PORTAL_URL + "/{id}/*" } )
	public String handleChildPages(@PathVariable Long id, ModelMap modelMap) throws PageNotFoundEception, PortalAccessDeniedException{
		return appendModelAndGetView(modelMap, getWebpage( id ));
	}
	
	
	
	@RequestMapping(value = "/ajax/registration", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JsonResponse  processAjaxSubmit(@Valid @RequestBody  PortalUserForm form, BindingResult result) throws ItemNotFoundException{
		JsonResponse response = new JsonResponse();

		if(result.hasErrors()){
			response.setResult(portalUserValidator.getErrorMessages(result.getAllErrors()));
			return response;
		}
		portalUserService.createNewUser(form.toUser());
		response.setStatus(JsonStatus.SUCCESS);
		return response;
	}
	
	
	
	
	
	@Override
	protected Map<String, Object> prepareModel(Webpage webpage){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webpage", webpage);
		
		if(webpage.getWebpageType().equals(WebpageType.NEWS_CATEGORY)){
			model.put("newsItems", webpageService.getLatestPublishedNews(30) );
		}
		model.put(PORTAL_MODEL_KEY, true);
		model.put("mainnav", webpageService.getChildrensOfNode(MAIN_NAV_ID, true));
		model.put("subnav", webpageService.getChildrensOfNode(SUB_NAV_ID, true));
		model.put("rootwebpage", webpageService.getWebpageByCode(Constants.PORTAL_URL));
		model.put("news", webpageService.getLatestPublishedNews(4) );
		model.put("portalParam", Constants.PORTAL_ID_PARAM_KEY);
		
		final User user = UserUtils.getLoggedUser();
		if(user != null){
			model.put("loggedUser", user);
		}else{
			model.put("registrationPage", webpageService.getWebpageByCode("registrace"));
		}
		return model;
	}
	
}
