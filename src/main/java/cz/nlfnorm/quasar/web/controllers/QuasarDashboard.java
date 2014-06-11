package cz.nlfnorm.quasar.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QuasarDashboard extends QuasarSupportController {
	
	public QuasarDashboard(){
		setViewName("dashboard");
	}
	
	@RequestMapping("/admin/quasar/dashboard")
	public String showDashboard(ModelMap modelMap){
		
		return getViewName();
	}
}
