package sk.peterjurkovic.cpr.controllers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.enums.UserOrder;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.utils.RequestUtils;
import sk.peterjurkovic.cpr.utils.UserUtils;
import sk.peterjurkovic.cpr.validators.admin.UserValidator;
import sk.peterjurkovic.cpr.web.editors.DateTimeEditor;
import sk.peterjurkovic.cpr.web.forms.admin.UserForm;
import sk.peterjurkovic.cpr.web.pagination.PageLink;
import sk.peterjurkovic.cpr.web.pagination.PaginationLinker;

@Controller
public class UserController extends SupportAdminController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private DateTimeEditor dateTimeEditor;
	@Autowired
	private UserValidator userValidator;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public UserController(){
		setEditFormView("user-add");
	}
	
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(DateTime.class, this.dateTimeEditor);
    }
	
	
	
	@RequestMapping("/admin/users")
    public String showArticlePage(ModelMap modelMap, HttpServletRequest request) {
		setTableItemsView("users");
		Map<String, Object> model = new HashMap<String, Object>();
		int currentPage = RequestUtils.getPageNumber(request);
		Map<String, Object> params = RequestUtils.getRequestParameterMap(request);
		List<PageLink>paginationLinks = getPaginationItems(request, params, currentPage);
		List<User> users = userService.getUserPage(currentPage, params);
		model.put("users", users);
		model.put("paginationLinks", paginationLinks);
		model.put("orders", UserOrder.getAll());
		model.put("tab", 1);
		model.put("params", params);
		modelMap.put("model", model);
        return getTableItemsView();
    }
	
	@RequestMapping("/admin/user/add")
	public String showCreateForm(ModelMap modelMap, HttpServletRequest request){
		UserForm form = new UserForm();
		User user = new User();
		user.setId(0L);
		form.setUser(user);
		form.addRole(userService.getAllAuthorities());
		prepareModel(modelMap,  form);
		return getEditFormView();
	}
	
	
	@RequestMapping("/admin/user/edit/{userId}")
	public String showEditForm(@PathVariable Long userId, ModelMap modelMap, HttpServletRequest request){
		setEditFormView("user-edit");
		UserForm form = new UserForm();
		prepareEditForm(form, userId);
		prepareModel(modelMap,  form);
		return getEditFormView();
	}
	
	public String processSubmit(@PathVariable Long userId, @Valid UserForm form, BindingResult result, ModelMap model){
		if(userId != 0){
			setEditFormView("user-edit");
		}
		userValidator.validate(result, form);
		if(!result.hasErrors()){
			
		}
		return getEditFormView();
	}
	
	private void createOrUpdate(UserForm form){
		User user = null;
		if(form.getUser().getId() == null || form.getUser().getId() == 0){
			user = new User();
	         user.setPassword(passwordEncoder.encodePassword( user.getPassword(), null ));
		}else{
			user = userService.getUserById(form.getUser().getId());
			if(user == null){
				createItemNotFoundError();
			}
		}
		
		User loggerUser = UserUtils.getLoggedUser();
		if(!loggerUser.isSuperAdminUser() && !loggerUser.equals(user)){
			createAccessDenied();
			throw new AccessDeniedException("PŘÍSTUP ODMÍTNUT.");
		}
		
		
	}
	
	private  List<PageLink> getPaginationItems(HttpServletRequest request, Map<String, Object> params,int currentPage){
		PaginationLinker paginger = new PaginationLinker(request, params);
		paginger.setUrl("/admin/users");
		paginger.setCurrentPage(currentPage);
		paginger.setRowCount( userService.getCountOfUsers(params).intValue() );
		return paginger.getPageLinks(); 
	}
	
	private void prepareModel(ModelMap modelMap, UserForm form){
		Map<String, Object> model = new HashMap<String, Object>();
		modelMap.addAttribute("userForm", form);
		model.put("tab", 2);
		modelMap.put("model", model);
	}
	
	
	private void prepareEditForm(UserForm form, Long userId){
		User user = userService.getUserById(userId);
		if(user == null){
			createItemNotFoundError();
		}
		User loggerUser = UserUtils.getLoggedUser();
		if(!loggerUser.isSuperAdminUser() && !loggerUser.equals(user)){
			createAccessDenied();
			throw new AccessDeniedException("PŘÍSTUP ODMÍTNUT.");
		}
		form.setUser(user);
		form.addRole(userService.getAllAuthorities());
	}

}
