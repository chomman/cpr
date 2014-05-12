package cz.nlfnorm.web.controllers.fontend;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
import cz.nlfnorm.enums.WebpageModule;
import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.exceptions.PageNotFoundEception;
import cz.nlfnorm.services.ExceptionLogService;
import cz.nlfnorm.services.PortalOrderService;
import cz.nlfnorm.services.PortalProductService;
import cz.nlfnorm.services.PortalUserService;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.utils.RequestUtils;
import cz.nlfnorm.utils.UserUtils;
import cz.nlfnorm.validators.forntend.PortalUserValidator;
import cz.nlfnorm.web.forms.portal.PortalOrderForm;
import cz.nlfnorm.web.forms.portal.PortalUserForm;
import cz.nlfnorm.web.json.JsonResponse;
import cz.nlfnorm.web.json.JsonStatus;


@Controller
public class PortalModuleWebpageController extends PortalWebpageControllerSupport{
	
	public static final String ONLINE_PUBLICATION_URL = Constants.PORTAL_URL + "/online-publikace/{pid}";
	
	@Autowired
	private PortalOrderService portalOrderService;
	@Autowired
	private PortalProductService portalProductService;
	@Autowired
	private PortalUserService portalUserService;
	@Autowired
	private PortalUserValidator portalUserValidator;
	@Autowired
	private ExceptionLogService exceptionLogService;
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value = "/ajax/registration", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JsonResponse  processAjaxSubmit(
			@Valid @RequestBody  PortalUserForm form, 
			BindingResult result, 
			HttpServletRequest request) throws ItemNotFoundException{
			return createOrder(form, result, request);
		}
	
	@RequestMapping(value = "/ajax/order", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JsonResponse  processAjaxOrderSubmit(
			@Valid @RequestBody  PortalOrderForm form, 
			BindingResult result, 
			HttpServletRequest request) throws ItemNotFoundException{
				return createOrder(form, result, request);
	}
	
	private JsonResponse createOrder(PortalOrderForm form, 
			BindingResult result, 
			HttpServletRequest request){
		
			JsonResponse response = new JsonResponse();
			if(result.hasErrors()){
				response.setResult(portalUserValidator.getErrorMessages(result.getAllErrors()));
				return response;
			}
			try{
				User user = null;
				if(form instanceof PortalUserForm){
					 portalUserService.createNewUser(((PortalUserForm)form).toUser());
				}else{
					user = userService.getUserById( UserUtils.getLoggedUser().getId() );
				}
				Validate.notNull(user);
				PortalOrder order = form.toPortalOrder();
				order.setUser(user);
				order.setIpAddress(RequestUtils.getIpAddress(request));
				order.setUserAgent(RequestUtils.getUserAgent(request, 150));
				order.setReferer(RequestUtils.getReferer(request, 250));
				order.setCreatedBy(user);
				order.setOrderItems(getOrderItems(form.getPortalProductItems(), order));
				portalOrderService.create(order);
				portalOrderService.sendOrderCreateEmail(order);
				logger.info(String.format("Objednavka bola uspesne vytvorena [oid=%1$d][uid=%2$d]", order.getId(), user.getId()));
				response.setStatus(JsonStatus.SUCCESS);
			}catch(Exception e){
				logger.error("Objednavku sa nepodarilo vytvorit, " + form.toString(), e);
				exceptionLogService.logException(request, e);
			}
			return response;
		
	}
	
	@RequestMapping(value = {ONLINE_PUBLICATION_URL, EN_PREFIX + ONLINE_PUBLICATION_URL})
    public String onlinePublicationDetail(@PathVariable Long pid, ModelMap modelMap) throws PageNotFoundEception {
		final PortalProduct product = portalProductService.getById(pid);
		Webpage  webpage = webpageService.getWebpageByModule(WebpageModule.PUBLICATIONS);
		if(product == null || !product.isEnabled() || webpage == null){
		        throw new PageNotFoundEception();
		}
		Map<String, Object> model = prepareModel( webpage );
		model.put("portalProduct", product);
		model.put("publications", portalProductService.getAllOnlinePublications(true));
		modelMap.put(WEBPAGE_MODEL_KEY, model);
		return "/module/detail/online-publication-detail";
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
	
}
