package cz.nlfnorm.dto;

import cz.nlfnorm.entities.Webpage;

public class WebpageSearchDto {
	
	private String title;
	private String description;
	private Webpage webpage;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Webpage getWebpage() {
		return webpage;
	}

	public void setWebpage(Webpage webpage) {
		this.webpage = webpage;
	}
	
	
}
