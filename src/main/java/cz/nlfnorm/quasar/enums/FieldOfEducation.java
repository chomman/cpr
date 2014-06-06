package cz.nlfnorm.quasar.enums;

public enum FieldOfEducation {
	
	ELECTRO_TECHNOLOGY("fieldOfEducation.electroTechnology", true, false),
	ELECTRONIC("fieldOfEducation.electrnic", true, false),
	SOFTWARE("fieldOfEducation.software", true, false),
	IT("fieldOfEducation.id", true, false),
	BIOMEDICAL("fieldOfEducation.biomedical", true, false),
	PHYSICS("fieldOfEducation.physics", true, false),
	OPTICS("fieldOfEducation.physics", true, true),
	NUCLEAR_SCIENCE("fieldOfEducation.physics", true, true),
	BIOLOGY("fieldOfEducation.biology", false, true),
	MICROBIOLOGY("fieldOfEducation.microbiology", false, true),
	CHE("fieldOfEducation.microbiology", false, true),
	
	private String code;
	private boolean activeMedicalDevives;
	private boolean nonActiveMedicalDevices;
	
	private FieldOfEducation(int id, String code, boolean active, boolean nonActive){
		this.code = code;
		this.activeMedicalDevives = active;
		this.nonActiveMedicalDevices = nonActive;
	}
	
	public String getCode() {
		return code;
	}
	public boolean isActiveMedicalDevives() {
		return activeMedicalDevives;
	}
	public boolean isNonActiveMedicalDevices() {
		return nonActiveMedicalDevices;
	}
	
	
	
}
