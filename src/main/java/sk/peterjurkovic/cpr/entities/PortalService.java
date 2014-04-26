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

@Entity
@Table(name="portal_service")
@SequenceGenerator(name = "portal_service_id_seq", sequenceName = "portal_service_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
public class PortalService extends AbstractEntity {
	
	private static final long serialVersionUID = -6504356044793122646L;
	
	private String czechName;
	private String englishName;
	private BigDecimal price;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "portal_service_id_seq")
	@Override
	public Long getId(){
		return super.getId();
	}
	
	@Column(name = "czech_name", length = 150)
	public String getCzechName() {
		return czechName;
	}
	
	public void setCzechName(String name) {
		this.czechName = name;
	}
	
	@Column(name = "english_name", length = 150)
	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	
	@Override
	@Transient
	public String getCode() {
		throw new UnsupportedOperationException("not supported");
	}
	
}
