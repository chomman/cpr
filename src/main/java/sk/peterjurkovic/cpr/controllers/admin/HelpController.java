package sk.peterjurkovic.cpr.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HelpController extends SupportAdminController {
	
	@RequestMapping("/admin/help")
    public String showHelp() {
		setViewName("help");
		return getViewName();
    }
}
