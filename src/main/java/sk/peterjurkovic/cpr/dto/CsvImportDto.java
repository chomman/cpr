package sk.peterjurkovic.cpr.dto;

import sk.peterjurkovic.cpr.enums.CsvImportState;

public class CsvImportDto {
	
	private CsvImportState state;
	
	private int successCount = 0;
	
	private int failedCount = 0;
	
	private StringBuilder info;
	
	public CsvImportDto(){
		this.state = CsvImportState.IMPORT_FAILED;
	}
	
	public CsvImportState getState() {
		return state;
	}

	public void setState(CsvImportState state) {
		this.state = state;
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
	
	
	
	
}
