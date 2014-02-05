package sk.peterjurkovic.cpr.entities;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import sk.peterjurkovic.cpr.dto.WebpageDto;
import sk.peterjurkovic.cpr.resolvers.LocaleResolver;
import sk.peterjurkovic.cpr.utils.RequestUtils;

/**
 * Entita reprezentujuca verejnu sekciu systemu
 * 
 * @author peto
 *
 */
@Entity
@Table(name="webpage")
@SequenceGenerator(name = "webpage_id_seq", sequenceName = "webpage_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
public class Webpage extends AbstractEntity {

	
	private static final long serialVersionUID = 3981658331L;
		
	private String nameCzech;
	private String titleCzech;
	private String descriptionCzech;
	private String topTextCzech;
	private String bottomTextCzech;
	
	private String nameEnglish;
	private String titleEnglish;
	private String descriptionEnglish;
	private String topTextEnglish;
	private String bottomTextEnglish;
	
	private WebpageCategory webpageCategory;
	private WebpageContent webpageContent;
	
	public Webpage(){
		setEnabled(Boolean.FALSE);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "webpage_id_seq")
	public Long getId() {
		return super.getId();
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "webpage_category_id")
	public WebpageCategory getWebpageCategory() {
		return webpageCategory;
	}

	public void setWebpageCategory(WebpageCategory webpageCategory) {
		this.webpageCategory = webpageCategory;
	}
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "webpage_content_id")
	public WebpageContent getWebpageContent() {
		return webpageContent;
	}
	

	public void setWebpageContent(WebpageContent webpageContent) {
		this.webpageContent = webpageContent;
	}

	/* ===============================================================
	 * CZECH CONTENT
	 */
	
	@Column(name = "name", length = 100)
	@Length(min = 1, max = 100, message = "Název musí být vyplněn")
	public String getNameCzech() {
		return nameCzech;
	}

	public void setNameCzech(String nameCzech) {
		this.nameCzech = nameCzech;
	}
	
	@Column(name = "title", length = 150)
	@Length(min = 1, max = 150, message = "Titulek musí být vyplněn")
	public String getTitleCzech() {
		return titleCzech;
	}

	public void setTitleCzech(String titleCzech) {
		this.titleCzech = titleCzech;
	}
	
	@Column(name = "description", length = 255)
	@Length(max = 255, message = "Překročili jste délku popisku")
	public String getDescriptionCzech() {
		return descriptionCzech;
	}

	public void setDescriptionCzech(String descriptionCzech) {
		this.descriptionCzech = descriptionCzech;
	}
	
	@Type(type = "text")
	@Column(name = "top_text")
	public String getTopTextCzech() {
		return topTextCzech;
	}

	public void setTopTextCzech(String topTextCzech) {
		this.topTextCzech = topTextCzech;
	}
	
	@Type(type = "text")
	@Column(name = "bottom_text")
	public String getBottomTextCzech() {
		return bottomTextCzech;
	}

	public void setBottomTextCzech(String bottomTextCzech) {
		this.bottomTextCzech = bottomTextCzech;
	}

	/* ===============================================================
	 * ENGLISH CONTENT
	 * 
	 */
	@Column(name = "name_english", length = 100)
	@Length(max = 100)
	public String getNameEnglish() {
		return nameEnglish;
	}

	public void setNameEnglish(String nameEnglish) {
		this.nameEnglish = nameEnglish;
	}

	@Column(name = "title_english", length = 150)
	@Length(max = 150)
	public String getTitleEnglish() {
		return titleEnglish;
	}

	public void setTitleEnglish(String titleEnglish) {
		this.titleEnglish = titleEnglish;
	}
	
	@Column(name = "description_english", length = 255)
	@Length(max = 255, message = "Překročili jste délku popisku")
	public String getDescriptionEnglish() {
		return descriptionEnglish;
	}

	public void setDescriptionEnglish(String descriptionEnglish) {
		this.descriptionEnglish = descriptionEnglish;
	}
	
	@Type(type = "text")
	@Column(name = "top_text_english")
	public String getTopTextEnglish() {
		return topTextEnglish;
	}

	public void setTopTextEnglish(String topTextEnglish) {
		this.topTextEnglish = topTextEnglish;
	}
	
	
	@Type(type = "text")
	@Column(name = "bottom_text_english")
	public String getBottomTextEnglish() {
		return bottomTextEnglish;
	}

	public void setBottomTextEnglish(String bottomTextEnglish) {
		this.bottomTextEnglish = bottomTextEnglish;
	}
	
	
	/* =============================================================== 
	 * SELECTED LANG CONTENT 
	 */
	@Transient
	public String getName() {
		if(RequestUtils.isEnglishLocale() && StringUtils.isNotBlank(nameEnglish)){
			return nameEnglish;
		}
		return nameCzech;
	}
	
	@Transient
	public String getTitle() {
		if(RequestUtils.isEnglishLocale() && StringUtils.isNotBlank(titleEnglish)){
			return titleEnglish;
		}
		return titleCzech;
	}

	@Transient
	public String getDescription() {
		if(RequestUtils.isEnglishLocale() && StringUtils.isNotBlank(descriptionEnglish)){
			return descriptionEnglish;
		}
		return descriptionCzech;
	}

	@Transient
	public String getTopText() {
		if(RequestUtils.isEnglishLocale() && StringUtils.isNotBlank(topTextEnglish)){
			return topTextEnglish;
		}
		return topTextCzech;
	}
	
	@Transient
	public String getBottomText() {
		if(RequestUtils.isEnglishLocale() && StringUtils.isNotBlank(bottomTextEnglish)){
			return bottomTextEnglish;
		}
		return bottomTextCzech;
	}
	
	
	

	
}
