package sk.peterjurkovic.cpr.web.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sk.peterjurkovic.cpr.utils.UserUtils;
import sk.peterjurkovic.cpr.web.controllers.admin.SupportAdminController;


@Controller
public class AccessController extends SupportAdminController {
	 
	 @RequestMapping(value = "/admin/login", method = RequestMethod.GET)
	 public String getAdminLoginPage(HttpServletRequest request,   ModelMap model) {	  
	  String error = request.getParameter("login_error");
	  
	  if(error != null && error.equals("1")){
		  model.put("loginError", "1");
	  }
	  
	  if(UserUtils.getLoggedUser() != null){
		  return "redirect:/admin/";
	  }
	  
	  return "/admin/login";
	 }
	 
	 
	 
	 
	 @RequestMapping(value = "/access-denied", method = RequestMethod.GET)
	  public String getDeniedPage() {
	  return "/blank/access-denied";
	 }
}
