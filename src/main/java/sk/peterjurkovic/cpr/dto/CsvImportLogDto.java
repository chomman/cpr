package sk.peterjurkovic.cpr.dto;

import sk.peterjurkovic.cpr.enums.CsvImportState;

public class CsvImportLogDto {
	
	private CsvImportState state;
	
	private int successCount = 0;
	
	private int failedCount = 0;
	
	private StringBuilder info;
	
	public CsvImportLogDto(){
		this.state = CsvImportState.IMPORT_FAILED;
		this.info = new StringBuilder("<table>");
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

	public void appendInfo(String message, int lineNumber){
		info.append("<tr><td class=\"br\">Řádek: ")
			.append(lineNumber)
			.append(" </td><td>")
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
