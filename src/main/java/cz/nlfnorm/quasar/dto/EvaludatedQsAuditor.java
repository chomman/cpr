package cz.nlfnorm.quasar.dto;

import java.util.List;

import cz.nlfnorm.quasar.entities.Auditor;

public class EvaludatedQsAuditor {
	
	public EvaludatedQsAuditor(final Auditor auditor){
		this.auditor = auditor;
	}
	private Auditor auditor;
	private List<EvaluatedEacCode> codes;
	
	public Auditor getAuditor() {
		return auditor;
	}
	public void setAuditor(Auditor auditor) {
		this.auditor = auditor;
	}
	public List<EvaluatedEacCode> getCodes() {
		return codes;
	}
	public void setCodes(List<EvaluatedEacCode> codes) {
		this.codes = codes;
	}
	
}
