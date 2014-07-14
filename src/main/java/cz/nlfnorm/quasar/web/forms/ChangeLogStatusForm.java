package cz.nlfnorm.quasar.web.forms;

import cz.nlfnorm.quasar.enums.LogStatus;

public class ChangeLogStatusForm {
	
	private String comment;
	private int status;
	private int action;
	private Long logId;
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public LogStatus getLogStatus() {
		return LogStatus.getById(status);
	}	
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public Long getLogId() {
		return logId;
	}
	public void setLogId(Long logId) {
		this.logId = logId;
	}
	
	
}
