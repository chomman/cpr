package cz.nlfnorm.web.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.utils.UserUtils;
import cz.nlfnorm.web.controllers.admin.AdminSupportController;


@Controller
public class AccessController extends AdminSupportController {
	 
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
