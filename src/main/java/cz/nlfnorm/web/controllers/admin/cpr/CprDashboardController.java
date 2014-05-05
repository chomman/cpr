package cz.nlfnorm.web.controllers.admin.cpr;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.nlfnorm.web.controllers.admin.AdminSupportController;

@Controller
public class CprDashboardController extends AdminSupportController {

	public CprDashboardController(){
		setViewName("cpr/dashboard");
	}
	
	@RequestMapping("/admin/cpr")
    public String showPage() {
        return getViewName();
    }
	
}
