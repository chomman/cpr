package sk.peterjurkovic.cpr.dto;

public class LinkDto {
	
	private String anchorText;
	private String href;
	
	public String getAnchorText() {
		if(anchorText != null){
			return anchorText.trim();
		}
		return anchorText;
	}
	public void setAnchorText(String anchorText) {
		this.anchorText = anchorText;
	}
	public String getHref() {
		if(href != null){
			return href.trim();
		}
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	
	
}
