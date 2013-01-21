package sk.peterjurkovic.cpr.controllers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.enums.UserOrder;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.utils.RequestUtils;
import sk.peterjurkovic.cpr.web.editors.DateTimeEditor;
import sk.peterjurkovic.cpr.web.pagination.PageLink;
import sk.peterjurkovic.cpr.web.pagination.PaginationLinker;

@Controller
public class UserController extends SupportAdminController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private DateTimeEditor dateTimeEditor;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(DateTime.class, this.dateTimeEditor);
    }
	
	
	
	@RequestMapping("/admin/users")
    public String showArticlePage(ModelMap modelMap, HttpServletRequest request) {
		setTableItemsView("users");
		Map<String, Object> model = new HashMap<String, Object>();
		int currentPage = RequestUtils.getPageNumber(request);
		Map<String, Object> params = RequestUtils.getRequestParameterMap(request);
		List<PageLink>paginationLinks = getPaginationItems(request, params, currentPage);
		List<User> users = userService.getUserPage(currentPage, params);
		model.put("users", users);
		model.put("paginationLinks", paginationLinks);
		model.put("orders", UserOrder.getAll());
		model.put("tab", 1);
		model.put("params", params);
		modelMap.put("model", model);
        return getTableItemsView();
    }
	
	
	
	
	private  List<PageLink> getPaginationItems(HttpServletRequest request, Map<String, Object> params,int currentPage){
		PaginationLinker paginger = new PaginationLinker(request, params);
		paginger.setUrl("/admin/users");
		paginger.setCurrentPage(currentPage);
		paginger.setRowCount( userService.getCountOfUsers(params).intValue() );
		return paginger.getPageLinks(); 
	}
}
