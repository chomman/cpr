package cz.nlfnorm.web.controllers.fontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cz.nlfnorm.enums.WebpageModule;
import cz.nlfnorm.exceptions.PageNotFoundEception;
import cz.nlfnorm.exceptions.PortalAccessDeniedException;
import cz.nlfnorm.utils.RequestUtils;
import cz.nlfnorm.web.forms.portal.PortalOrderForm;
import cz.nlfnorm.web.forms.portal.PortalUserForm;

@Controller
public class WidgetController extends PortalWebpageControllerSupport{

	private final static String CSS_PARAM = "css";
	
	private final static int TYPE_REGISTRATION = 1;
	private final static int TYPE_WEBPAGE = 2;
	private final static String WIDGET_PARAM = "";
	
	
	
	@RequestMapping(value = { "/widget/registrace", "/{lang}/widget/registrace"} )
	public String handleRegistrationPage(
			@RequestParam(value = CSS_PARAM, required = false) String css,
			ModelMap modelMap, 
			HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		prepareModel(model, css, TYPE_REGISTRATION);
		preparePortalOrderModel(model, modelMap, request, new PortalUserForm());
		appendModel(modelMap, model);
		return getView(); 
	}
	
	@RequestMapping(value = { "/widget/{webpageId}", "/{lang}/widget/{webpageId}"} )
	public String handleWebpage(
			@RequestParam(value = CSS_PARAM, required = false) String css,
			@PathVariable Long webpageId,
			ModelMap modelMap, 
			HttpServletRequest request) throws PageNotFoundEception, PortalAccessDeniedException {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webpage", getWebpage(webpageId));
		prepareModel(model, css, TYPE_WEBPAGE);
		appendModel(modelMap, model);
		return getView(); 
	}
	
	
	private void prepareModel(Map<String, Object> model, final String css, final int type){
		model.put(CSS_PARAM, css);
		model.put("nav",  webpageService.getChildrensOfNode(76l, true));
		model.put("registration", webpageService.getWebpageByModule(WebpageModule.PORTAL_REGISTRATION));
		model.put("type",  type);
	}
	
	public String getReigstrationUrl(final PortalOrderForm form, HttpServletRequest request){
		StringBuilder url = new StringBuilder("?");
		int paramVal = RequestUtils.getIntParameter(CURRENCY_PARAM, request);
		append(CURRENCY_PARAM, paramVal, url);
		paramVal = RequestUtils.getIntParameter(COUNTRY_PARAM, request);
		append(COUNTRY_PARAM, paramVal, url);
		paramVal = RequestUtils.getIntParameter(SOURCE_PARAM, request);
		append(SOURCE_PARAM, paramVal, url);
		return url.toString();
	}
	
	private void append(String paramName, int paramVal, StringBuilder url){
		if(paramVal > 0){
			if(!url.toString().startsWith("?")){
				url.append("&amp;");
			}
			url.append(CURRENCY_PARAM).append("=").append(paramVal);
		}
	}
	
	private String getView(){
		return "/widget/widget"; 
	}
}
