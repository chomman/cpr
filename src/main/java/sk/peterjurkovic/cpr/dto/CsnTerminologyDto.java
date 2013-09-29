package sk.peterjurkovic.cpr.dto;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import sk.peterjurkovic.cpr.entities.Csn;
import sk.peterjurkovic.cpr.entities.CsnTerminology;

public class CsnTerminologyDto {

	private List<CsnTerminology> czechTerminologies;
	
	private List<CsnTerminology> englishTerminologies;
	
	public CsnTerminologyDto(List<CsnTerminology> czechTerminologies, List<CsnTerminology> englishTerminologies){
		this.czechTerminologies = czechTerminologies;
		this.englishTerminologies = englishTerminologies;
	}
	
	public List<CsnTerminology> getCzechTerminologies() {
		return czechTerminologies;
	}

	public void setCzechTerminologies(List<CsnTerminology> czechTerminologies) {
		this.czechTerminologies = czechTerminologies;
	}

	public List<CsnTerminology> getEnglishTerminologies() {
		return englishTerminologies;
	}

	public void setEnglishTerminologies(List<CsnTerminology> englishTerminologies) {
		this.englishTerminologies = englishTerminologies;
	}
	
	
	public void setCsn(Csn csn){
		for(CsnTerminology t : englishTerminologies){
			t.setCsn(csn);
		}
		for(CsnTerminology t : czechTerminologies){
			t.setCsn(csn);
		}
	}
	
	public boolean areEmpty(){
		 return CollectionUtils.isEmpty(czechTerminologies) && 
				CollectionUtils.isEmpty(englishTerminologies);
	}
	
	
	
	
}
