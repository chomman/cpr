package sk.peterjurkovic.cpr.controllers.admin;

import org.apache.log4j.Logger;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.exceptions.ItemNotFoundException;

/**
 * 
 * @author Peter Jurkovič email@peterjurkovic.sk
 *
 */
public class SupportAdminController {
    
	
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
    
   
   
}
