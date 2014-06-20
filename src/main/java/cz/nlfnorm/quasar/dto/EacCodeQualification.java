package cz.nlfnorm.quasar.dto;

import cz.nlfnorm.quasar.entities.AuditorEacCode;

public class EacCodeQualification extends AbstractCodeQualification{
	
	private AuditorEacCode code;
	
	public AuditorEacCode getCode() {
		return code;
	}
	public void setCode(AuditorEacCode code) {
		this.code = code;
	}
	

}
