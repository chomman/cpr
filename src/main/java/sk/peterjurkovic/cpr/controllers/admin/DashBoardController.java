package sk.peterjurkovic.cpr.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.controllers.SupportController;


@Controller
@RequestMapping("/admin")
public class DashBoardController extends SupportController {
    
    
    @RequestMapping
    public String showDashBoard() {
        return "admin/index";
    }
    
}
