package cz.nlfnorm.web.controllers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.CsnTerminologyLog;
import cz.nlfnorm.enums.ImportStatus;
import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.services.CsnTerminologyLogService;
import cz.nlfnorm.utils.RequestUtils;
import cz.nlfnorm.web.pagination.PageLink;
import cz.nlfnorm.web.pagination.PaginationLinker;


@Controller
public class CsnTerminologyLogController extends SupportAdminController {
	
	public static final int TAB_INDEX = 5;
	
	private static final String LOG_URL = "/admin/csn/terminology/log";
	
	@Autowired
	private CsnTerminologyLogService csnTerminologyLogService;
	
	public CsnTerminologyLogController(){
		setTableItemsView("csn/csn-terminology-log-list");
		setViewName("csn/csn-terminology-log-detail");
	}
	
	
	
	@RequestMapping(LOG_URL)
	private String showLogPage(HttpServletRequest request, ModelMap modelMap){
		int currentPage = RequestUtils.getPageNumber(request);
		Map<String, Object> params = RequestUtils.getRequestParameterMap(request);
		PageDto page = csnTerminologyLogService.getLogPage(currentPage, params);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("paginationLinks", getPaginationItems(request, params, currentPage, page.getCount()));
		model.put("logs", page.getItems() );
		model.put("tab", TAB_INDEX );
		model.put("importStatuses", ImportStatus.values());
		modelMap.put("model", model);
		return getTableItemsView();
	}
	
	
	@RequestMapping(LOG_URL+ "/{id}")
	private String showLogDetail(@PathVariable Long id, ModelMap modelMap) throws ItemNotFoundException{
		
		CsnTerminologyLog log = csnTerminologyLogService.getById(id);
		if(log == null){
			throw new ItemNotFoundException("Log terminologie s ID " + id + " sa v systeme nenachadza");
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("tab", TAB_INDEX );
		modelMap.put("model", model);
		modelMap.put("log", log);
		return getViewName();
	}
	
	
	private  List<PageLink> getPaginationItems(HttpServletRequest request, Map<String, Object> params, int currentPage, int count){
		PaginationLinker paginger = new PaginationLinker(request, params);
		paginger.setUrl(LOG_URL);
		paginger.setCurrentPage(currentPage);
		paginger.setRowCount(count);
		return paginger.getPageLinks(); 
	}
	
	
}
