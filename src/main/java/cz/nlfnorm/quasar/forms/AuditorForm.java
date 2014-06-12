package cz.nlfnorm.quasar.forms;

import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.web.forms.portal.ResetPassowrdForm;

public class AuditorForm extends ResetPassowrdForm {
	
	private Auditor auditor = new Auditor();

	
	public Auditor getAuditor() {
		return auditor;
	}

	public void setAuditor(Auditor auditor) {
		this.auditor = auditor;
	}
	
	
	
}
