package cz.nlfnorm.dto;

import cz.nlfnorm.enums.ImportStatus;

public class CsvImportLogDto {
	
	private ImportStatus status;
	
	private int successCount = 0;
	
	private int failedCount = 0;
	
	private StringBuilder info;
	
	public CsvImportLogDto(){
		this.status = ImportStatus.FAILED;
		this.info = new StringBuilder("<table>");
	}
	
	
	public ImportStatus getStatus() {
		return status;
	}

	public void setStatus(ImportStatus status) {
		this.status = status;
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

	public void appendInfo(String message){
		info.append("<tr><td>")
			.append(message)
			.append("</td></tr>");
	}
	
	public StringBuilder getInfo() {
		return info.append("</table>");
	}

	public void setInfo(StringBuilder info) {
		this.info = info;
	}
	
	
	public void incrementSuccess(){
		successCount++;
	}
	
	public void incrementFailure(){
		failedCount++;
	}
	
}
