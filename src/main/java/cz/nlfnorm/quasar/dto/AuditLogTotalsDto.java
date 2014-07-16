package cz.nlfnorm.quasar.dto;

import java.util.HashMap;
import java.util.Map;

import cz.nlfnorm.quasar.entities.EacCode;
import cz.nlfnorm.quasar.entities.NandoCode;

public class AuditLogTotalsDto {
	
	private Map<EacCode, AuditLogCodeSumDto> eacCodes = new HashMap<>();
	private Map<NandoCode, AuditLogCodeSumDto> nandoCodes = new HashMap<>();
	private int auditDays = 0;
	private int audits = 0;
	
	public Map<EacCode, AuditLogCodeSumDto> getEacCodes() {
		return eacCodes;
	}
	public void setEacCodes(Map<EacCode, AuditLogCodeSumDto> eacCodes) {
		this.eacCodes = eacCodes;
	}
	public Map<NandoCode, AuditLogCodeSumDto> getNandoCodes() {
		return nandoCodes;
	}
	public void setNandoCodes(Map<NandoCode, AuditLogCodeSumDto> nandoCodes) {
		this.nandoCodes = nandoCodes;
	}
	public int getAuditDays() {
		return auditDays;
	}
	public void setAuditDays(int auditDays) {
		this.auditDays = auditDays;
	}
	public int getAudits() {
		return audits;
	}
	public void setAudits(int audits) {
		this.audits = audits;
	}	
	public void incrementAudits(){
		audits++;
	}
	public void incrementAuditDays(int val){
		auditDays += val;
	}
}
