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
		if(replacedStandard == null){
			replacedStandard = new Standard();
			replacedStandard.setCode(current.getCode());
			replacedStandard.setStandardStatus(StandardStatus.CANCELED_HARMONIZED);
		}
		replacedStandard.setCzechName(current.getCzechName());
		replacedStandard.setEnglishName(current.getEnglishName());
	}
	
	public void updateReferences(){
		if(replacedStandard != null){
			current.setReplaceStandard(replacedStandard);
			replacedStandard.setReplaceStandard(current);
		}
	}
}
