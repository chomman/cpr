package sk.peterjurkovic.cpr.controllers;

import org.apache.log4j.Logger;

import sk.peterjurkovic.cpr.constants.Constants;

/**
 * @author Peter Jurkovič email@peterjurkovic.sk
 *
 */
public class SupportController {
    
	
    protected Logger logger = Logger.getLogger(getClass());
    
    
    private String tableItemsView;
    private String editFormView;
    private String successView;
    
    
    
    
    protected void createItemNotFoundError(){
	   
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
    
   
   
}
