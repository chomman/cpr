package cz.nlfnorm.quasar.dto;

import cz.nlfnorm.quasar.entities.AuditorNandoCode;

public class EvaluatedAuditorNandoCode extends EvaludatedCode {
		
	private AuditorNandoCode auditorNandoCode;
	
	public EvaluatedAuditorNandoCode(AuditorNandoCode code){
		auditorNandoCode = code;
	}
	public AuditorNandoCode getAuditorNandoCode() {
		return auditorNandoCode;
	}
	public void setAuditorNandoCode(AuditorNandoCode auditorNandoCode) {
		this.auditorNandoCode = auditorNandoCode;
	}
	
}	
