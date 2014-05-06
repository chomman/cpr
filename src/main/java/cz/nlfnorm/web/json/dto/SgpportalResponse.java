package cz.nlfnorm.web.json.dto;

public class SgpportalResponse {
	
	public static final int STATUS_OK = 0;
	public static final int STATUS_ERROR = 1;
	
	private int error;
	
	private String message;

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus(){
		return getError();
	}
	
	@Override
	public String toString() {
		return "SgpportalResponse [error=" + error + ", message=" + message
				+ "]";
	}
	
	
	
}
