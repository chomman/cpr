package sk.peterjurkovic.cpr.web.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.utils.UserUtils;
import sk.peterjurkovic.cpr.web.controllers.admin.SupportAdminController;


@Controller
public class AccessController extends SupportAdminController {
	 
	 @RequestMapping(value = "/admin/login", method = RequestMethod.GET)
	 public String getAdminLoginPage(HttpServletRequest request,   ModelMap model) {	  
	  
		 if(StringUtils.isNotBlank(request.getParameter(Constants.FAILURE_LOGIN_PARAM_KEY))){
			 model.put(Constants.FAILURE_LOGIN_PARAM_KEY, "1");
		 }
	  
	  final User user = UserUtils.getLoggedUser();
	  if(user != null && user.isAdministrator()){
		  return "redirect:" + Constants.SUCCESS_ROLE_ADMIN_URL;
	  }
	  return Constants.ADMIN_ENTRY_POIN_REDIRECT_URL;
	 }
	 
	 
	 
	 
	 @RequestMapping(value = "/access-denied", method = RequestMethod.GET)
	  public String getDeniedPage() {
	  return "/blank/access-denied";
	 }
}
