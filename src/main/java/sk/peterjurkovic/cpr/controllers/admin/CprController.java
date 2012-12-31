package sk.peterjurkovic.cpr.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.controllers.SupportController;

@Controller
public class CprController extends SupportController {

	
	@RequestMapping("/admin/cpr")
    public String showPage() {
        return "/"+ Constants.ADMIN_PREFIX +"/cpr";
    }
}
