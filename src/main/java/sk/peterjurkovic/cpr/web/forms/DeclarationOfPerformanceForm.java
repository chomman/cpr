package sk.peterjurkovic.cpr.web.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import sk.peterjurkovic.cpr.entities.DeclarationOfPerformance;
import sk.peterjurkovic.cpr.entities.EssentialCharacteristic;
import sk.peterjurkovic.cpr.entities.Requirement;

public class DeclarationOfPerformanceForm {
	
	public static final String NPD = "NPD";
	
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
		characteristics.clear();
		for(Requirement r : requirements){
			EssentialCharacteristic item = new EssentialCharacteristic();
			item.setRequirement(r);
			if(r.getNpd()){
				item.setValue(NPD);
			}
			characteristics.add(item);
		}
	}
	public void createCharacteristics(Set<Requirement> requirements) {
		characteristics.clear();
		for(Requirement r : requirements){
			EssentialCharacteristic item = new EssentialCharacteristic();
			item.setRequirement(r);
			if(r.getNpd()){
				item.setValue(NPD);
			}
			characteristics.add(item);
		}
	}
	
	

	
	
}
