package cz.nlfnorm.quasar.web.forms;

import javax.validation.Valid;

import cz.nlfnorm.quasar.entities.AuditLog;
import cz.nlfnorm.quasar.entities.AuditLogItem;
import cz.nlfnorm.quasar.entities.Company;

public class AuditLogItemForm extends AbstractLogItemForm 
	implements LogItemForm, CompanyForm{
	
	@Valid
	private AuditLogItem item;
	private String certificationBodyName;
	private String eacCodes;
		
	public AuditLogItemForm(){}
	
	public AuditLogItemForm(final AuditLog auditLog){
		item = new AuditLogItem(auditLog);
		setLogId( auditLog.getId() );
	}
	
	public AuditLogItemForm(final AuditLog auditLog, final AuditLogItem item){
		this.item = item;
		setLogId( auditLog.getId() );
	}
	
	public AuditLogItem getItem() {
		return item;
	}
	public void setItem(AuditLogItem item) {
		this.item = item;
	}
	
	public String getEacCodes() {
		return eacCodes;
	}
	public void setEacCodes(String eacCodes) {
		this.eacCodes = eacCodes;
	}
	
	public String getCertificationBodyName() {
		return certificationBodyName;
	}

	public void setCertificationBodyName(String certificationBodyName) {
		this.certificationBodyName = certificationBodyName;
	}

	@Override
	public void setCompany(Company company) {
		if(item != null){
			item.setCompany(company);
		}
	}

	@Override
	public Company getCompany() {
		if(item != null){
			return item.getCompany();
		}
		return null;
	}

	@Override
	public String getStringNandoCodes() {
		return getNandoCodes();
	}

	@Override
	public String getStringEacCodes() {
		return getEacCodes();
	}

}
