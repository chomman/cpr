package cz.nlfnorm.quasar.web.forms;

import cz.nlfnorm.quasar.entities.AuditLogItem;

public class AuditLogItemForm {
	
	private AuditLogItem item;
	private String companyName;
	private String certificationBodyName;
	private String eacCodes;
	private String nandoCodes;
	
	public AuditLogItemForm(){
		item = new AuditLogItem();
	}
	public AuditLogItemForm(AuditLogItem item){
		this.item = item;
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
	
	
	
}
