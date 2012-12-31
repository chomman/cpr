package sk.peterjurkovic.cpr.forms.admin;


public class StandardGroupForm {
	
	private Long id;
	private String groupName;
	private String commissionDecisionFileUrl;
	private String code;
	private String timestamp;

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getCommissionDecisionFileUrl() {
		return commissionDecisionFileUrl;
	}
	public void setCommissionDecisionFileUrl(String commissionDecisionFileUrl) {
		this.commissionDecisionFileUrl = commissionDecisionFileUrl;
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
}
