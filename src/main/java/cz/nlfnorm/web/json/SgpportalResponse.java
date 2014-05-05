package cz.nlfnorm.web.json;

public class SgpportalResponse {
	
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

	@Override
	public String toString() {
		return "SgpportalResponse [error=" + error + ", message=" + message
				+ "]";
	}
	
	
	
}
