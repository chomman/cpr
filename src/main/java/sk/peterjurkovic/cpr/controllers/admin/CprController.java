package sk.peterjurkovic.cpr.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CprController extends SupportAdminController {

	
	public CprController(){
		setViewName("cpr-dashboard");
	}
	
	
	@RequestMapping("/admin/cpr")
    public String showPage() {
        return getViewName();
    }
}
