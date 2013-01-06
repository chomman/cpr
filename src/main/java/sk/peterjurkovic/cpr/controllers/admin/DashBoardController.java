package sk.peterjurkovic.cpr.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DashBoardController extends SupportAdminController {
    
	
	public DashBoardController(){
		setViewName("dashboard");
	}
	
    
	@RequestMapping("/admin/")
    public String showDashBoard() {
		return getViewName();
    
	}
    
}
