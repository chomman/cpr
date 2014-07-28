package cz.nlfnorm.quasar.web.forms;

import cz.nlfnorm.quasar.enums.LogStatus;

public class ChangeLogStatusForm {
	
	private String comment;
	private LogStatus status;
	private int action;
	private Long logId;
	private Double rating;
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
	public LogStatus getStatus() {
		return status;
	}
	public void setStatus(LogStatus status) {
		this.status = status;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}	
}
