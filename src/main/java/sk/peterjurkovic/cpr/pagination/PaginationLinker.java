package sk.peterjurkovic.cpr.pagination;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import sk.peterjurkovic.cpr.constants.Constants;

public class PaginationLinker {
	
	Logger logger = Logger.getLogger(getClass());

	private List<PageLink> navigationLinks = new ArrayList<PageLink>();
	
	private String url = "";
		
	private String encodedParams;
	
	private int currentPage; 
	
	private int rowCount;
	
	private int pageSize = Constants.PAGINATION_PAGE_SIZE;
	
	private int countOfNeighbors = 4;
	
	private int totalPages;
	
	
	public PaginationLinker(HttpServletRequest request,Map<String, Object> params){
		encodeUrlParams(params);
	}
	
	/**
	 * Funkcia ktora vytvori polozky strankovania. 
	 * 
	 * Strnakovanie je vo formaate: 
	 * 
	 * 	< 1 .. 4 5 5 6 7 <b> 8 </b>  6 8 7 9 5... 20>  
	 * kde 8 je aktualna stranka. Pocet susednych cisiel je definovany v 
	 * premennej countOfNeighbors.
	 * 
	 * @return List<PageLink>
	 */
	public List<PageLink> getPageLinks(){
		if(rowCount <= pageSize){
			return null;
		}
		setTotalPages();	
		if(currentPage != 1){
			navigationLinks.add(createLink(currentPage - 1, "&laquo;"));
		}
		
		navigationLinks.add(createLink(1, "1"));
		
		
		int start = 2;
		if(currentPage - countOfNeighbors - 1 > 0){
			start = currentPage - countOfNeighbors;
		}
		
		for(int pageNumber = start; pageNumber <= currentPage; pageNumber++){
			navigationLinks.add(createLink(pageNumber, pageNumber+""));
		}
		
		int stop = totalPages;
		if(currentPage + countOfNeighbors + 1 <= totalPages){
			stop = currentPage + countOfNeighbors + 1;
		}
		
		for(int pageNumber = currentPage +1; pageNumber < stop; pageNumber++  ){
			navigationLinks.add(createLink(pageNumber, pageNumber+""));
		}
		
		
		
		if(currentPage != totalPages){
			navigationLinks.add(createLink(totalPages, totalPages + ""));
			navigationLinks.add(createLink(currentPage + 1, "&raquo;"));
		}
		
		
		return navigationLinks;
	}
	
	
	 /**
	  * Z mapy parametrov vytvori url, pricom PAGE_PARAM_NAME je vynechany.
	  * Vysledny retazec je ulozeny v encodedParams, ktory sa nasledne pridava do odkazov pri strankovani.
	  * 
	  * result e.g:
	  * order=5&amp;name=text
	  * 
	  * @param Map<String, Object>  mapa parametrov
	  */
	 private void encodeUrlParams(Map<String, Object> params) {
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
	
	
	
	private PageLink createLink(int page, String anchor){
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
		return totalPages;
	}
	
	public void setTotalPages(){
		totalPages = (int) Math.ceil(((double)rowCount) / pageSize);
	}

	public List<PageLink> getNavigationLinks() {
		return navigationLinks;
	}

	public void setNavigationLinks(List<PageLink> navigationLinks) {
		this.navigationLinks = navigationLinks;
	}

	public String getEncodedParams() {
		return encodedParams;
	}

	public void setEncodedParams(String encodedParams) {
		this.encodedParams = encodedParams;
	}

	public int getCountOfNeighbors() {
		return countOfNeighbors;
	}

	public void setCountOfNeighbors(int countOfNeighbors) {
		this.countOfNeighbors = countOfNeighbors;
	}
	 
	
	
}
