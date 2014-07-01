package cz.nlfnorm.quasar.dto;

import cz.nlfnorm.quasar.entities.AuditorEacCode;

public class EvaluatedEacCode extends EvaludatedCode{
	
	private AuditorEacCode auditorEacCode;
	
	public EvaluatedEacCode(final AuditorEacCode code){
		this.auditorEacCode = code;
	}

	public AuditorEacCode getAuditorEacCode() {
		return auditorEacCode;
	}

	public void setAuditorEacCode(AuditorEacCode auditorEacCode) {
		this.auditorEacCode = auditorEacCode;
	}
	
	
}
