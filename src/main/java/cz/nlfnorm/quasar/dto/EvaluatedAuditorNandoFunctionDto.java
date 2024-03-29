package cz.nlfnorm.quasar.dto;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import cz.nlfnorm.quasar.entities.Auditor;

public class EvaluatedAuditorNandoFunctionDto implements GridTagItem, EvaluatedAuditorFunction{
	
	private Auditor auditor;
	private List<EvaluatedAuditorNandoCode> codes;

	public EvaluatedAuditorNandoFunctionDto(Auditor auditor){
		this.auditor = auditor;
	}
	
	@Override
	public Auditor getAuditor() {
		return auditor;
	}
	public void setAuditor(Auditor auditor) {
		this.auditor = auditor;
	}
	public List<EvaluatedAuditorNandoCode> getCodes() {
		return codes;
	}
	public void setCodes(List<EvaluatedAuditorNandoCode> codes) {
		this.codes = codes;
	}

	@Override
	public String getCodeOnPosition(final int position) {
		if(indexExists(position)){
			return codes.get(position).getAuditorNandoCode().getNandoCode().getCode();
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

	@Override
	public boolean isCodeOnPositionGranted(final int position) {
		if(indexExists(position)){
			return codes.get(position).isGrated();
		}
		return false;
	}
	
	public boolean indexExists(final int index) {
	    return index >= 0 && index < codes.size();
	}

	@Override
	public boolean isFunctionGranted() {
		if(CollectionUtils.isNotEmpty(codes)){
			for(final EvaluatedAuditorNandoCode eCode : codes){
				if(eCode.isGrated()){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String getGrantedCodes() {
		StringBuilder strCodes = new StringBuilder("");
		int i = 0;
		if( CollectionUtils.isNotEmpty(codes) ){
			for(final EvaluatedAuditorNandoCode eCode : codes){
				if(eCode.isGrated()){
					if(i != 0){
						strCodes.append(", ");
					}
					strCodes.append(eCode.getAuditorNandoCode().getNandoCode().getCode());
					i++;
				}
				
			}
		}
		return strCodes.toString();
	}
	
}
