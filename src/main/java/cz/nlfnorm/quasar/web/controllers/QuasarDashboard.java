package cz.nlfnorm.quasar.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.nlfnorm.utils.UserUtils;

@Controller
public class QuasarDashboard extends QuasarSupportController {
	
	public static final String QUASAR_DASHBOARD_URL = "/admin/quasar/dashboard";
	
	
	@RequestMapping(QUASAR_DASHBOARD_URL)
	public String showDashboard(ModelMap modelMap){
		if(UserUtils.getLoggedUser().isQuasarAdmin()){
			return getViewDir() + "dashboard" ;
		}
		return getViewDir() + "profile/dashboard" ;
	}
}
