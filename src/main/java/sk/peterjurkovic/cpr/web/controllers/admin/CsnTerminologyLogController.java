package sk.peterjurkovic.cpr.web.controllers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.dto.PageDto;
import sk.peterjurkovic.cpr.enums.ImportStatus;
import sk.peterjurkovic.cpr.services.CsnTerminologyLogService;
import sk.peterjurkovic.cpr.utils.RequestUtils;
import sk.peterjurkovic.cpr.web.pagination.PageLink;
import sk.peterjurkovic.cpr.web.pagination.PaginationLinker;


@Controller
public class CsnTerminologyLogController extends SupportAdminController {
	
	public static final int TAB_INDEX = 5;
	
	private static final String LOG_URL = "/admin/csn/terminology/log";
	
	@Autowired
	private CsnTerminologyLogService csnTerminologyLogService;
	
	public CsnTerminologyLogController(){
		setTableItemsView("csn/csn-terminology-log-list");
	}
	
	
	
	@RequestMapping(LOG_URL)
	private String showLogPage(HttpServletRequest request, ModelMap modelMap){
		int currentPage = RequestUtils.getPageNumber(request);
		Map<String, Object> params = RequestUtils.getRequestParameterMap(request);
		PageDto page = csnTerminologyLogService.getLogPage(currentPage, params);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("paginationLinks", getPaginationItems(request, params, currentPage, page.getCount()));
		model.put("logs", page.getItems() );
		model.put("importStatuses", ImportStatus.values());
		modelMap.put("model", model);
		return getTableItemsView();
	}
	
	
	private  List<PageLink> getPaginationItems(HttpServletRequest request, Map<String, Object> params, int currentPage, int count){
		PaginationLinker paginger = new PaginationLinker(request, params);
		paginger.setUrl(LOG_URL);
		paginger.setCurrentPage(currentPage);
		paginger.setRowCount(count);
		return paginger.getPageLinks(); 
	}
	
	
}
