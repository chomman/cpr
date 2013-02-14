package sk.peterjurkovic.cpr.web.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import sk.peterjurkovic.cpr.entities.AssessmentSystem;
import sk.peterjurkovic.cpr.entities.EssentialCharacteristic;
import sk.peterjurkovic.cpr.entities.NotifiedBody;
import sk.peterjurkovic.cpr.entities.Requirement;
import sk.peterjurkovic.cpr.entities.Standard;

public class DeclarationOfPerformaceForm {
	
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
		
	private List<EssentialCharacteristic> characteristics = new ArrayList<EssentialCharacteristic>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumberOfDeclaration() {
		return numberOfDeclaration;
	}

	public void setNumberOfDeclaration(String numberOfDeclaration) {
		this.numberOfDeclaration = numberOfDeclaration;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	public String getIntendedUse() {
		return intendedUse;
	}

	public void setIntendedUse(String intendedUse) {
		this.intendedUse = intendedUse;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getAuthorisedRepresentative() {
		return authorisedRepresentative;
	}

	public void setAuthorisedRepresentative(String authorisedRepresentative) {
		this.authorisedRepresentative = authorisedRepresentative;
	}

	public AssessmentSystem getAssessmentSystem() {
		return assessmentSystem;
	}

	public void setAssessmentSystem(AssessmentSystem assessmentSystem) {
		this.assessmentSystem = assessmentSystem;
	}

	public Standard getStandard() {
		return standard;
	}

	public void setStandard(Standard standard) {
		this.standard = standard;
	}

	public NotifiedBody getNotifiedBody() {
		return notifiedBody;
	}

	public void setNotifiedBody(NotifiedBody notifiedBody) {
		this.notifiedBody = notifiedBody;
	}

	public Set<EssentialCharacteristic> getEssentialCharacteristics() {
		return essentialCharacteristics;
	}

	public void setEssentialCharacteristics(
			Set<EssentialCharacteristic> essentialCharacteristics) {
		this.essentialCharacteristics = essentialCharacteristics;
	}

	public String getSignedManufacturer() {
		return signedManufacturer;
	}

	public void setSignedManufacturer(String signedManufacturer) {
		this.signedManufacturer = signedManufacturer;
	}

	public List<EssentialCharacteristic> getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(List<EssentialCharacteristic> characteristics) {
		this.characteristics = characteristics;
	}
	
	public void setCharacteristics(Set<EssentialCharacteristic> characteristics) {
		this.characteristics = new ArrayList<EssentialCharacteristic>(characteristics);
	}
	
	public void createCharacteristics(List<Requirement> requirements) {
		for(Requirement r : requirements){
			EssentialCharacteristic item = new EssentialCharacteristic();
			item.setRequirement(r);
			characteristics.add(item);
		}
	}
	
	
	
}
