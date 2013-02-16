package sk.peterjurkovic.cpr.web.forms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import sk.peterjurkovic.cpr.entities.DeclarationOfPerformance;
import sk.peterjurkovic.cpr.entities.EssentialCharacteristic;
import sk.peterjurkovic.cpr.entities.Requirement;
import sk.peterjurkovic.cpr.services.RequirementService;

public class DeclarationOfPerformanceForm {
	
	@Autowired
	private RequirementService requirementService;
	
	private DeclarationOfPerformance declarationOfPerformance;
		
	private List<EssentialCharacteristic> characteristics = new ArrayList<EssentialCharacteristic>();
	

	public DeclarationOfPerformance getDeclarationOfPerformance() {
		return declarationOfPerformance;
	}

	public void setDeclarationOfPerformance(
			DeclarationOfPerformance declarationOfPerformance) {
		this.declarationOfPerformance = declarationOfPerformance;
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
	
	

	public Set<EssentialCharacteristic> getEssentialCharacteristics(){
		Set<EssentialCharacteristic> items = new HashSet<EssentialCharacteristic>();
			for(EssentialCharacteristic item : characteristics){
				item.setRequirement(requirementService.getRequirementById(item.getRequirement().getId()));
			}
		return items;
	}
	
	
}
