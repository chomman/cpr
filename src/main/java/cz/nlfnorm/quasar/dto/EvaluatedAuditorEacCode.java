package cz.nlfnorm.quasar.dto;

import cz.nlfnorm.quasar.entities.AuditorEacCode;

public class EvaluatedAuditorEacCode extends EvaluatedAuditorCode{
	
	private AuditorEacCode code;
	
	public EvaluatedAuditorEacCode(AuditorEacCode code){
		this.code = code;
	}
	
	public AuditorEacCode getCode() {
		return code;
	}
	public void setCode(AuditorEacCode code) {
		this.code = code;
	}
	

}
