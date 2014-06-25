package cz.nlfnorm.quasar.dto;

import cz.nlfnorm.quasar.entities.AuditorNandoCode;

public class EvaluatedAuditorNandoCode {
	
	private boolean grated;
	private AuditorNandoCode auditorNandoCode;
	
	public EvaluatedAuditorNandoCode(AuditorNandoCode code){
		auditorNandoCode = code;
	}
	
	public boolean isGrated() {
		return grated;
	}
	public void setGrated(boolean grated) {
		this.grated = grated;
	}
	public AuditorNandoCode getAuditorNandoCode() {
		return auditorNandoCode;
	}
	public void setAuditorNandoCode(AuditorNandoCode auditorNandoCode) {
		this.auditorNandoCode = auditorNandoCode;
	}
	
	
	
}	
