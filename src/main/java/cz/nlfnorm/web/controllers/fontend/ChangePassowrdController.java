package cz.nlfnorm.web.controllers.fontend;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.context.ContextHolder;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.exceptions.PageNotFoundEception;
import cz.nlfnorm.exceptions.PortalAccessDeniedException;
import cz.nlfnorm.services.PortalUserService;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.utils.RequestUtils;
import cz.nlfnorm.utils.ValidationsUtils;
import cz.nlfnorm.validators.forntend.ChangePassowrdValidator;
import cz.nlfnorm.web.forms.portal.ResetPassowrdForm;

@Controller
public class ChangePassowrdController extends PortalWebpageControllerSupport {
	
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private UserService userService;
	@Autowired
	private PortalUserService portalUserService;
	@Autowired
	private ChangePassowrdValidator changePassowrdValidator;
	
	private final static String CHANGE_PASS_REQUEST_URL = "/"+  Constants.PORTAL_URL+"/zapomenute-heslo";
	private final static String RESET_PASS_URL = "/"+  Constants.PORTAL_URL+"/reset-hesla";
	private final static String REQUEST_TOKEN_PARAM = "t";
	
	
	@RequestMapping(value = { CHANGE_PASS_REQUEST_URL,  "/{lang}"+ CHANGE_PASS_REQUEST_URL }, method = RequestMethod.GET)
	public String handleChangePasswordRequest(ModelMap modelMap) throws PageNotFoundEception, PortalAccessDeniedException{
		return prepareModelAndGetView(modelMap, "forgotten-passowrd");
	}
	
	@RequestMapping(value = { CHANGE_PASS_REQUEST_URL,  "/{lang}"+ CHANGE_PASS_REQUEST_URL }, method = RequestMethod.POST)
	public String handleSubmitChangePasswordRequest(ModelMap modelMap, @RequestParam(value="email") String email, HttpServletRequest request) throws PageNotFoundEception, PortalAccessDeniedException{
		User user = null;
		if(!ValidationsUtils.isEmailValid(email)){
			modelMap.put("error", getMessage("error.email"));
		}else{
			user = userService.getUserByUsername(email.trim());
			if(user == null){
				modelMap.put("error", getMessage("error.email.nofFound"));
			}else{
				final String changePassUrl = RequestUtils.getApplicationUrlPrefix(request) + RESET_PASS_URL + "?" + REQUEST_TOKEN_PARAM +"=";
				userService.sendChangePassowrdEmail(user, changePassUrl);
				modelMap.put("success", true );
			}
		}
		modelMap.put("email", email);
		return prepareModelAndGetView(modelMap, "forgotten-passowrd");
	}
	
	
	
	@RequestMapping(value = { RESET_PASS_URL,  "/{lang}"+ RESET_PASS_URL }, method = RequestMethod.GET)
	public String handleChangePasswordResposne(ModelMap modelMap, @RequestParam(REQUEST_TOKEN_PARAM) String token) throws PageNotFoundEception, PortalAccessDeniedException{
		final User user = userService.getUserByChangePasswordRequestToken(token);
		if(user != null){
			modelMap.addAttribute("user", new ResetPassowrdForm(user));
		}
		modelMap.put("changePassUrl", CHANGE_PASS_REQUEST_URL);
		return prepareModelAndGetView(modelMap, "reset-password");
	}
	
	@RequestMapping(value = { RESET_PASS_URL,  "/{lang}"+ RESET_PASS_URL }, method = RequestMethod.POST)
	public String processResetPassowrd(
			@Valid @ModelAttribute("user") ResetPassowrdForm form, 
			BindingResult result, ModelMap modelMap, 
			@RequestParam(REQUEST_TOKEN_PARAM) String token) throws PageNotFoundEception, PortalAccessDeniedException{
		final User user = userService.getUserByChangePasswordRequestToken(token);
		if(user != null){
			Validate.notNull(user);
			if(!result.hasErrors()){
				changePassowrdValidator.validate(form, result);
			}
			if(!result.hasErrors()){
				portalUserService.changeUserPassword(form);
				user.setChangePasswordRequestDate(null);
				userService.updateUser(user);
				modelMap.put("success", true);
			}			
			modelMap.addAttribute("user", form);
		}
		modelMap.put("changePassUrl", CHANGE_PASS_REQUEST_URL);
		return prepareModelAndGetView(modelMap, "reset-password");
	}
	
	
	
	
	private String prepareModelAndGetView(final ModelMap modelMap, final String view) throws PageNotFoundEception, PortalAccessDeniedException{
		appendModel(modelMap,  prepareModel( getWebpage( Constants.PORTAL_URL ) ));
		return getViewDirectory() + view;
	}
	
	private String getMessage(final String code){
		return  messageSource.getMessage(code, null, ContextHolder.getLocale());
	}
	
	
	
}
