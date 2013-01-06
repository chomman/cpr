package sk.peterjurkovic.cpr.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SettingsController extends SupportAdminController {
	
	
	public SettingsController(){
		setViewName("settings");
	}
	
	
	@RequestMapping
	public String showSettingsPage(){
		return getViewName();
	}
}
