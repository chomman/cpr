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

import cz.nlfnorm.entities.ExceptionLog;
import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.services.ExceptionLogService;
import cz.nlfnorm.utils.RequestUtils;
import cz.nlfnorm.web.pagination.PageLink;
import cz.nlfnorm.web.pagination.PaginationLinker;

/**
 * Controller pre srpacovavanie poziadaviek tykajucich sa logov vynimiek
 * 
 * @author Peter Jurkovic
 *
 */
@Controller
public class ExceptionLogController extends AdminSupportController {

	@Autowired
	private ExceptionLogService exceptionLogService;
	
	
	/**
	 * Zobrazi JSP stranku s nastrankovanymi evidovanymi vynimkami
	 * 
	 * @param ModelMap model
	 * @param HttpServletRequest request
	 * @return String view
	 */
	@RequestMapping("/admin/settings/exceptions")
    public String showLogPage(ModelMap modelMap, HttpServletRequest request) {
		setTableItemsView("settings-exceptions");
		Map<String, Object> model = new HashMap<String, Object>();
		int currentPage = RequestUtils.getPageNumber(request);
		Map<String, Object> params = RequestUtils.getRequestParameterMap(request);
		List<PageLink>paginationLinks = getPaginationItems(request, params, currentPage);
		List<ExceptionLog> exceptions = exceptionLogService.getExceptionLogPage(currentPage, params);
		
		if(request.getParameter("successDelete") != null){
			model.put("successDelete", true);
		}
		
		model.put("exceptions", exceptions);
		model.put("paginationLinks", paginationLinks);
		model.put("tab", 5);
		model.put("params", params);
		modelMap.put("model", model);
        return getTableItemsView();
    }
	
	
	/**
	 * Zobrazi detail vynimky
	 * 
	 * @param id
	 * @param modelMap
	 * @return
	 * @throws ItemNotFoundException, ak polozska s danym ID neexistuje
	 */
	@RequestMapping("/admin/settings/exceptions/{id}")
	public String showExceptionLogDetail(@PathVariable Long id, ModelMap modelMap) throws ItemNotFoundException{
		setViewName("settings-exception-detail");
		ExceptionLog exception = exceptionLogService.getExceptionLogById(id);
		
		if(exception == null){
			throw new ItemNotFoundException();
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("exception", exception);
		modelMap.put("model", model);
		return getViewName();
	}
	
	/**
	 * Odstrani polozku so systemu
	 * 
	 * 
	 * @param Identifikator danej polozky
	 * @return presmeruje na zoznam evidovanych vynimiek
	 * @throws ItemNotFoundException, ak polozska s danym ID neexistuje
	 */
	@RequestMapping("/admin/settings/exceptions/delete/{id}")
	public String removeExceptionLog(@PathVariable Long id) throws ItemNotFoundException{
		ExceptionLog exception = exceptionLogService.getExceptionLogById(id);
		
		if(exception == null){
			throw new ItemNotFoundException();
		}
		exceptionLogService.deleteException(exception);
		return "redirect:/admin/settings/exceptions?successDelete=1";
	}
	
	
	
	private  List<PageLink> getPaginationItems(HttpServletRequest request, Map<String, Object> params,int currentPage){
		PaginationLinker paginger = new PaginationLinker(request, params);
		paginger.setUrl("/admin/settings/exceptions");
		paginger.setCurrentPage(currentPage);
		paginger.setRowCount( exceptionLogService.getCountOfLogs(params).intValue() );
		return paginger.getPageLinks(); 
	}
}	
