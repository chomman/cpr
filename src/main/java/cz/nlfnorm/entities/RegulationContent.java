package cz.nlfnorm.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

@Embeddable
public class RegulationContent {
	
	private String nameCzech;
	private String nameEnglish;
	private String pdfCzech;
	private String pdfEnglish;
	private String descriptionCzech;
	private String descriptionEnglish;
	
	public RegulationContent(){}
	
	public RegulationContent(String czechName){
		this.nameCzech = czechName;
	}
	
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
		
	@Column(length = 150, name = "pdf_czech")
	public String getPdfCzech() {
		return pdfCzech;
	}
	public void setPdfCzech(String pdfCzech) {
		this.pdfCzech = pdfCzech;
	}
	
	@Column(length = 150, name = "pdf_english")
	public String getPdfEnglish() {
		return pdfEnglish;
	}
	public void setPdfEnglish(String pdfEnglish) {
		this.pdfEnglish = pdfEnglish;
	}
	@Type(type = "text")
	@Column(name = "description_czech")
	public String getDescriptionCzech() {
		return descriptionCzech;
	}
	public void setDescriptionCzech(String description) {
		this.descriptionCzech = description;
	}
	
	@Type(type = "text")
	@Column(name = "description_english")
	public String getDescriptionEnglish() {
		return descriptionEnglish;
	}

	public void setDescriptionEnglish(String descriptionEnglish) {
		this.descriptionEnglish = descriptionEnglish;
	}
	
	
}