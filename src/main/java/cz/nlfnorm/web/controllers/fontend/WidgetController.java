package cz.nlfnorm.web.controllers.fontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.nlfnorm.enums.WebpageModule;
import cz.nlfnorm.exceptions.PageNotFoundEception;
import cz.nlfnorm.exceptions.PortalAccessDeniedException;
import cz.nlfnorm.utils.RequestUtils;
import cz.nlfnorm.web.forms.portal.PortalUserForm;

@Controller
public class WidgetController extends PortalWebpageControllerSupport{

	private final static String CSS_PARAM = "css";
	private final static String COUNT_OF_NEWS_PARAM = "count";
	private final static String DESCR_LENGHT_PARAM = "descrLength";
	private final static int DEFAULT_COUNT_OF_NEWS = 5;
	private final static int DEFAULT_DESCR_LENGTH = 150;
	
	private final static int TYPE_REGISTRATION = 1;
	private final static int TYPE_WEBPAGE = 2;
	private final static int TYPE_NEWS = 3;
	
	
	
	@RequestMapping(value = { "/widget/registrace", "/{lang}/widget/registrace"} )
	public String handleRegistrationPage(
			ModelMap modelMap, 
			HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		prepareModel(model,  TYPE_REGISTRATION, request);
		preparePortalOrderModel(model, modelMap, request, new PortalUserForm());
		appendModel(modelMap, model);
		return getView(); 
	}
	
	@RequestMapping(value = { "/widget/aktuality", "/{lang}/widget/aktuality"} )
	public String handleNewsWidget(
			ModelMap modelMap, 
			HttpServletRequest request) {
		int countOfNews = RequestUtils.getIntParameter(COUNT_OF_NEWS_PARAM, request);
		if(countOfNews < 0 || countOfNews > 20){
			countOfNews = DEFAULT_COUNT_OF_NEWS;
		}
		int descrLength = RequestUtils.getIntParameter(DESCR_LENGHT_PARAM, request);
		if(descrLength < 0 || descrLength > 500){
			descrLength = DEFAULT_DESCR_LENGTH;
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("news", webpageService.getLatestPublishedNews(countOfNews));
		appendCss(model, request.getParameter(CSS_PARAM));
		appendType(model, TYPE_NEWS);
		model.put("descrLength", descrLength);
		appendModel(modelMap, model);
		return getView(); 
	}
	
	@RequestMapping(value = { "/widget/{webpageId}", "/{lang}/widget/{webpageId}"} )
	public String handleWebpage(
			@PathVariable Long webpageId,
			ModelMap modelMap, 
			HttpServletRequest request) throws PageNotFoundEception, PortalAccessDeniedException {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webpage", getWebpage(webpageId));
		prepareModel(model,  TYPE_WEBPAGE, request);
		appendModel(modelMap, model);
		return getView(); 
	}
	
	
	private void prepareModel(Map<String, Object> model, final int type, HttpServletRequest request){
		model.put("nav",  webpageService.getChildrensOfNode(76l, true));
		model.put("registration", webpageService.getWebpageByModule(WebpageModule.PORTAL_REGISTRATION));
		model.put("params", getReigstrationUrl(request));
		appendCss(model, request.getParameter(CSS_PARAM));
		appendType(model, type);
	}
	
	public String getReigstrationUrl(HttpServletRequest request){
		StringBuilder url = new StringBuilder("?");
		int paramVal = RequestUtils.getIntParameter(CURRENCY_PARAM, request);
		append(CURRENCY_PARAM, paramVal, url);
		paramVal = RequestUtils.getIntParameter(COUNTRY_PARAM, request);
		append(COUNTRY_PARAM, paramVal, url);
		paramVal = RequestUtils.getIntParameter(SOURCE_PARAM, request);
		append(SOURCE_PARAM, paramVal, url);
		append(CSS_PARAM, request.getParameter(CSS_PARAM), url);
		if(url.equals("?")){
			return "";
		}
		return url.toString();
	}
	
	private void appendCss(Map<String, Object> model, String val){
		if(StringUtils.isNotBlank(val)){
			model.put("css", val);
		}
	}
	private void appendType(Map<String, Object> model, int val){
		model.put("type", val);
	}
	
	private void append(String paramName, int paramVal, StringBuilder url){
		if(paramVal > 0){
			append(paramName, ""+paramVal, url);
		}
	}
	private void append(String paramName, String paramVal, StringBuilder url){
		if(StringUtils.isNotBlank(paramVal)){
			if(!url.toString().endsWith("?")){
				url.append("&amp;");
			}
			url.append(paramName).append("=").append(paramVal);
		}
	}
	
	private String getView(){
		return "/widget/widget"; 
	}
}
