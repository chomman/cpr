package cz.nlfnorm.web.controllers.fontend;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.entities.PortalCurrency;
import cz.nlfnorm.entities.PortalOrder;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.entities.UserInfo;
import cz.nlfnorm.entities.Webpage;
import cz.nlfnorm.exceptions.PageNotFoundEception;
import cz.nlfnorm.services.BasicSettingsService;
import cz.nlfnorm.services.PortalOrderService;
import cz.nlfnorm.services.PortalProductService;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.utils.UserUtils;
import cz.nlfnorm.web.forms.portal.BaseUserForm;
import cz.nlfnorm.web.forms.portal.PortalOrderForm;


@Controller
public class PortalProfileWebpageController extends	PortalWebpageControllerSupport {
	
	private final static String TAB_KEY = "profileTab";

	
	@Autowired
	private UserService userService;
	@Autowired
	private PortalOrderService portalOrderService;
	@Autowired
	private BasicSettingsService basicSettingsService;
	@Autowired
	private PortalProductService portalProductService;
	
	@RequestMapping( value = { PRIFILE_URL, EN_PREFIX + PRIFILE_URL }, method = RequestMethod.GET)
	public String showProfile(ModelMap map){
		User user = userService.getUserById(UserUtils.getLoggedUser().getId());
		BaseUserForm form = new BaseUserForm();
		form.setUser(user);
		prepareModel(map, form);
		return getView();
	}
	
	@RequestMapping( value = { PRIFILE_URL, EN_PREFIX + PRIFILE_URL }, method = RequestMethod.POST)
	public String processProfileEdit(@Valid @ModelAttribute("user") BaseUserForm form, BindingResult result, ModelMap map){
		if(result.hasErrors()){
			prepareModel(map, form);
			return getViewDirectory() + "profile-edit";
		}
		
		updateProfile(form);
		map.put("successCreate" , true);
		prepareModel(map, form);
		return getView();
	}
	
	
	@RequestMapping( value = { PRIFILE_URL + "/orders", EN_PREFIX + PRIFILE_URL + "/orders" })
	public String showOrders(ModelMap map){
		Map<String, Object> model = prepareModel(webpageService.getHomePage());
		model.put("portalOrders", portalOrderService.getUserOrders(UserUtils.getLoggedUser().getId()));
		map.put(WEBPAGE_MODEL_KEY, model);
		map.put(TAB_KEY, 2);
		return getView();
	}
	
	@RequestMapping( value = { PRIFILE_URL + "/products", EN_PREFIX + PRIFILE_URL + "/products" })
	public String showActivatedProducts(ModelMap map){
		Map<String, Object> model = prepareModel(webpageService.getHomePage());
		model.put("user", userService.getUserById(UserUtils.getLoggedUser().getId()));
		map.put(WEBPAGE_MODEL_KEY, model);
		map.put(TAB_KEY, 4);
		return getView();
	}
	
	@RequestMapping( value = { PRIFILE_URL + "/order/{oid}", EN_PREFIX + PRIFILE_URL + "/order/{oid}" })
	public String showOrder(@PathVariable Long oid, ModelMap map) throws PageNotFoundEception{
		final PortalOrder portalOrder = portalOrderService.getById(oid);
		final User user = UserUtils.getLoggedUser();
		if(portalOrder == null ){
			throw new PageNotFoundEception("Order [id="+oid+"] was not found");
		}
		if(!portalOrder.getUser().equals(user)){
			throw new AccessDeniedException("User [id="+ user.getId()+ "] tryed to access to order [oid=" + oid +"] ");
		}
		Map<String, Object> model = prepareModel(webpageService.getHomePage());
		model.put("portalOrder",  portalOrder );
		model.put("settings",  basicSettingsService.getBasicSettings() );
		map.put(WEBPAGE_MODEL_KEY, model);
		map.put(TAB_KEY, 3);
		return getView();
	}
	
	@RequestMapping( value = { PRIFILE_URL + "/new-order", EN_PREFIX + PRIFILE_URL + "/new-order" })
	public String handleCreateOrderForm(ModelMap map, HttpServletRequest request){
		Map<String, Object> model = prepareModel(webpageService.getHomePage());
		model.put("portalRegistrations", portalProductService.getAllRegistrations(true));
		model.put("portalOnlinePublications", portalProductService.getAllOnlinePublications(true));
		model.put("vat", Constants.VAT);
		appendSelectedProduct(model, request);
		PortalOrderForm form = new PortalOrderForm();
		final User user = userService.getUserById(UserUtils.getLoggedUser().getId());
		form.setUser(user);
		final String currency = request.getParameter(CURRENCY_PARAM);
		if(StringUtils.isNotBlank(currency) && currency.toUpperCase().equals(PortalCurrency.EUR.getCode())){
			model.put("useEuro", true);
			form.setPortalCurrency(PortalCurrency.EUR);
		}
		map.put(WEBPAGE_MODEL_KEY, model);
		map.put(TAB_KEY, 5);
		map.addAttribute("user", form);
		return getView();
	}
	
	
	
	private void updateProfile(BaseUserForm form){
		User user = userService.getUserById(UserUtils.getLoggedUser().getId());
		Validate.notNull(user);
		user.setFirstName(form.getFirstName());
		user.setLastName(form.getLastName());
		if(user.getUserInfo() == null){
			user.setUserInfo(new UserInfo());
			user.getUserInfo().setUser(user);
		}
		user.getUserInfo().merge(form.getUserInfo());
		userService.createOrUpdateUser(user);
	}
	
	
	
	@Override
	public String getViewDirectory() {
		return super.getViewDirectory() + "profile/";
	}
	
	
	private void prepareModel(ModelMap map, BaseUserForm form){
		map.addAttribute("user", form);
		map.put(TAB_KEY, 1);
		Webpage webpage = webpageService.getHomePage();
		Map<String, Object> model = prepareModel(webpage);
		map.put(WEBPAGE_MODEL_KEY, model);
	}
	
	private String getView(){
		return getViewDirectory() + "profile";
	}
}
