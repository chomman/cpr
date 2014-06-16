package cz.nlfnorm.quasar.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Decision on the assessor’s branch assignation
 * 
 * @author Peter Jurkovic
 * @date Jun 17, 2014
 */
@Embeddable
public class AuditorSpecialist {

	/* Decision on the Specialist’s branch assignation  */
	private boolean activeMedicalDeviceSpecialist;
	private boolean nonActiveMedicalDeviceSpecialist;
	private boolean inVitroDiagnosticSpecialist;
	
	
	@Column(name = "is_for_active_mdd")
	public boolean isActiveMedicalDeviceSpecialist() {
		return activeMedicalDeviceSpecialist;
	}

	public void setActiveMedicalDeviceSpecialist(
			boolean activeMedicalDeviceSpecialist) {
		this.activeMedicalDeviceSpecialist = activeMedicalDeviceSpecialist;
	}
	
	@Column(name = "is_for_non_active_mdd")
	public boolean isNonActiveMedicalDeviceSpecialist() {
		return nonActiveMedicalDeviceSpecialist;
	}

	public void setNonActiveMedicalDeviceSpecialist(
			boolean nonActiveMedicalDeviceSpecialist) {
		this.nonActiveMedicalDeviceSpecialist = nonActiveMedicalDeviceSpecialist;
	}
	
	@Column(name = "is_for_invitro_diagnostic")
	public boolean isInVitroDiagnosticSpecialist() {
		return inVitroDiagnosticSpecialist;
	}

	public void setInVitroDiagnosticSpecialist(boolean inVitroDiagnosticSpecialist) {
		this.inVitroDiagnosticSpecialist = inVitroDiagnosticSpecialist;
	}
}
