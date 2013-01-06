package sk.peterjurkovic.cpr.pagination;

public class PageLink {
	
	private String url;
	
	private String anchor;
	
	public PageLink( String anchor){
		this.anchor = anchor;
	}
	
	public PageLink(String url, String anchor){
		this.url = url;
		this.anchor = anchor;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAnchor() {
		return anchor;
	}

	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}
	
	
}
