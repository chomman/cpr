package cz.nlfnorm.web.controllers.fontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.entities.Webpage;
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
	private final static String TARGET_PARAM = "target";
	
	private final static String PORTAL_PRODUCT_DETAIL_URL = "/widget/registrace/4";
	private final static String PORTAL_PRODUCT_ID_PARAM = "pid";
	
	private final static int DEFAULT_COUNT_OF_NEWS = 5;
	private final static int DEFAULT_DESCR_LENGTH = 150;
	
	private final static int TYPE_REGISTRATION = 1;
	private final static int TYPE_ABOUT = 2;
	private final static int TYPE_NEWS = 3;
	private final static int TYPE_PORTAL_PRODUCT_DETAIL = 4;
	private final static int TYPE_NEWS_DETAIL = 5;
	private final static int TYPE_REDIRECT = 6;
	
	private final static int LIMIT_OF_SIMILAR_NEWS = 4;
	
	
	@RequestMapping(value = { "/widget/aktuality", "/{lang}/widget/aktuality"} )
	public String handleNewsWidget(
			ModelMap modelMap, 
			HttpServletRequest request) {		
		appendModel(modelMap, prepareNewsModel(request));
		return getView(); 
	}
	
	@RequestMapping(value = { "/widget/aktualita/{id}", "/{lang}/widget/aktualita/{id}"} )
	public String handleNewsDetail(
			ModelMap modelMap, 
			HttpServletRequest request,
			final @PathVariable long id) throws PageNotFoundEception, PortalAccessDeniedException {
		Map<String, Object> model = new HashMap<String, Object>();
		final Webpage webpage = getWebpage(id);
		model.put("webpage", webpage);
		model.put("news", webpageService.getSimilarNews(webpage, LIMIT_OF_SIMILAR_NEWS));
		appendType(model, TYPE_NEWS_DETAIL);
		prepareNewsModel(request, model);
		appendModel(modelMap, model);
		return getView(); 
	}
	
	@RequestMapping(value = { "/widget/registrace", "/{lang}/widget/registrace"} )
	public String registracion(HttpServletRequest request){
		return "redirect:/widget/registrace/" + TYPE_ABOUT + getRegistrationWigetRedirectParams(request);
	}
	
	@RequestMapping(value = { "/widget/registrace/{pageType}", "/{lang}/widget/registrace/pageType"} )
	public String registracion(final @PathVariable int pageType, ModelMap map, HttpServletRequest request) throws PageNotFoundEception{
		Map<String, Object> model = new HashMap<String, Object>();
		switch (pageType) {
			case TYPE_REGISTRATION:
				prepareRegistrationFormModel(model);
				preparePortalOrderModel(model, map, request, new PortalUserForm());
				break;
			case TYPE_PORTAL_PRODUCT_DETAIL:
				preaprePortalProductDetailModel(model, request);
				break;
			case TYPE_ABOUT:
				preapreAboutWebpageModel(model);
				break;
			case TYPE_REDIRECT:
				 setSessionSourceParam(request);
				 return "redirect:/" + Constants.PORTAL_URL;
			default :
				throw new PageNotFoundEception();
		}
		preapreRegistravionModel(model, request, pageType);
		appendModel(map, model);
		return getView();
	}
		
	
	private void preapreRegistravionModel(final Map<String, Object> model, HttpServletRequest request, final int type){
		model.put("params", getRegistrationWigetParams(request));
		appendCss(model, request.getParameter(CSS_PARAM));
		appendType(model, type);
	}
	
	private void prepareRegistrationFormModel(final Map<String, Object> model){
		model.put("registration", webpageService.getWebpageByModule(WebpageModule.PORTAL_REGISTRATION));
		model.put("portalProductDetailUrl", PORTAL_PRODUCT_DETAIL_URL);
	}
	
	private void preaprePortalProductDetailModel(final Map<String, Object> model, HttpServletRequest request){
		final Long portalProductId = Long.valueOf(request.getParameter(PORTAL_PRODUCT_ID_PARAM));
		model.put("portalProduct", portalProductService.getById(portalProductId));
	}
	
	private void preapreAboutWebpageModel(final Map<String,	Object> model){
		model.put("webpage", webpageService.getWebpageById(88l));
	}
	
	public Map<String, Object> prepareNewsModel(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("news", webpageService.getLatestPublishedNews(getNewsLimit(request)));
		appendType(model, TYPE_NEWS);
		prepareNewsModel(request,model);
		return model;
	}
	
	public Map<String, Object> prepareNewsModel(HttpServletRequest request, Map<String, Object> model){
		appendCss(model, request.getParameter(CSS_PARAM));
		model.put("descrLength", getDescriptionLimit(request));
		model.put("showInWindow", isTargetWindow(request));
		model.put("params", getWidgetRequestParamsUrl(request));
		return model;
	}
	
	public String getWidgetRequestParamsUrl(HttpServletRequest request){
		StringBuilder url = new StringBuilder("?");
		int paramVal = RequestUtils.getIntParameter(COUNT_OF_NEWS_PARAM, request);
		append(COUNT_OF_NEWS_PARAM, paramVal, url);
		paramVal = RequestUtils.getIntParameter(DESCR_LENGHT_PARAM, request);
		append(DESCR_LENGHT_PARAM, paramVal, url);
		append(CSS_PARAM, request.getParameter(CSS_PARAM), url);
		append(TARGET_PARAM, request.getParameter(TARGET_PARAM), url);
		if(url.equals("?")){
			return "";
		}
		return url.toString();
	}
	
	public String getRegistrationWigetParams(HttpServletRequest request){
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
	
	public String getRegistrationWigetRedirectParams(HttpServletRequest request){
		return getRegistrationWigetParams(request).replace("&amp;", "&");
	}
	
	private boolean isTargetWindow(final HttpServletRequest request){
		final String target = request.getParameter(TARGET_PARAM);
		return target != null && target.equals("window");
	}
	
	
	private int getDescriptionLimit(final HttpServletRequest request){
		int descrLength = RequestUtils.getIntParameter(DESCR_LENGHT_PARAM, request);
		if(descrLength < 0 || descrLength > 500){
			descrLength = DEFAULT_DESCR_LENGTH;
		}
		return descrLength;
	}
	
	private int getNewsLimit(final HttpServletRequest request){
		int countOfNews = RequestUtils.getIntParameter(COUNT_OF_NEWS_PARAM, request);
		if(countOfNews < 0 || countOfNews > 20){
			countOfNews = DEFAULT_COUNT_OF_NEWS;
		}
		return countOfNews;
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
