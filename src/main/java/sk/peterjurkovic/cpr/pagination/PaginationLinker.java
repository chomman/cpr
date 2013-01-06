package sk.peterjurkovic.cpr.pagination;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.utils.RequestUtils;

public class PaginationLinker {
	
	Logger logger = Logger.getLogger(getClass());

	private List<PageLink> navigationLinks = new ArrayList<PageLink>();
	
	private String url = "";
		
	private String encodedParams;
	
	private int currentPage; 
	
	private int rowCount = 200;
	
	private int pageSize = Constants.PAGINATION_PAGE_SIZE;
	
	
	
	public PaginationLinker(HttpServletRequest request,Map<String, Object> params){
		currentPage = RequestUtils.getPageNumber(request);
		encodeUrlParams(params);
	}
	
	public List<PageLink> getNavigatorLinsk(){
		if(rowCount <= pageSize){
			return null;
		}
		int offset = 5;

		if(currentPage != 1){
			navigationLinks.add(createLink(currentPage - 1, "&laquo;"));
		}
		
		navigationLinks.add(createLink(1, "1"));
		
		
		int start = 2;
		if(currentPage - offset - 1 > 0){
			start = currentPage - offset;
		}
		
		for(int pageNumber = start; pageNumber <= currentPage; pageNumber++){
			navigationLinks.add(createLink(pageNumber, pageNumber+""));
		}
		
		int stop = getTotalPates();
		if(currentPage + offset + 1 < stop){
			start = currentPage + offset + 1;
		}
		
		for(int pageNumber = currentPage +1; pageNumber <= stop; pageNumber++  ){
			navigationLinks.add(createLink(pageNumber, pageNumber+""));
		}
		
		if(currentPage != stop){
			navigationLinks.add(createLink(currentPage + 1, "&raquo;"));
		}
		
		return navigationLinks;
	}
	
	
	
	 public void encodeUrlParams(Map<String, Object> params) {
	        StringBuffer item = new StringBuffer();
	        String encoding = "UTF-8";
	        try {
	            for (Iterator<String> iter = params.keySet().iterator(); iter.hasNext();) {
	                String key = iter.next();
	                if(! key.equals(Constants.PAGE_PARAM_NAME)){
		                Object value = params.get(key);
		                if (value instanceof String) {
		                    item.append(key).append("=").append(URLEncoder.encode((String)value, encoding)).append("&amp;");
		                } else if (value != null) {
		                    item.append(key).append("=").append(URLEncoder.encode(value.toString(), encoding)).append("&amp;");
		                }
	                }
	            }
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	        encodedParams =  item.toString();
	    }
	
	
	
	public PageLink createLink(int page, String anchor){
		if(page == currentPage){
			return new PageLink(anchor); 
		}
		return new PageLink(url + "?" + Constants.PAGE_PARAM_NAME + "=" + page + "&amp;" + encodedParams, anchor);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

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
	
	public int getTotalPates(){
		return (int) Math.ceil(rowCount / pageSize);
	}
	
	
	
}
