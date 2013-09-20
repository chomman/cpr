package sk.peterjurkovic.cpr.dto;


import sk.peterjurkovic.cpr.enums.ImportStatus;

public class CsvImportDto {
	
	private ImportStatus status;
	
	private int successCount = 0;
	
	private int failedCount = 0;
	
	private StringBuilder info;
	
	public CsvImportDto(){
		this.status = ImportStatus.FAILED;
	}
	
	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public int getFailedCount() {
		return failedCount;
	}

	public void setFailedCount(int failedCount) {
		this.failedCount = failedCount;
	}

	public StringBuilder getInfo() {
		return info;
	}

	public void setInfo(StringBuilder info) {
		this.info = info;
	}

	public ImportStatus getStatus() {
		return status;
	}

	public void setStatus(ImportStatus status) {
		this.status = status;
	}
	
	
	
	
}
