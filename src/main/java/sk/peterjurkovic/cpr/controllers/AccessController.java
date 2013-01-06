package sk.peterjurkovic.cpr.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sk.peterjurkovic.cpr.controllers.admin.SupportAdminController;


@Controller
public class AccessController extends SupportAdminController {
	 
	
	
	 @RequestMapping(value = "/login", method = RequestMethod.GET)
	 public String getLoginPage(HttpServletRequest request,  ModelMap model) {
	  logger.debug("Received request to show login page");
	  
	  String error = request.getParameter("login_error");
	  
	  if(error != null && error.equals("1")){
		  model.put("loginError", "1");
	  }
	  
	  return "/public/login";
	 }
	 
	 
	 @RequestMapping(value = "/admin/login", method = RequestMethod.GET)
	 public String getAdminLoginPage(HttpServletRequest request,   ModelMap model) {
	  logger.debug("Received request to show login page");
	 
	  
	  return "/admin/login";
	 }
	 
	 
	 
	 
	 @RequestMapping(value = "/access-denied", method = RequestMethod.GET)
	  public String getDeniedPage() {
	  logger.debug("Received request to show denied page");
	  return "/blank/access-denied";
	 }
}
