package cz.nlfnorm.quasar.dto;

import java.util.List;

import cz.nlfnorm.quasar.entities.Auditor;

public class QsAuditor extends AuditorFunction {
	
	public QsAuditor(Auditor auditor){
		super(auditor);
	}
	
	private List<EvaluatedAuditorEacCode> evaluatedAuditorEacCode;

	public List<EvaluatedAuditorEacCode> getEvaluatedAuditorEacCode() {
		return evaluatedAuditorEacCode;
	}

	public void setEvaluatedAuditorEacCode(
			List<EvaluatedAuditorEacCode> evaluatedAuditorEacCode) {
		this.evaluatedAuditorEacCode = evaluatedAuditorEacCode;
	}
	
	
	
	
}
