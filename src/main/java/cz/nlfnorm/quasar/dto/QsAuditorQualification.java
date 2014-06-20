package cz.nlfnorm.quasar.dto;

import java.util.List;

import cz.nlfnorm.quasar.entities.Auditor;

public class QsAuditorQualification extends AbstractQualification {

	private boolean trainingRequiremets;
	private boolean auditingRequiremets;
	private boolean mostRecentYearActivity;
	private int auditdaysRecentYear;
	private int trainingHourRecentYear;
	
	public QsAuditorQualification(Auditor auditor){
		super(auditor);
	}
	
	private List<QsAuditorQualification> qualifications;

	public boolean isTrainingRequiremets() {
		return trainingRequiremets;
	}

	public void setTrainingRequiremets(boolean trainingRequiremets) {
		this.trainingRequiremets = trainingRequiremets;
	}

	public boolean isAuditingRequiremets() {
		return auditingRequiremets;
	}

	public void setAuditingRequiremets(boolean auditingRequiremets) {
		this.auditingRequiremets = auditingRequiremets;
	}

	public boolean isMostRecentYearActivity() {
		return mostRecentYearActivity;
	}

	public void setMostRecentYearActivity(boolean mostRecentYearActivity) {
		this.mostRecentYearActivity = mostRecentYearActivity;
	}

	public int getAuditdaysRecentYear() {
		return auditdaysRecentYear;
	}

	public void setAuditdaysRecentYear(int auditdaysRecentYear) {
		this.auditdaysRecentYear = auditdaysRecentYear;
	}

	public int getTrainingHourRecentYear() {
		return trainingHourRecentYear;
	}

	public void setTrainingHourRecentYear(int trainingHourRecentYear) {
		this.trainingHourRecentYear = trainingHourRecentYear;
	}

	public List<QsAuditorQualification> getQualifications() {
		return qualifications;
	}

	public void setQualifications(List<QsAuditorQualification> qualifications) {
		this.qualifications = qualifications;
	}
	
	
	
}
