package cz.nlfnorm.web.controllers.admin.portal;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.Authority;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.entities.UserOnlinePublication;
import cz.nlfnorm.enums.UserOrder;
import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.services.PortalOrderService;
import cz.nlfnorm.services.PortalProductService;
import cz.nlfnorm.services.PortalUserService;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.utils.RequestUtils;
import cz.nlfnorm.utils.ValidationsUtils;
import cz.nlfnorm.web.controllers.admin.AdminSupportController;
import cz.nlfnorm.web.editors.LocalDateEditor;

@Controller
public class PortalUserController extends AdminSupportController {
	
	private final static String EDIT_MAPPING_URL = "/admin/portal/user/{userId}";
	private final static String LIST_MAPPING_URL = "/admin/portal/users";
	
	@Autowired
	private PortalProductService portalProductService;
	@Autowired
	private PortalOrderService portalOrderService;
	@Autowired
	private PortalUserService portalUserService;
	@Autowired
	private UserService userService;
	@Autowired
	private LocalDateEditor localDateEditor;
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		binder.registerCustomEditor(LocalDate.class, this.localDateEditor);
	}
	
	public PortalUserController(){
		setTableItemsView("portal/user-list");
		setEditFormView("portal/user-edit");
	}
	
	@RequestMapping(LIST_MAPPING_URL)
	public String handlePortalUserList(HttpServletRequest request,  ModelMap modelMap){
		Map<String, Object> model = new HashMap<String, Object>();
		int currentPage = RequestUtils.getPageNumber(request);
		Map<String, Object> params = RequestUtils.getRequestParameterMap(request);
		params.put("authority", Authority.ROLE_PORTAL_USER);
		PageDto page = userService.getUserPage(currentPage, params);
		if(page.getCount() > 0){
			model.put("paginationLinks", getPaginationItems(request,params, page.getCount(), LIST_MAPPING_URL));
			model.put("users", page.getItems());
		}
		model.put("orders", UserOrder.getAll());
		model.put("tab", 2);
		model.put("params", params);
		modelMap.put("model", model);
		return getTableItemsView();
	}
	
	
	
	@RequestMapping(value = EDIT_MAPPING_URL, method = RequestMethod.GET)
	public String handlePortalUserEdit(@PathVariable Long userId, ModelMap map, HttpServletRequest request) throws ItemNotFoundException{
		if(isSucceded(request)){
			appendSuccessCreateParam(map);
		}
		prepareModel(map, getUser(userId));
		return getEditFormView();
	}
	
	
	
	@RequestMapping(value = EDIT_MAPPING_URL, method = RequestMethod.POST)
	public String processUserEdit(@Valid @ModelAttribute("user") User form, BindingResult result, ModelMap map, HttpServletRequest request) throws ItemNotFoundException{
		validate(form, result);
		if(result.hasErrors()){
			prepareModel(map, form);
			return getEditFormView();
		}
		form = updateBasicInfo(form);
		map.put(SUCCESS_CREATE_PARAM, true);
		prepareModel(map, form);
		return getEditFormView();
	}
	
	@RequestMapping(value = "/admin/portal/user/{userId}/validity", method = RequestMethod.GET)
	public String updateValidity(@PathVariable Long userId, @RequestParam LocalDate date, @RequestParam Long id, ModelMap map, HttpServletRequest request) throws ItemNotFoundException{
		User user = getUser(userId);
		if(id == 0){
			user.setRegistrationValidity(date);
		}else{
			UserOnlinePublication uop = user.getUserOnlineUplication(id);
			if(uop == null){
				throw new ItemNotFoundException("Uživatel: " + user.getFirstName()+ " " + user.getLastName() + " nemá přiřazenou publikaci [id="+id+"] ");
			}
			uop.setValidity(date);
		}
		portalUserService.syncUser(user);
		userService.createOrUpdateUser(user);
		map.put("validityChanged", true);
		return handlePortalUserEdit(userId, map, request);
	}
	
	@RequestMapping("/admin/portal/user/{userId}/sync")
	public String updateValidity(@PathVariable Long userId, ModelMap map, HttpServletRequest request) throws ItemNotFoundException{
		User user = getUser(userId);
		user.getUserInfo().setSynced(true);
		userService.updateUser(user);
		portalUserService.syncUser(user);
		return "redirect:" + EDIT_MAPPING_URL.replace("{userId}", userId.toString());
	}
	
	
	private User updateBasicInfo(User form) throws ItemNotFoundException{
		User user = getUser(form.getId());
		user.setEmail(form.getEmail());
		user.setFirstName(form.getFirstName());
		user.setLastName(form.getLastName());
		user.getUserInfo().merge(form.getUserInfo());
		userService.createOrUpdateUser(user);
		return user;
	}
	
		
	private void validate(User user, BindingResult result){
		if(!ValidationsUtils.isEmailValid(user.getEmail())){
			result.rejectValue("email", "error.email");
		}
		if(!userService.isUserNameUniqe(user.getId(), user.getEmail())){
			result.rejectValue("email", "error.email.uniqe");
		}
	}
	
	
	private void prepareModel(ModelMap map, User user) throws ItemNotFoundException{
		map.addAttribute("user", user);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("tab", 2);
		model.put("user", getUser(user.getId()) );
		model.put("portalOrders", portalOrderService.getUserOrders(user.getId()));
		map.put("model", model);
	}
	
	
	private User getUser(Long id) throws ItemNotFoundException{
		User user = userService.getUserById(id);
		if(user == null){
			throw new ItemNotFoundException("Uživatel s ID:" + id + " se v systému nenachádzí");
		}
		return user;
	}
}
