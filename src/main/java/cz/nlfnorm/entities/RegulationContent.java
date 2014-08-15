package cz.nlfnorm.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

@Embeddable
public class RegulationContent {
	
	private String nameCzech;
	private String nameEnglish;
	private String pdfUrl;
	private String description;
	
	
	
	@Column(length = 100)
	public String getNameCzech() {
		return nameCzech;
	}
	public void setNameCzech(String nameCzech) {
		this.nameCzech = nameCzech;
	}
	
	@Column(length = 100)
	public String getNameEnglish() {
		return nameEnglish;
	}
	public void setNameEnglish(String nameEnglish) {
		this.nameEnglish = nameEnglish;
	}
	@Column(length = 150)
	public String getPdfUrl() {
		return pdfUrl;
	}
	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}
	
	@Type(type = "text")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	
}
