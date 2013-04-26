package sk.peterjurkovic.cpr.web.controllers.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import sk.peterjurkovic.cpr.entities.Authority;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.enums.UserOrder;
import sk.peterjurkovic.cpr.exceptions.ItemNotFoundException;
import sk.peterjurkovic.cpr.mail.MailMessageCreator;
import sk.peterjurkovic.cpr.mail.MailSender;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.utils.RequestUtils;
import sk.peterjurkovic.cpr.utils.UserUtils;
import sk.peterjurkovic.cpr.validators.admin.UserValidator;
import sk.peterjurkovic.cpr.web.editors.DateTimeEditor;
import sk.peterjurkovic.cpr.web.forms.admin.UserForm;
import sk.peterjurkovic.cpr.web.pagination.PageLink;
import sk.peterjurkovic.cpr.web.pagination.PaginationLinker;

@Controller
@SessionAttributes("userForm")
public class UserController extends SupportAdminController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private DateTimeEditor dateTimeEditor;
	@Autowired
	private UserValidator userValidator;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private SaltSource saltSource;
	
	public UserController(){
		setEditFormView("user-add");
	}
	
	
	/**
	 * Prekovertuje String datum na objekt
	 * 
	 * @param binder
	 */
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(DateTime.class, this.dateTimeEditor);
    }
	
	
	/**
	 * Odstrani uzivatela na zaklade daneho ID, v pripade ak ma uzivatel na operaciu pravo
	 * 
	 * @param Long ID uzivatela, ktory ma byt odstraneny
	 * @param ModelMap model
	 * @param HttpServletRequestrequest
	 * @return String JSP stranka
	 * @throws ItemNotFoundException, v pripade ak odstranovany uzivatel s danym ID v systeme neexistuje.
	 */
	@RequestMapping( value = "/admin/user/delete/{userId}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable Long userId,  ModelMap modelMap, HttpServletRequest request) throws ItemNotFoundException {
						
		User user = userService.getUserById(userId);
		if(user == null){
			createUserNotFound(userId);
		}
		
		User loggedUser = UserUtils.getLoggedUser();
		if(user.equals(loggedUser)){
			modelMap.put("successDelete", 3);
		}
		else if(! loggedUser.isAdministrator()){
			modelMap.put("successDelete", 2);
		}else{
			user.clearAuthorities();
			try{
				userService.removeUser(user);
				modelMap.put("successDelete", 1);
			}catch(ConstraintViolationException ex){
				modelMap.put("successDelete", 4);
			}catch(Exception ex){
				modelMap.put("successDelete", 4);
				logger.error(ex.getMessage());
			}
		}
		return showArticlePage(modelMap, request);
	}
	
	
	/**
	 * Zobrazi nastrankovany zoznam registrovanych uzivatelov v systeme
	 * 
	 * @param ModelMap model
	 * @param HttpServletRequest request
	 * @return String jsp stranka
	 */
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
	
	
	/**
	 * Zobrazi JSP stranku s uzivatelskymi, obsahujuca evidovane uzivatelske role v systeme
	 * 
	 * @param ModelMap model
	 * @param HttpServletRequest request
	 * @return String jsp stranka
	 */
	@RequestMapping("/admin/user/roles")
    public String showRoles(ModelMap modelMap, HttpServletRequest request) {
		setTableItemsView("user-roles");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roles", userService.getAllAuthorities());
		model.put("tab", 4);
		modelMap.put("model", model);
        return getTableItemsView();
    }
	
	
	/**
	 * Zobrazi formular, pre pridanie noveh uzivatela
	 * 
	 * @param ModelMap model
	 * @param request
	 * @return String view
	 */
	@RequestMapping("/admin/user/add")
	public String showCreateForm(ModelMap modelMap, HttpServletRequest request){
		setEditFormView("user-add");
		UserForm form = createEmptyUserForm();
		form.addRoles(userService.getAllAuthorities());
		prepareModel(modelMap,  form);
		return getEditFormView();
	}
	
	
	/**
	 * Spracuje odoslany formular, a priada noveho uzivatela, v pripade ak uzival, ktory ho odoslal ma na operaciu pravo
	 * 
	 * @param UserForm form
	 * @param BindingResult result
	 * @param ModelMap model
	 * @return String view
	 * @throws ItemNotFoundException, v pripade ak uzivatel s danym ID v systeme nie je evidovany
	 */
	@RequestMapping(value = "/admin/user/add", method = RequestMethod.POST)
	public String processSubmit(@Valid UserForm form, BindingResult result, ModelMap modelMap) throws ItemNotFoundException{
		setEditFormView("user-add");
		userValidator.validate(result, form);
		if(!result.hasErrors()){
			createOrUpdate(form);
			form = createEmptyUserForm();
			modelMap.put("successUserCreate", true);
		}
		form.addRoles(userService.getAllAuthorities());
		prepareModel(modelMap,  form);
		return getEditFormView();
	}
	
	
	/**
	 * Zobrazi JSP stranku s formularom, prostrednictom ktoreho je mozne edivaat uzivatela
	 * 
	 * @param Long userId
	 * @param ModelMap model
	 * @param request
	 * @return String view
	 * @throws ItemNotFoundException, v pripade ak uzivatel s danym ID v systeme nie je evidovany
	 */
	@RequestMapping("/admin/user/edit/{userId}")
	public String showEditForm(@PathVariable Long userId, ModelMap modelMap, HttpServletRequest request) throws ItemNotFoundException{
		setEditFormView("user-edit");
		UserForm form = new UserForm();
		prepareEditForm(form, userId);
		prepareModel(modelMap,  form);
		
		return getEditFormView();
	}
	
	
	
	/**
	 * Zobazi profil prihlaseneho uzivatela
	 * 
	 * @param ModelMap model
	 * @param request
	 * @return String view
	 */
	@RequestMapping("/admin/user/profile")
	public String showProfile( ModelMap modelMap, HttpServletRequest request){
		setEditFormView("user-profile");
		User loggedUser = UserUtils.getLoggedUser();
		UserForm form = new UserForm();
		form.setUser(loggedUser);
		Map<String, Object> model = new HashMap<String, Object>();
		modelMap.addAttribute("userForm", form);
		model.put("tab", 3);
		modelMap.put("model", model);
		return getEditFormView();
	}
	
	
	
	
	/**
	 * Spracuje odoslany uzivatelsky profil a ulozi zmeny.
	 * 
	 * @param UserForm form
	 * @param BindingResultresult
	 * @param ModelMap model
	 * @return String jsp stranka
	 * @throws ItemNotFoundException, v pripade ak uzivatel s danym ID v systeme nie je evidovany
	 */
	@RequestMapping(value = "/admin/user/profile", method = RequestMethod.POST)
	public String editProfile(@ModelAttribute("userForm") UserForm form, BindingResult result, ModelMap modelMap) throws ItemNotFoundException{
		setEditFormView("user-profile");
		User loggedUser = UserUtils.getLoggedUser();
		
		User persistedUser = userService.getUserById(loggedUser.getId());
		if(persistedUser == null){
			createUserNotFound(Long.valueOf(-1));
		}
		
		
		List<Authority> list = new ArrayList<Authority>(persistedUser.getAuthoritySet());
		form.addRoles(list);
		userValidator.validate(result, form);
		if(!result.hasErrors()){
			createOrUpdate(form);
			modelMap.put("successCreate", true);
		}
		Map<String, Object> model = new HashMap<String, Object>();
		modelMap.addAttribute("userForm", form);
		model.put("tab", 3);
		modelMap.put("model", model);
		return getEditFormView();
	}
	
	
	
	
	@RequestMapping(value = "/admin/user/edit/{userId}", method = RequestMethod.POST)
	public String processSubmit(@PathVariable Long userId, @ModelAttribute("userForm") UserForm form, BindingResult result, ModelMap modelMap) throws ItemNotFoundException{
		setEditFormView("user-edit");
		userValidator.validate(result, form);
		if(!result.hasErrors()){
			createOrUpdate(form);
			modelMap.put("successCreate", true);
		}
		prepareModel(modelMap,  form);
		return getEditFormView();
	}
	
	
	/**
	 * Spracuje a odosle data pre autocomplete
	 * 
	 * @param String term
	 * @return RequestBody, jsp s obsahujuci uzivatelske mena
	 */
	@RequestMapping(value = "/admin/user/autocomplete", method = RequestMethod.GET)
	public @ResponseBody List<User>  autocomplete(@RequestBody @RequestParam("term") String query){
		return userService.autocomplateSearch(query);
	}
	
	
	
	
	private void createOrUpdate(UserForm form) throws ItemNotFoundException{
		User user = null;
		if(form.getUser().getId() == null || form.getUser().getId() == 0){
			user = new User();
	        user.setPassword(passwordEncoder.encodePassword( form.getPassword(), saltSource.getSalt(null) ));
		}else{
			user = userService.getUserById(form.getUser().getId());
			if(user == null){
				createUserNotFound(form.getUser().getId());
			}
			if(StringUtils.isNotBlank(form.getPassword()) && StringUtils.isNotBlank(form.getConfifmPassword())){
				user.setPassword(passwordEncoder.encodePassword( form.getPassword(), saltSource.getSalt(null) ));
			}
		}
		
		User loggedUser = UserUtils.getLoggedUser();
		if(!loggedUser.isAdministrator() && !loggedUser.equals(user)){
			createAccessDenied();
			throw new AccessDeniedException("PŘÍSTUP ODMÍTNUT.");
		}
			
		user.setFirstName(form.getUser().getFirstName());
		user.setLastName(form.getUser().getLastName());
		user.setEnabled(form.getUser().getEnabled());
		user.setEmail( form.getUser().getEmail() );
		user.clearAuthorities();
		user.setAuthoritySet(form.getSelectedAuthorities());
		user.setCreatedBy(loggedUser);
		user.setChanged(new DateTime());
		user.setChangedBy(loggedUser);
		userService.mergeUser(user);
		
		if(form.getSendEmail() != null && form.getSendEmail()){
			MailSender mailSender = new MailSender();
			mailSender.sendMail(user.getFirstName() + "" + user.getLastName(), user.getEmail(), "Vytvoření uživatelského účtu", 
					MailMessageCreator.newUserCreatedMessage(loggedUser, user, form.getPassword()));
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
		User formUser = form.getUser();
		if(formUser == null){
			modelMap.put("sameUser", false);
		}else{
			modelMap.put("sameUser", formUser.equals(UserUtils.getLoggedUser()));
		}
		modelMap.put("model", model);
	}
	
	
	
	
	
	private void prepareEditForm(UserForm form, Long userId) throws ItemNotFoundException{
		User user = userService.getUserById(userId);
		if(user == null){
			createUserNotFound(userId);
		}
		User loggerUser = UserUtils.getLoggedUser();
		if(!loggerUser.isAdministrator() && !loggerUser.equals(user)){
			createAccessDenied();
			throw new AccessDeniedException("PŘÍSTUP ODMÍTNUT.");
		}
		form.setUser(user);
		form.addRoles(userService.getAllAuthorities());
	}

	private UserForm createEmptyUserForm(){
		UserForm form = new UserForm();
		User user = new User();
		user.setId(0L);
		form.setUser(user);
		return form;
	}
	
	private void createUserNotFound(Long userId) throws ItemNotFoundException{
		createItemNotFoundError("Uživatel s ID: " + userId + " se v systému nenachází");
	}
}
