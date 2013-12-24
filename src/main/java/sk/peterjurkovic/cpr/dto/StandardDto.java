package sk.peterjurkovic.cpr.dto;

import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.enums.StandardStatus;

public class StandardDto {

	private Standard current = new Standard();
	
	private Standard replacedStandard;
	
	private boolean createCopy = false;

	public Standard getCurrent() {
		return current;
	}

	public void setCurrent(Standard current) {
		this.current = current;
	}

	public Standard getReplacedStandard() {
		return replacedStandard;
	}

	public void setReplacedStandard(Standard replacedStandard) {
		this.replacedStandard = replacedStandard;
	}

	public boolean shouldCreateCopy() {
		return createCopy;
	}

	public void setCreateCopy(boolean createCopy) {
		this.createCopy = createCopy;
	}
	
	
	public void createCopy(){
		replacedStandard = new Standard();
		replacedStandard.setAssessmentSystems(current.getAssessmentSystems());
		replacedStandard.setCode(current.getCode());
		replacedStandard.setStandardChanges(current.getStandardChanges());
		replacedStandard.setCzechName(current.getCzechName());
		replacedStandard.setEnglishName(current.getEnglishName());
		replacedStandard.setNotifiedBodies(current.getNotifiedBodies());
		replacedStandard.setStandardGroups(current.getStandardGroups());
		replacedStandard.setStandardStatus(StandardStatus.CANCELED);
		replacedStandard.setStandardCsns(current.getStandardCsns());
	}
	
}
