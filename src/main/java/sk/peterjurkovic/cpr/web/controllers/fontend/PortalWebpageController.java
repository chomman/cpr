package sk.peterjurkovic.cpr.web.controllers.fontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.enums.WebpageType;
import sk.peterjurkovic.cpr.exceptions.PageNotFoundEception;
import sk.peterjurkovic.cpr.exceptions.PortalAccessDeniedException;
import sk.peterjurkovic.cpr.utils.UserUtils;

@Controller
public class PortalWebpageController extends WebpageControllerSupport {

	private final static Long MAIN_NAV_ID = 75l;
	private final static Long SUB_NAV_ID = 84l;
	private final static Long SCOPE_ID = 104l;
	
	public PortalWebpageController(){
		setViewDirectory("/portal/");
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
	
	
	public String handleRegistrationPage(){
		
		return null;
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
