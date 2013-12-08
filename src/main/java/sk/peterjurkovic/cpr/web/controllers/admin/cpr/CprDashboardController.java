package sk.peterjurkovic.cpr.web.controllers.admin.cpr;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.web.controllers.admin.SupportAdminController;

@Controller
public class CprDashboardController extends SupportAdminController {

	public CprDashboardController(){
		setViewName("cpr/dashboard");
	}
	
	@RequestMapping("/admin/cpr")
    public String showPage() {
        return getViewName();
    }
	
}
