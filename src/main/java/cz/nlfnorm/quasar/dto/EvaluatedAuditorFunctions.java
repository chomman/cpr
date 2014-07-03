package cz.nlfnorm.quasar.dto;

import cz.nlfnorm.quasar.entities.Auditor;

public class EvaluatedAuditorFunctions {
	
	private Auditor auditor;
	
	private EvaluatedAuditorFunction qsAuditor;
	
	private EvaluatedAuditorFunction productAssessorA;
	
	private EvaluatedAuditorFunction productAssessorR;
	
	private EvaluatedAuditorFunction productSpecialist;

	public EvaluatedAuditorFunctions(Auditor auditor){
		this.auditor = auditor;
	}
	
	public Auditor getAuditor() {
		return auditor;
	}

	public void setAuditor(Auditor auditor) {
		this.auditor = auditor;
	}

	public EvaluatedAuditorFunction getQsAuditor() {
		return qsAuditor;
	}

	public void setQsAuditor(EvaluatedAuditorFunction qsAuditor) {
		this.qsAuditor = qsAuditor;
	}

	public EvaluatedAuditorFunction getProductAssessorA() {
		return productAssessorA;
	}

	public void setProductAssessorA(EvaluatedAuditorFunction productAssessorA) {
		this.productAssessorA = productAssessorA;
	}

	public EvaluatedAuditorFunction getProductAssessorR() {
		return productAssessorR;
	}

	public void setProductAssessorR(EvaluatedAuditorFunction productAssessorR) {
		this.productAssessorR = productAssessorR;
	}

	public EvaluatedAuditorFunction getProductSpecialist() {
		return productSpecialist;
	}

	public void setProductSpecialist(EvaluatedAuditorFunction productSpecialist) {
		this.productSpecialist = productSpecialist;
	}

	
	
}
