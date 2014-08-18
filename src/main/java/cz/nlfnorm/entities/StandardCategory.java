package cz.nlfnorm.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;


@Entity
@SequenceGenerator(name = "shared_id_seq", sequenceName = "shared_id_seq", initialValue = 1, allocationSize =1)
@Table(name = "standard_category")
public class StandardCategory extends IdentifiableEntity {

	private static final long serialVersionUID = 8808493272764733087L;
	
	private String nameCzech;
	private String nameEnglish;
	private String specializationCzech;
	private String specializationEnglish;
	private String ojeuPublicationCzech;
	private String ojeuPublicationEnglish;
	private String noaoUrl;
	private String code;
	private Set<Regulation> regulations;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shared_id_seq")
	public Long getId() {
		return super.getId();
	}
	
	@NotEmpty(message = "{error.standardCategory.required}")
	@Column(name = "name_czech", length = 120)
	public String getNameCzech() {
		return nameCzech;
	}

	public void setNameCzech(String nameCzech) {
		this.nameCzech = nameCzech;
	}

	@Column(name = "name_english", length = 120)
	public String getNameEnglish() {
		return nameEnglish;
	}

	public void setNameEnglish(String nameEnglish) {
		this.nameEnglish = nameEnglish;
	}
	
	@Column(name = "specialization_czech", length = 120)
	public String getSpecializationCzech() {
		return specializationCzech;
	}

	public void setSpecializationCzech(String specializationCzech) {
		this.specializationCzech = specializationCzech;
	}
	
	@Column(name = "specialization_english", length = 120)
	public String getSpecializationEnglish() {
		return specializationEnglish;
	}

	public void setSpecializationEnglish(String specializationEnglish) {
		this.specializationEnglish = specializationEnglish;
	}

	@Type(type = "text")
	@Column(name = "ojeu_czech")
	public String getOjeuPublicationCzech() {
		return ojeuPublicationCzech;
	}

	public void setOjeuPublicationCzech(String ojeuPublicationCzech) {
		this.ojeuPublicationCzech = ojeuPublicationCzech;
	}
	
	@Type(type = "text")
	@Column(name = "ojeu_english")
	public String getOjeuPublicationEnglish() {
		return ojeuPublicationEnglish;
	}

	public void setOjeuPublicationEnglish(String ojeuPublicationEnglish) {
		this.ojeuPublicationEnglish = ojeuPublicationEnglish;
	}

	@Length(message = "{error.noaoUrl.length}", max = 250)
	@Column(name = "nando_url", length = 250)
	public String getNoaoUrl() {
		return noaoUrl;
	}

	public void setNoaoUrl(String noaoUrl) {
		this.noaoUrl = noaoUrl;
	}
	
	@Length(max = 10, message = "{error.code.length}")
	@Column(name = "code", length = 10)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "standard_category_has_regulations", joinColumns = @JoinColumn(name = "standard_category_id"), inverseJoinColumns = @JoinColumn(name = "regulation_id"))
	public Set<Regulation> getRegulations() {
		return regulations;
	}

	public void setRegulations(Set<Regulation> regulations) {
		this.regulations = regulations;
	}
	
	
}
