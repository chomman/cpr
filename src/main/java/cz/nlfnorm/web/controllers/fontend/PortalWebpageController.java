package cz.nlfnorm.web.controllers.fontend;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.Webpage;
import cz.nlfnorm.enums.WebpageModule;
import cz.nlfnorm.exceptions.PageNotFoundEception;
import cz.nlfnorm.exceptions.PortalAccessDeniedException;
import cz.nlfnorm.services.PortalProductService;
import cz.nlfnorm.utils.RequestUtils;
import cz.nlfnorm.web.pagination.PageLink;
import cz.nlfnorm.web.pagination.PaginationLinker;



@Controller
public class PortalWebpageController extends PortalWebpageControllerSupport {

	public static final String ACCESS_DENIED_URL = "/" + Constants.PORTAL_URL + "/pristup-zamitnut";
	
	private final static Long SCOPE_ID = 104l;
	
	@Autowired
	private PortalProductService portalProductService;
	
			
	@RequestMapping(value = { "/"+ Constants.PORTAL_URL,  EN_PREFIX + Constants.PORTAL_URL })
	public String handlePortalHmepage(ModelMap modelMap, HttpServletRequest request) throws PageNotFoundEception, PortalAccessDeniedException{
		appendModel(modelMap, getWebpage( Constants.PORTAL_URL ) );
		modelMap.put("scopes", webpageService.getWebpageById(SCOPE_ID));
		modelMap.put("onlinePublicationPage", webpageService.getWebpageByModule(WebpageModule.PUBLICATIONS));
		modelMap.put("publications", portalProductService.getAllOnlinePublications(true));
		if(StringUtils.isNotBlank(request.getParameter(Constants.FAILURE_LOGIN_PARAM_KEY))){
			modelMap.put(Constants.FAILURE_LOGIN_PARAM_KEY, true);
		}
		return getViewDirectory() + "index";
	}
	
	
	
	@RequestMapping( value = { "/"+ Constants.PORTAL_URL + "/{id}/*", EN_PREFIX+ Constants.PORTAL_URL + "/{id}/*" } )
	public String handleChildPages(@PathVariable Long id, ModelMap modelMap) throws PageNotFoundEception, PortalAccessDeniedException{
		return appendModelAndGetView(modelMap, getWebpage( id ));
	}
	
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@RequestMapping( ACCESS_DENIED_URL )
	public String handlePortalAccessDenied(ModelMap modelMap) throws PageNotFoundEception, PortalAccessDeniedException{
		appendModel(modelMap, getWebpage( Constants.PORTAL_URL ) );
		return getViewDirectory() + "access-denied";
	}
	
	@RequestMapping(value = { "/"+ Constants.PORTAL_URL+"/search",  EN_PREFIX + Constants.PORTAL_URL+"/search" })
	public String search(ModelMap modelMap, HttpServletRequest request) throws PageNotFoundEception, PortalAccessDeniedException{
		final Webpage webpage = getWebpage( Constants.PORTAL_URL );
		Map<String, Object> model =  prepareModel( webpage );
		int currentPage = RequestUtils.getPageNumber(request);
		Map<String, Object> params = RequestUtils.getRequestParameterMap(request);
		PageDto page = webpageService.getSearchPage(currentPage, (String)params.get("q"), webpage.getId());
		if(page.getCount() > 0){
			model.put("paginationLinks", getPaginationItems(request,params, currentPage, page.getCount()));
			model.put("items", page.getItems() );
		}
		model.put("count", page.getCount());
		model.put("q", (String)params.get("q"));
		modelMap.put(WEBPAGE_MODEL_KEY, model );
		return getViewDirectory() + "search-results";
	}
	
	
	private  List<PageLink> getPaginationItems(HttpServletRequest request, Map<String, Object> params, int currentPage, int count){
		PaginationLinker paginger = new PaginationLinker(request, params);
		paginger.setUrl(request.getRequestURL().toString());
		paginger.setCurrentPage(currentPage);
		paginger.setRowCount(count);
		return paginger.getPageLinks(); 
	}
	
}
