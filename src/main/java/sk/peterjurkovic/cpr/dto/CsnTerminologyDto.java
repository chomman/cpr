package sk.peterjurkovic.cpr.dto;

import java.util.List;

import sk.peterjurkovic.cpr.entities.CsnTerminology;
import sk.peterjurkovic.cpr.enums.CsnTerminologyLanguage;

public class CsnTerminologyDto {

	private List<CsnTerminology> terminologies;
	
	private CsnTerminologyLanguage language;
	
	private String htmlContent;

	public List<CsnTerminology> getTerminologies() {
		return terminologies;
	}

	public void setTerminologies(List<CsnTerminology> terminologies) {
		this.terminologies = terminologies;
	}

	public CsnTerminologyLanguage getLanguage() {
		return language;
	}

	public void setLanguage(CsnTerminologyLanguage language) {
		this.language = language;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}
	
	
	
	
}
