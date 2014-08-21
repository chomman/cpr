package cz.nlfnorm.web.controllers.fontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.entities.PortalCurrency;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.entities.Webpage;
import cz.nlfnorm.enums.PortalCountry;
import cz.nlfnorm.enums.PortalOrderSource;
import cz.nlfnorm.enums.WebpageType;
import cz.nlfnorm.services.PortalOrderService;
import cz.nlfnorm.services.PortalProductService;
import cz.nlfnorm.utils.RequestUtils;
import cz.nlfnorm.utils.UserUtils;
import cz.nlfnorm.web.forms.portal.PortalOrderForm;
import cz.nlfnorm.web.forms.portal.PortalUserForm;

public abstract class PortalWebpageControllerSupport extends WebpageControllerSupport {
	
	public static final String SESSION_SOURCE_KEY = "ps";
	
	public static final String CURRENCY_PARAM = "currency";
	public static final String SOURCE_PARAM = "source";
	public static final String COUNTRY_PARAM = "country";
	
	private final static int COUNT_OF_SIMILAR_NEWS = 5;
	private final static int COUNT_OF_NEWS = 6;
	private final static Long MAIN_NAV_ID = 75l;
	private final static Long SUB_NAV_ID = 84l;
	protected static final String PRIFILE_URL =  Constants.PORTAL_URL + "/profile";
	
	@Autowired
	protected PortalOrderService portalOrderService;
	@Autowired
	protected PortalProductService portalProductService;
	
	
	
	public PortalWebpageControllerSupport(){
		setViewDirectory("/portal/");
	}
	
	@Override
	protected Map<String, Object> prepareModel(final Webpage webpage){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webpage", webpage);
		if(webpage.getWebpageType().equals(WebpageType.NEWS_CATEGORY)){
			model.put("newsItems", webpageService.getLatestPublishedNews(30) );
		}
		model.put(PORTAL_MODEL_KEY, true);
		model.put("mainnav", webpageService.getChildrensOfNode(MAIN_NAV_ID, true));
		model.put("subnav", webpageService.getChildrensOfNode(SUB_NAV_ID, true));
		model.put("rootwebpage", webpageService.getWebpageByCode(Constants.PORTAL_URL));
		model.put("footerNav", webpageService.getFooterWebpages());
		model.put("news", webpageService.getLatestPublishedNews(COUNT_OF_NEWS) );
		model.put("portalParam", Constants.PORTAL_ID_PARAM_KEY);
		model.put("profileUrl", "/" + PRIFILE_URL);
		if(webpage.isNews()){
			model.put("similarWebpages", webpageService.getSimilarNews(webpage, COUNT_OF_SIMILAR_NEWS));
		}else if(webpage.isArticle()){
			model.put("similarWebpages", webpageService.getSimilarArticles(webpage, COUNT_OF_SIMILAR_NEWS));
		}
		final User user = UserUtils.getLoggedUser();
		if(user != null){
			model.put("loggedUser", user);
		}else{
			model.put("registrationPage", webpageService.getWebpageByCode(Webpage.REGISTATION_CODE));
		}
		return model;
	}
	
	
	protected void preparePortalOrderModel(Map<String, Object> model, ModelMap map, HttpServletRequest request, PortalOrderForm form) {
		model.put("portalRegistrations", portalProductService.getAllRegistrations(true));
		model.put("portalOnlinePublications", portalProductService.getAllOnlinePublications(true));
		model.put("vat", Constants.VAT);
		model.put("portalCountries", PortalCountry.getAll());
		model.put("portalCurrencies", PortalCurrency.getAll());
		model.put("isRegistration", (form instanceof PortalUserForm));
		model.put("termsOfConditions", webpageService.getWebpageByCode(Webpage.TERMS_OF_CONDITIONS_CODE));
		appendSelectedProduct(model, request);
		setPortaOrderCurrency(request, form);
		setPortalOrderSource(request, form);
		setPortaOrderCountry(request, form);
		map.addAttribute("user", form);
	}
	
	
	private void setPortaOrderCurrency(HttpServletRequest request, PortalOrderForm form){
		final int paramVal = RequestUtils.getIntParameter(CURRENCY_PARAM, request);
		if(paramVal != RequestUtils.NOT_FOUD){
			final PortalCurrency currency = PortalCurrency.getById(Integer.valueOf(paramVal));
			if(currency != null){
				form.setPortalCurrency(currency);
			}
		}
	}
	
	private void setPortaOrderCountry(HttpServletRequest request, PortalOrderForm form){
		final int paramVal = RequestUtils.getIntParameter(COUNTRY_PARAM, request);
		if(paramVal != RequestUtils.NOT_FOUD){
			final PortalCountry country = PortalCountry.getById(Integer.valueOf(paramVal));
			if(country != null){
				form.getUserInfo().setPortalCountry(country);
			}
		}
	}
	
		
	private void setPortalOrderSource(HttpServletRequest request, PortalOrderForm form){
		int paramVal = RequestUtils.getIntParameter(SOURCE_PARAM, request);
		if(paramVal == RequestUtils.NOT_FOUD){
			paramVal = getSessionSourceParam(request);
		}
		if(paramVal != RequestUtils.NOT_FOUD){
			final PortalOrderSource source = PortalOrderSource.getById(Integer.valueOf(paramVal));
			if(source != null){
				form.setPortalOrderSource(source);
			}
		}
	}
	
	protected int getSessionSourceParam(HttpServletRequest request){
		final String val = (String)request.getSession().getAttribute(SESSION_SOURCE_KEY);
		if(val != null ){
			return Integer.valueOf(val);
		}
		return RequestUtils.NOT_FOUD;
	}
	
	
	protected void setSessionSourceParam(HttpServletRequest request){
		final String source = request.getParameter(SOURCE_PARAM);
		if(StringUtils.isNotBlank(source)){
			request.getSession().setAttribute(SESSION_SOURCE_KEY, source);
		}
	}
	
}
