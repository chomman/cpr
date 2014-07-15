package cz.nlfnorm.quasar.web.forms;

import javax.validation.Valid;

import cz.nlfnorm.quasar.entities.AuditLog;
import cz.nlfnorm.quasar.entities.AuditLogItem;

public class AuditLogItemForm {
	
	@Valid
	private AuditLogItem item;
	private String companyName;
	private String certificationBodyName;
	private String eacCodes;
	private String nandoCodes;
	private long auditLogId;
	
	public AuditLogItemForm(){}
	
	public AuditLogItemForm(final AuditLog auditLog){
		item = new AuditLogItem(auditLog);
		auditLogId = auditLog.getId();
	}
	
	public AuditLogItemForm(final AuditLog auditLog, final AuditLogItem item){
		this.item = item;
		auditLogId = auditLog.getId();
	}
	
	public AuditLogItem getItem() {
		return item;
	}
	public void setItem(AuditLogItem item) {
		this.item = item;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getEacCodes() {
		return eacCodes;
	}
	public void setEacCodes(String eacCodes) {
		this.eacCodes = eacCodes;
	}
	public String getNandoCodes() {
		return nandoCodes;
	}
	public void setNandoCodes(String nandoCodes) {
		this.nandoCodes = nandoCodes;
	}

	public String getCertificationBodyName() {
		return certificationBodyName;
	}

	public void setCertificationBodyName(String certificationBodyName) {
		this.certificationBodyName = certificationBodyName;
	}

	public long getAuditLogId() {
		return auditLogId;
	}

	public void setAuditLogId(long auditLogId) {
		this.auditLogId = auditLogId;
	}	
	
}
