package cz.nlfnorm.quasar.dto;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import cz.nlfnorm.quasar.entities.Auditor;

public class EvaludatedQsAuditorDto implements GridTagItem, EvaluatedAuditorFunction{
	
	public EvaludatedQsAuditorDto(final Auditor auditor){
		this.auditor = auditor;
	}
	private Auditor auditor;
	private List<EvaluatedEacCode> codes;
	
	@Override
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
	
	@Override
	public String getCodeOnPosition(int position) {
		if( indexExists(position)){
			return codes.get(position).getAuditorEacCode().getEacCode().getCode();
		}
		return null;
	}
	
	@Override
	public int getCodeSize() {
		if(codes != null){
			return codes.size();
		}
		return 0;
	}
	
	public boolean indexExists(final int index) {
	    return codes != null && index >= 0 && index < codes.size();
	}
	@Override
	public boolean isCodeOnPositionGranted(int position) {
		if(indexExists(position)){
			return codes.get(position).isGrated();
		}
		return false;
	}
	@Override
	public boolean isFunctionGranted() {
		if(CollectionUtils.isNotEmpty(codes)){
			for(final EvaluatedEacCode eCode : codes){
				if(eCode.isGrated()){
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public String getGrantedCodes() {
		StringBuilder strCodes = new StringBuilder();
		int i = 0;
		if( CollectionUtils.isNotEmpty(codes) ){
			for(final EvaluatedEacCode eCode : codes){
				if(eCode.isGrated()){
					if(i != 0){
						strCodes.append(", ");
					}
					strCodes.append(eCode.getAuditorEacCode().getEacCode().getCode());
					i++;
				}
				
			}
		}
		return strCodes.toString();
	}
	
}
