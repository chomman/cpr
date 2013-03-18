package sk.peterjurkovic.cpr.controllers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.entities.DeclarationOfPerformance;
import sk.peterjurkovic.cpr.exceptions.ItemNotFoundException;
import sk.peterjurkovic.cpr.services.DeclarationOfPerformanceService;
import sk.peterjurkovic.cpr.utils.DoPUtils;
import sk.peterjurkovic.cpr.utils.RequestUtils;
import sk.peterjurkovic.cpr.web.pagination.PageLink;
import sk.peterjurkovic.cpr.web.pagination.PaginationLinker;

@Controller
public class CprDeclarationOfPerformanceController extends SupportAdminController {
		
	public static final int CPR_TAB_INDEX = 1;
	
	@Autowired
	private DeclarationOfPerformanceService dopService;
	
	public CprDeclarationOfPerformanceController(){
		setViewName("cpr-dop-view");
		setTableItemsView("cpr-dops");
	}
	
	@RequestMapping("/admin/cpr/dop")
	public String showGeneratedDoP(HttpServletRequest request, ModelMap modelMap){
		Map<String, Object> model = new HashMap<String, Object>();
		int currentPage = RequestUtils.getPageNumber(request);
		Map<String, Object> params = RequestUtils.getRequestParameterMap(request);
		List<PageLink>paginationLinks = getPaginationItems(request, params, currentPage);
		List<DeclarationOfPerformance> dops = dopService.getDopPage(currentPage, params);
		model.put("dops", dops);
		model.put("paginationLinks", paginationLinks);
		model.put("tab", CPR_TAB_INDEX);
		model.put("params", params);
		modelMap.put("model", model);
        return getTableItemsView();
		
	}
	
	@RequestMapping("/admin/cpr/dop/{dopId}")
	public String showDetail(@PathVariable Long dopId, ModelMap modelMap) throws ItemNotFoundException{
		Map<String, Object> model = new HashMap<String, Object>();
		DeclarationOfPerformance dop = dopService.getDopById(dopId);
		if(dop == null){
			throw new ItemNotFoundException();
		}
		model.put("dop", dop);
		model.put("point7", DoPUtils.makeText(dop));
		model.put("tab", CPR_TAB_INDEX);
		modelMap.put("model", model);
        return getViewName();
		
	}
	
	@RequestMapping("/admin/cpr/dop/delete/{dopId}")
	public String delete(HttpServletRequest request, @PathVariable Long dopId, ModelMap modelMap) throws ItemNotFoundException{
		DeclarationOfPerformance dop = dopService.getDopById(dopId);
		if(dop == null){
			throw new ItemNotFoundException();
		}
		dopService.deleteDop(dop);
		modelMap.put("successDelete", true);
        return showGeneratedDoP(request, modelMap);
		
	}
	
	
	private  List<PageLink> getPaginationItems(HttpServletRequest request, Map<String, Object> params,int currentPage){
		PaginationLinker paginger = new PaginationLinker(request, params);
		paginger.setUrl("/admin/cpr/dop");
		paginger.setCurrentPage(currentPage);
		paginger.setRowCount( dopService.getCountOfDop(params).intValue() );
		return paginger.getPageLinks(); 
	}
}
