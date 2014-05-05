package cz.nlfnorm.web.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HelpController extends SupportAdminController {
	
	/**
	 * Zobrazi JSP stranku s HTML napovedou
	 * 
	 * @return String jsp stranka
	 */
	@RequestMapping("/admin/help")
    public String showHelp() {
		setViewName("help");
		return getViewName();
    }
}
