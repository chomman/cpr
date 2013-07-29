package sk.peterjurkovic.cpr.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "csn")
public class Csn extends AbstractEntity {

	
	private static final long serialRVersionUID = -697883831104944278L;

	private Long id;
	
	private String csnId;
		
	private DateTime published;
	
	private String classificationSymbol;
	
	private String czechName;
	
	private String englishName;
	
	private String ics;
	
	private CsnCategory csnCategory;
	
	private String fileName;
	
	private String htmlContent;

	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "csn_id", length = 50)	
	public String getCsnId() {
		return csnId;
	}

	public void setCsnId(String csnId) {
		this.csnId = csnId;
	}

	@Type(type = "jodaDateTime")
	@Column(name = "csn_published")
	public DateTime getPublished() {
		return published;
	}

	public void setPublished(DateTime published) {
		this.published = published;
	}
	
	@Column(name = "classification_symbol", length = 10)
	public String getClassificationSymbol() {
		return classificationSymbol;
	}

	public void setClassificationSymbol(String classificationSymbol) {
		this.classificationSymbol = classificationSymbol;
	}

	public String getCzechName() {
		return czechName;
	}

	public void setCzechName(String czechName) {
		this.czechName = czechName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	
	public String getIcs() {
		return ics;
	}

	public void setIcs(String ics) {
		this.ics = ics;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "csn_category_id")
	public CsnCategory getCsnCategory() {
		return csnCategory;
	}

	public void setCsnCategory(CsnCategory csnCategory) {
		this.csnCategory = csnCategory;
	}
	
	@Column(name = "file_name", length = 64)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	@Column
	@Type(type = "text")
	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}
	
	
	
	
	
	
}
