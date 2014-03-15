package sk.peterjurkovic.cpr.dto;

import java.util.List;

import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.entities.StandardCsn;

public class ReportDto {
	
	private List<Standard> standards;
	private List<StandardCsn> standardCsns;
	
	public List<Standard> getStandards() {
		return standards;
	}
	public void setStandards(List<Standard> standards) {
		this.standards = standards;
	}
	public List<StandardCsn> getStandardCsns() {
		return standardCsns;
	}
	public void setStandardCsns(List<StandardCsn> standardCsns) {
		this.standardCsns = standardCsns;
	}
	
	
	
}
