package sk.peterjurkovic.cpr.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.validator.constraints.Length;
import org.jadira.usertype.dateandtime.joda.PersistentDateTime;
import org.joda.time.DateTime;


@Entity
@Table(name = "declaration_of_performance")
@TypeDefs( { @TypeDef(name = "jodaDateTime", typeClass = PersistentDateTime.class) })
public class DeclarationOfPerformance {
	
	private Long id;
	
	private String numberOfDeclaration;
	
	private String productId;
	
	private String serialId;
	
	private String intendedUse;
	
	private String manufacturer;

	private String authorisedRepresentative;
	
	private AssessmentSystem assessmentSystem;
	
	private Standard standard;
	
	private NotifiedBody notifiedBody;
	
	private Set<EssentialCharacteristic> essentialCharacteristics;
	
	private String signedManufacturer;
	
	private DateTime created;
	
	private String token;

	
	public DeclarationOfPerformance(){
		this.essentialCharacteristics = new HashSet<EssentialCharacteristic>();
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	
	@Length(min =1, max = 50, message = "Číslo prohlášení musí být vyplněno.")
	@Column(name = "number_of_declaration", length = 50)	
	public String getNumberOfDeclaration() {
		return numberOfDeclaration;
	}

	public void setNumberOfDeclaration(String numberOfDeclaration) {
		this.numberOfDeclaration = numberOfDeclaration;
	}

	
	@Length(min =1, max = 100, message = "Identifikátor výrobku musí být vyplněn.")
	@Column(name = "product_id", length = 100)
	public String getProductId() {
		return productId;
	}

	
	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Length(min =1, max = 100, message = "Sériové číslo výrobku musí být vyplněno.")
	@Column(name = "serial_id", length = 100, nullable = false)
	public String getSerialId() {
		return serialId;
	}


	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	@Column(name = "intended_use")
	@Type(type = "text")
	public String getIntendedUse() {
		return intendedUse;
	}


	public void setIntendedUse(String intendedUse) {
		this.intendedUse = intendedUse;
	}

	@Length(min =1, max = 255, message = "Výrobce musí být vyplněn.")
	public String getManufacturer() {
		return manufacturer;
	}


	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Length(max = 255, message = "Zplnomocněný zástupce obsahuje více než 255 znaků.")
	public String getAuthorisedRepresentative() {
		return authorisedRepresentative;
	}


	public void setAuthorisedRepresentative(String authorisedRepresentative) {
		this.authorisedRepresentative = authorisedRepresentative;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_system_id")
	public AssessmentSystem getAssessmentSystem() {
		return assessmentSystem;
	}


	public void setAssessmentSystem(AssessmentSystem assessmentSystem) {
		this.assessmentSystem = assessmentSystem;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "standard_id")
	public Standard getStandard() {
		return standard;
	}


	public void setStandard(Standard standard) {
		this.standard = standard;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notified_body_id")
	public NotifiedBody getNotifiedBody() {
		return notifiedBody;
	}


	public void setNotifiedBody(NotifiedBody notifiedBody) {
		this.notifiedBody = notifiedBody;
	}

	@OneToMany(mappedBy = "declarationOfPerformance", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	public Set<EssentialCharacteristic> getEssentialCharacteristics() {
		return essentialCharacteristics;
	}


	public void setEssentialCharacteristics(
			Set<EssentialCharacteristic> essentialCharacteristics) {
		this.essentialCharacteristics = essentialCharacteristics;
	}

	@Length(max = 255, message = "Podepsáný výrobce je chybne vyplněn.")
	public String getSignedManufacturer() {
		return signedManufacturer;
	}


	public void setSignedManufacturer(String signedManufacturer) {
		this.signedManufacturer = signedManufacturer;
	}

	@Type(type = "jodaDateTime")
	@Column(name = "created")
	public DateTime getCreated() {
		return created;
	}


	public void setCreated(DateTime created) {
		this.created = created;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	
	
}
