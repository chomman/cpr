package cz.nlfnorm.web.controllers.fontend;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.entities.PortalCurrency;
import cz.nlfnorm.entities.PortalOrder;
import cz.nlfnorm.entities.PortalOrderItem;
import cz.nlfnorm.entities.PortalProduct;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.entities.Webpage;
import cz.nlfnorm.enums.WebpageType;
import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.exceptions.PageNotFoundEception;
import cz.nlfnorm.exceptions.PortalAccessDeniedException;
import cz.nlfnorm.services.PortalOrderService;
import cz.nlfnorm.services.PortalProductService;
import cz.nlfnorm.services.PortalUserService;
import cz.nlfnorm.utils.RequestUtils;
import cz.nlfnorm.utils.UserUtils;
import cz.nlfnorm.validators.forntend.PortalUserValidator;
import cz.nlfnorm.web.forms.portal.PortalUserForm;
import cz.nlfnorm.web.json.JsonResponse;
import cz.nlfnorm.web.json.JsonStatus;



@Controller
public class PortalWebpageController extends WebpageControllerSupport {

	private final static Long MAIN_NAV_ID = 75l;
	private final static Long SUB_NAV_ID = 84l;
	private final static Long SCOPE_ID = 104l;
	
	@Autowired
	private PortalUserValidator portalUserValidator;
	@Autowired
	private PortalUserService portalUserService;
	@Autowired
	private PortalOrderService portalOrderService;
	@Autowired
	private PortalProductService portalProductService;
	
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
	public @ResponseBody JsonResponse  processAjaxSubmit(
			@Valid @RequestBody  PortalUserForm form, 
			BindingResult result, 
			HttpServletRequest request) throws ItemNotFoundException{
				JsonResponse response = new JsonResponse();
				if(result.hasErrors()){
					response.setResult(portalUserValidator.getErrorMessages(result.getAllErrors()));
					return response;
				}
				try{
					final User user = portalUserService.createNewUser(form.toUser());
					PortalOrder order = form.toPortalOrder();
					order.setUser(user);
					order.setIpAddress(RequestUtils.getIpAddress(request));
					order.setUserAgent(RequestUtils.getUserAgent(request, 150));
					order.setReferer(RequestUtils.getReferer(request, 250));
					order.setCreatedBy(user);
					order.setOrderItems(getOrderItems(form.getPortalProductItems(), order));
					portalOrderService.create(order);
					logger.info(String.format("Objednavka bola uspesne vytvorena [oid=%1$d][uid=%2$d]", order.getId(), user.getId()));
				}catch(Exception e){
					logger.error("Objednavku sa nepodarilo vytvorit, " + form.toString(), e);
				}
				response.setStatus(JsonStatus.SUCCESS);
				return response;
	}
	
	
	private Set<PortalOrderItem> getOrderItems(List<Long> productIds, PortalOrder order){
		Set<PortalOrderItem> orderItems = new HashSet<PortalOrderItem>();
		for(Long productId : productIds){
			PortalProduct product = portalProductService.getById(productId);
			PortalOrderItem productItem = new PortalOrderItem();
			productItem.setPortalOrder(order);
			productItem.setPortalProduct(product);
			productItem.setPrice( 
					order.getCurrency().equals(PortalCurrency.CZK) ? 
				    product.getPriceCzk() :
				    product.getPriceEur() );
			orderItems.add(productItem);
		}
		return orderItems;
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
