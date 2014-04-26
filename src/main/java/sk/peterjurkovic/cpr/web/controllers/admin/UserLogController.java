package sk.peterjurkovic.cpr.web.controllers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import sk.peterjurkovic.cpr.entities.UserLog;
import sk.peterjurkovic.cpr.enums.UserLogOrder;
import sk.peterjurkovic.cpr.services.UserLogService;
import sk.peterjurkovic.cpr.utils.RequestUtils;
import sk.peterjurkovic.cpr.web.pagination.PageLink;
import sk.peterjurkovic.cpr.web.pagination.PaginationLinker;


@Controller
public class UserLogController extends SupportAdminController {
	
	@Autowired
	private UserLogService userLogService;
	
	/**
	 * Zobrazi JSP stranku s nastrankovanymi uzivatelskymi pristupmi, v pripade ak ma uzivatel na zobrazenie pravo.
	 * 
	 * @param ModelMap model
	 * @param HttpServletRequest request
	 * @return String view
	 */
	@RequestMapping("/admin/user/logs")
    public String showLogPage(ModelMap modelMap, HttpServletRequest request) {
		setTableItemsView("user-logs");
		Map<String, Object> model = new HashMap<String, Object>();
		int currentPage = RequestUtils.getPageNumber(request);
		Map<String, Object> params = RequestUtils.getRequestParameterMap(request);
		List<PageLink>paginationLinks = getPaginationItems(request, params, currentPage);
		List<UserLog> logs = userLogService.getLogPage(currentPage, params);
		model.put("logs", logs);
		model.put("paginationLinks", paginationLinks);
		model.put("orders", UserLogOrder.getAll());
		model.put("tab",5);
		model.put("params", params);
		modelMap.put("model", model);
        return getTableItemsView();
    }
	

	private  List<PageLink> getPaginationItems(HttpServletRequest request, Map<String, Object> params,int currentPage){
		PaginationLinker paginger = new PaginationLinker(request, params);
		paginger.setUrl("/admin/user/logs");
		paginger.setCurrentPage(currentPage);
		paginger.setRowCount( userLogService.getCountOfLogs(params).intValue() );
		return paginger.getPageLinks(); 
	}
	
	
}
