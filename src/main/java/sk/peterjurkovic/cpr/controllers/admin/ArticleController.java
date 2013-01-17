package sk.peterjurkovic.cpr.controllers.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ArticleController extends SupportAdminController {
	
	public ArticleController(){
		setTableItemsView("article");
		setEditFormView("article-edit");
	}
	
	
	@RequestMapping("/admin/articles")
    public String showCprGroupsPage(ModelMap modelMap, HttpServletRequest request) {

        return getTableItemsView();
    }
	
}
