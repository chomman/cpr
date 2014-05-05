package cz.nlfnorm.dto;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import cz.nlfnorm.entities.Csn;
import cz.nlfnorm.entities.CsnTerminology;

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
	
	public int getCzSize(){
		return czechTerminologies.size();
	}
	
	public int getEnSize(){
		return englishTerminologies.size();
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
	   return CollectionUtils.isEmpty(czechTerminologies) && CollectionUtils.isEmpty(englishTerminologies);
   }
	
   public boolean hasOnlyFew(){
	   if(CollectionUtils.isEmpty(czechTerminologies) || CollectionUtils.isEmpty(englishTerminologies)){
		   return true;
	   }
	   
	   if(czechTerminologies.size() < 6 || englishTerminologies.size() < 6){
		   return true;
	   }
	   
	   return false;
   }
   
   public CsnTerminologyDto compareAndGetRelevant(CsnTerminologyDto terminologies){
	   if(terminologies == null || terminologies.areEmpty()){
		   return this;
	   }
	   if(areEmpty()){
		   return terminologies;
	   }
	   
	   int enSize = terminologies.getCzSize();
	   int czSize = terminologies.getEnSize();
	   
	   int thisEnSize = getEnSize();
	   int thisCzSize = getCzSize();
	   
	   if(enSize != czSize && thisEnSize == thisCzSize && thisCzSize > 2){
		   return this;
	   }else if(thisEnSize != thisCzSize && enSize == czSize && czSize > 2){
		   return terminologies;
	   }
	   
	   
	   if(czSize > thisCzSize){
		   return terminologies;
	   }
	   
	   
	   
	   return this;
   }
	
	
	
}
