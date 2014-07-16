package cz.nlfnorm.quasar.dto;

public class AuditLogCodeSumDto {
	
	private int numberOfNbAudits = 0;
	private int numberOfIso13485Audits = 0;
	
	
	public void incrementIso13484Audits(){
		numberOfIso13485Audits++;
	}
	public void incrementNbAudits(){
		numberOfNbAudits++;
	}
	
	public int getNumberOfNbAudits() {
		return numberOfNbAudits;
	}
	public void setNumberOfNbAudits(int numberOfNbAudits) {
		this.numberOfNbAudits = numberOfNbAudits;
	}
	public int getNumberOfIso13485Audits() {
		return numberOfIso13485Audits;
	}
	public void setNumberOfIso13485Audits(int numberOfIso13485Audits) {
		this.numberOfIso13485Audits = numberOfIso13485Audits;
	}
	
	public int getTotal(){
		return numberOfIso13485Audits + numberOfNbAudits;
	}
}
