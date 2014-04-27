package sk.peterjurkovic.cpr.web.controllers.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.exceptions.ItemNotFoundException;

/**
 * 
 * @author Peter Jurkovič email@peterjurkovic.sk
 *
 */
public class SupportAdminController {
    
	protected static final String SUCCESS_CREATE_PARAM = "successCreate";
	protected static final String SUCCESS_DELETE_PARAM = "successDelete";
    protected Logger logger = Logger.getLogger(getClass());
    
    private String viewName;
    private String tableItemsView;
    private String editFormView;
    private String successView;
    
    
    
    
    protected void createItemNotFoundError(String message) throws ItemNotFoundException{
    	logger.error("Item not found: " + message);
    	throw new ItemNotFoundException(message);
    }
    
    protected void createAccessDenied() {
    	logger.error(" ACCESS DENIED");
	}

   
	public String getTableItemsView() {
		return "/"+ Constants.ADMIN_PREFIX +"/"+ tableItemsView;
	}
	public void setTableItemsView(String tableItemsView) {
		this.tableItemsView = tableItemsView;
	}
	public String getEditFormView() {
		return "/"+ Constants.ADMIN_PREFIX +"/"+ editFormView;
	}
	public void setEditFormView(String editFormView) {
		this.editFormView = editFormView;
	}
	public String getSuccessView() {
		return "/"+ Constants.ADMIN_PREFIX +"/"+ successView;
	}
	public void setSuccessView(String successView) {
		this.successView = successView;
	}
	public String getViewName() {
		return "/"+ Constants.ADMIN_PREFIX +"/"+ viewName;
	}
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
    
	
	protected boolean isDeleted(HttpServletRequest request) {
		return containsParam(request, SUCCESS_DELETE_PARAM);
	}
	
	protected boolean isSucceded(HttpServletRequest request) {
		return containsParam(request, SUCCESS_CREATE_PARAM);
	}
    
    private boolean containsParam(HttpServletRequest request,  String param){
    	if(request.getParameter(param) != null){
			return true;
		}
		return false;
    }
    
    protected void appendSuccessCreateParam(ModelMap model) {
    	model.put(SUCCESS_CREATE_PARAM, true);
	}
    
    protected void appendSuccessDeleteParam(ModelMap model) {
    	model.put(SUCCESS_DELETE_PARAM, true);
	}
    
    
    public String successDeleteRedirect(String url){
    	return buildRedirect(url, SUCCESS_DELETE_PARAM);
    }
    
    public String successUpdateRedirect(String url){
    	return buildRedirect(url, SUCCESS_CREATE_PARAM);
    }
    
    private String buildRedirect(String url, String successParam){
    	return "redirect:"+ url + "?" + successParam + "=1"; 
    }
 
}
