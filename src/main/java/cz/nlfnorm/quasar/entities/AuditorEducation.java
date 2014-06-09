package cz.nlfnorm.quasar.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import cz.nlfnorm.quasar.enums.EductionLevel;

@Embeddable
public class AuditorEducation implements Serializable{
	
	private static final long serialVersionUID = -8478865809281357051L;
	
	private EductionLevel eductionLevel = EductionLevel.BASIC;
	private FieldOfEducation education1;
	private FieldOfEducation education2;
	private FieldOfEducation postgreduateStudy;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "eduction_level", length = 12)
	public EductionLevel getEductionLevel() {
		return eductionLevel;
	}
	public void setEductionLevel(EductionLevel eductionLevel) {
		this.eductionLevel = eductionLevel;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education1_id")
	public FieldOfEducation getEducation1() {
		return education1;
	}
	public void setEducation1(FieldOfEducation education1) {
		this.education1 = education1;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education2_id")
	public FieldOfEducation getEducation2() {
		return education2;
	}
	public void setEducation2(FieldOfEducation education2) {
		this.education2 = education2;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postgreduate_study_id")
	public FieldOfEducation getPostgreduateStudy() {
		return postgreduateStudy;
	}
	public void setPostgreduateStudy(FieldOfEducation postgreduateStudy) {
		this.postgreduateStudy = postgreduateStudy;
	}
	
	
}
