package cz.nlfnorm.quasar.web.forms;

public abstract class AbstractLogItemForm {

	private String companyName;
	private String nandoCodes;
	private long logId;
	
	public long getLogId() {
		return logId;
	}
	public void setLogId(long auditLogId) {
		this.logId = auditLogId;
	}	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getNandoCodes() {
		return nandoCodes;
	}
	public void setNandoCodes(String nandoCodes) {
		this.nandoCodes = nandoCodes;
	}
}
