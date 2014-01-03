package sk.peterjurkovic.cpr.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import sk.peterjurkovic.cpr.entities.AssessmentSystem;
import sk.peterjurkovic.cpr.entities.CommissionDecision;
import sk.peterjurkovic.cpr.entities.Mandate;
import sk.peterjurkovic.cpr.entities.StandardGroup;

public class FilterDto {
	
	private List<StandardGroupDto> standardGroups = new ArrayList<StandardGroupDto>();
	private List<MandateDto> mandates = new ArrayList<MandateDto>();
	private List<CommissionDecisionDto> commissionDecisions = new ArrayList<CommissionDecisionDto>();
	private List<AssessmentSystemDto> assessmentSystems = new ArrayList<AssessmentSystemDto>();
	
	
	
	public void setStandardGroups(final List<StandardGroup> standardGroups) {
		if(CollectionUtils.isNotEmpty(standardGroups)){
			for(StandardGroup g : standardGroups){
				this.standardGroups.add(
						new StandardGroupDto(
							g.getId(),
							g.getCode(),
							g.getCzechName()
								)
				);
			}
		}
	}
	
	
	public void setMandates(final List<Mandate> mandates) {
		if(CollectionUtils.isNotEmpty(mandates)){
			for(Mandate m : mandates){
				this.mandates.add(
						new MandateDto(m.getId(), m.getMandateName())
				);
			}
		}
	}
	
	
	public void setCommissionDecisions(final List<CommissionDecision> commissionDecisions) {
		if(CollectionUtils.isNotEmpty(commissionDecisions)){
			for(CommissionDecision c : commissionDecisions){
				this.commissionDecisions.add(
						new CommissionDecisionDto(
								c.getId(), 
								c.getCzechLabel(),
								c.getEnglishLabel())
				);
			}
		}
		
	}
	
	
	public void setAssessmentSystems(final List<AssessmentSystem> assessmentSystems) {
		if(CollectionUtils.isNotEmpty(assessmentSystems)){
			for(AssessmentSystem as : assessmentSystems){
				this.assessmentSystems.add(
						new AssessmentSystemDto(
								as.getId(), 
								as.getName())
						);
			}
		}
		
	}


	public List<StandardGroupDto> getStandardGroups() {
		return standardGroups;
	}


	public List<MandateDto> getMandates() {
		return mandates;
	}


	public List<CommissionDecisionDto> getCommissionDecisions() {
		return commissionDecisions;
	}


	public List<AssessmentSystemDto> getAssessmentSystems() {
		return assessmentSystems;
	}
	
	
	
	
	
}
