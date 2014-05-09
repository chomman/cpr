package cz.nlfnorm.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import cz.nlfnorm.enums.OnlinePublication;
import cz.nlfnorm.enums.PortalProductInterval;
import cz.nlfnorm.enums.PortalProductType;
import cz.nlfnorm.web.controllers.fontend.PortalModuleWebpageController;


@Entity
@Table(name="portal_product")
@SequenceGenerator(name = "portal_service_id_seq", sequenceName = "portal_service_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
public class PortalProduct extends AbstractEntity {
	
	private static final long serialVersionUID = -6504356044793122646L;
	
	private String czechName;
	private String englishName;
	private BigDecimal priceCzk;
	private BigDecimal priceEur;
	private String descriptionCzech;
	private String descriptionEnglish;
	private Boolean deleted;
	private PortalProductInterval portalProductInterval;
	private Integer intervalValue;
	private PortalProductType portalProductType;
	private OnlinePublication onlinePublication;
	
	public PortalProduct(){
		setEnabled(true);
		deleted = false;
		portalProductInterval = PortalProductInterval.YEAR;
		portalProductType = PortalProductType.REGISTRATION;
		intervalValue = 1;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "portal_service_id_seq")
	@Override
	public Long getId(){
		return super.getId();
	}
	
	@Length(min = 3, max = 150, message = "{error.portalService.name}")
	@Column(name = "czech_name", length = 150)
	public String getCzechName() {
		return czechName;
	}

	public void setCzechName(String czechName) {
		this.czechName = czechName;
	}
	
	@Length(max = 150)
	@Column(name = "english_name", length = 150)
	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	@NotNull(message = "{error.portalService.price}")
	@Range(min = 0, max = 100000, message = "{error.portalService.price.range}")
	@Column(precision = 6, name = "price_czk")
	public BigDecimal getPriceCzk() {
		return priceCzk;
	}
	
	public void setPriceCzk(BigDecimal priceCzk) {
		this.priceCzk = priceCzk;
	}

	@NotNull(message = "{error.portalService.price}")
	@Range(min = 0, max = 100000, message = "{error.portalService.price.range}")
	@Column(precision = 6, name = "price_eur")
	public BigDecimal getPriceEur() {
		return priceEur;
	}

	public void setPriceEur(BigDecimal priceEur) {
		this.priceEur = priceEur;
	}
	
	
	
	@Column(name = "description_czech")
	@Type(type = "text")
	public String getDescriptionCzech() {
		return descriptionCzech;
	}


	public void setDescriptionCzech(String descriptionCzech) {
		this.descriptionCzech = descriptionCzech;
	}
	
	@Column(name = "description_english")
	@Type(type = "text")
	public String getDescriptionEnglish() {
		return descriptionEnglish;
	}

	public void setDescriptionEnglish(String descriptionEnglish) {
		this.descriptionEnglish = descriptionEnglish;
	}
	
	@Column(nullable = false)
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	

	@Enumerated(value = EnumType.STRING)
	@Column(name = "interval_type", length = 10)
	public PortalProductInterval getPortalProductInterval() {
		return portalProductInterval;
	}

	public void setPortalProductInterval(PortalProductInterval portalProductInterval) {
		this.portalProductInterval = portalProductInterval;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "portal_product_type", length = 12)
	public PortalProductType getPortalProductType() {
		return portalProductType;
	}

	public void setPortalProductType(PortalProductType portalProductType) {
		this.portalProductType = portalProductType;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "online_publication", length = 20)
	public OnlinePublication getOnlinePublication() {
		return onlinePublication;
	}

	public void setOnlinePublication(OnlinePublication onlinePublication) {
		this.onlinePublication = onlinePublication;
	}

	@Range(min = 1, max = 100, message = "{error.portalProduct.reange.invalid}")
	@Column(name = "interval_value")
	public Integer getIntervalValue() {
		return intervalValue;
	}

	public void setIntervalValue(Integer intervalValue) {
		this.intervalValue = intervalValue;
	}

	@Override
	@Transient
	public String getCode() {
		return null;
	}
	
	
	@Transient
	public String getNameCzech() {
		return czechName;
	}
	@Transient
	public String getNameEnglish(){
		return englishName;
	}
	
	@Transient
	public String getPublicationUrl(){
		return PortalModuleWebpageController.ONLINE_PUBLICATION_URL.replace("{pid}", getId().toString());
	}
	
}
