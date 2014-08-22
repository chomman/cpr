package cz.nlfnorm.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import cz.nlfnorm.entities.AssessmentSystem;
import cz.nlfnorm.entities.CommissionDecision;
import cz.nlfnorm.entities.Mandate;
import cz.nlfnorm.entities.Regulation;
import cz.nlfnorm.entities.StandardCategory;
import cz.nlfnorm.entities.StandardGroup;

public class FilterDto {
	
	private List<SelectDto> standardGroups = new ArrayList<>();
	private List<SelectDto> mandates = new ArrayList<>();
	private List<SelectDto> commissionDecisions = new ArrayList<>();
	private List<SelectDto> assessmentSystems = new ArrayList<>();
	private List<SelectDto> standardCategories = new ArrayList<>();
	private List<Regulation> regulations = new ArrayList<>();
	
	private String getName(final String czName, final String enName, final boolean useEnglish){
		return useEnglish ? enName : czName;
	}
	
	public void setStandardGroups(final List<StandardGroup> standardGroups, final boolean useEnglish) {
		if(CollectionUtils.isNotEmpty(standardGroups)){
			for(final StandardGroup g : standardGroups){
				this.standardGroups.add(
						new SelectDto(
							g.getId(),
							g.getCode() +  " - " + getName(g.getCzechName(), g.getEnglishName(), useEnglish)
							
					)
				);
			}
		}
	}
	
	
	public void setMandates(final List<Mandate> mandates) {
		if(CollectionUtils.isNotEmpty(mandates)){
			for(final Mandate m : mandates){
				this.mandates.add(
						new SelectDto(m.getId(), m.getMandateName())
				);
			}
		}
	}
	
	
	public void setCommissionDecisions(final List<CommissionDecision> commissionDecisions, final boolean useEnglish) {
		if(CollectionUtils.isNotEmpty(commissionDecisions)){
			for(final CommissionDecision c : commissionDecisions){
				this.commissionDecisions.add(
						new SelectDto(
								c.getId(), 
								getName(c.getCzechLabel(), c.getEnglishLabel(),useEnglish)
							)
				);
			}
		}
		
	}
	
	
	public void setAssessmentSystems(final List<AssessmentSystem> assessmentSystems, final boolean useEnglish) {
		if(CollectionUtils.isNotEmpty(assessmentSystems)){
			for(final AssessmentSystem as : assessmentSystems){
				this.assessmentSystems.add(
						new SelectDto(
								as.getId(), 
								getName(as.getNameCzech(), as.getNameEnglish(), useEnglish)
						));
			}
		}
		
	}
	
	public void setStandardCategories(List<StandardCategory> standardCategories, final boolean useEnglish) {
		if(CollectionUtils.isNotEmpty(standardCategories)){
			for(final StandardCategory c : standardCategories){
				this.standardCategories.add(new SelectDto(
						c.getId(),
						(StringUtils.isNotBlank(c.getCode()) ? c.getCode() + " - " : "")
						+ getName(c.getNameCzech(), c.getNameEnglish(), useEnglish)	
				));
			}
		}
	}
	public void setRegulations(List<Regulation> regulations) {
		this.regulations = regulations;
	}
	public List<SelectDto> getStandardCategories() {
		return standardCategories;
	}
	public List<SelectDto> getStandardGroups() {
		return standardGroups;
	}
	public List<SelectDto> getMandates() {
		return mandates;
	}
	public List<SelectDto> getCommissionDecisions() {
		return commissionDecisions;
	}
	public List<SelectDto> getAssessmentSystems() {
		return assessmentSystems;
	}
	public List<Regulation> getRegulations() {
		return regulations;
	}
}
