package sk.peterjurkovic.cpr.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.controllers.SupportController;


@Controller
public class DashBoardController extends SupportController {
    
    
	@RequestMapping("/admin/")
    public String showDashBoard() {
        
		return "admin/dashboard";
    
	}
    
}
