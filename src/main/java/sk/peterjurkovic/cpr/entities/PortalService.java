package sk.peterjurkovic.cpr.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
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


@Entity
@Table(name="portal_service")
@SequenceGenerator(name = "portal_service_id_seq", sequenceName = "portal_service_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
public class PortalService extends AbstractEntity {
	
	private static final long serialVersionUID = -6504356044793122646L;
	
	private String czechName;
	private String englishName;
	private BigDecimal price;
	private String description;
	private Boolean deleted;
	
	public PortalService(){
		setEnabled(true);
		deleted = false;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "portal_service_id_seq")
	@Override
	public Long getId(){
		return super.getId();
	}
	
	@Length(min = 3, max = 150, message = "{Length.PortalService.czechName}")
	@Column(name = "czech_name", length = 150)
	public String getCzechName() {
		return czechName;
	}
	
	
	public void setCzechName(String name) {
		this.czechName = name;
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
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@Column(name = "description")
	@Type(type = "text")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(nullable = false)
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	@Transient
	public String getCode() {
		return null;
	}
	
}
