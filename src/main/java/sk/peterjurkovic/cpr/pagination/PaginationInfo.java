package sk.peterjurkovic.cpr.pagination;

import sk.peterjurkovic.cpr.constants.Constants;

public class PaginationInfo {
	
	  private int currentPage; 
	  private int rowCount;
	  private int pageSize = Constants.PAGINATION_PAGE_SIZE;

	  
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	} 
	
	
	  
	  
	  
}
