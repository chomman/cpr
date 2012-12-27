package sk.peterjurkovic.cpr.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping
public class AccessController extends SupportController {
	 
	
	
	 @RequestMapping(value = "/login", method = RequestMethod.GET)
	 public String getLoginPage(@RequestParam(value="error", required=false) boolean error,   ModelMap model) {
	  logger.debug("Received request to show login page");
	 
	  if (error == true) {
	   model.put("error", "You have entered an invalid username or password!");
	  } else {
	   model.put("error", "");
	  }
	  return "/public/login";
	 }
	 
	 
	 @RequestMapping(value = "/admin/login", method = RequestMethod.GET)
	 public String getAdminLoginPage(@RequestParam(value="error", required=false) boolean error,   ModelMap model) {
	  logger.debug("Received request to show login page");
	 
	  if (error == true) {
	   model.put("error", "You have entered an invalid username or password!");
	  } else {
	   model.put("error", "");
	  }
	  return "/admin/login";
	 }
	 
	 
	 
	 
	 @RequestMapping(value = "/denied", method = RequestMethod.GET)
	  public String getDeniedPage() {
	  logger.debug("Received request to show denied page");
	  return "denied";
	 }
}
