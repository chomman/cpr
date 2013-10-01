package sk.peterjurkovic.cpr.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "csn_id_seq", sequenceName = "csn_id_seq", initialValue = 1, allocationSize =1)
@Table(name = "csn")
public class Csn extends AbstractEntity {

	
	private static final long serialRVersionUID = -697883831104944278L;

	private Long id;
	
	private String csnId;
		
	private LocalDate published;
	
	private String classificationSymbol;
	
	private String categorySearchCode;
	
	private String czechName;
	
	private String englishName;
	
	private String ics;
	
	private CsnCategory csnCategory;
	
	private String fileName;
	
	private String catalogId;
	
	private String changeLabel;
	
	
	private Set<CsnTerminology> terminologies;
	
	public Csn(){
		this.terminologies = new HashSet<CsnTerminology>();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "csn_id_seq")
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
	
	@Column(name = "published")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	public LocalDate getPublished() {
		return published;
	}

	public void setPublished(LocalDate published) {
		this.published = published;
	}

	@Column(name = "classification_symbol", length = 10)
	public String getClassificationSymbol() {
		return classificationSymbol;
	}

	public void setClassificationSymbol(String classificationSymbol) {
		this.classificationSymbol = classificationSymbol;
	}
	
	@Column(name="czech_name")
	public String getCzechName() {
		return czechName;
	}

	public void setCzechName(String czechName) {
		this.czechName = czechName;
	}
	
	@Column(name="english_name")
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
	
	@OneToMany(mappedBy = "csn", fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE}, orphanRemoval = true)
	@OrderBy("section, title")
	public Set<CsnTerminology> getTerminologies() {
		return terminologies;
	}

	public void setTerminologies(Set<CsnTerminology> terminologies) {
		this.terminologies = terminologies;
	}
	
	@Column(name = "csnonline_id", length = 10)
	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String csnOnlineId) {
		this.catalogId = csnOnlineId;
	}

	@Column(name = "label_of_change", length = 25)
	public String getChangeLabel() {
		return changeLabel;
	}

	public void setChangeLabel(String changeLabel) {
		this.changeLabel = changeLabel;
	}
		
	@Column(name = "category_search_code", length = 4)
	public String getCategorySearchCode() {
		return categorySearchCode;
	}

	public void setCategorySearchCode(String categorySearchCode) {
		this.categorySearchCode = categorySearchCode;
	}
	
	
	
}
