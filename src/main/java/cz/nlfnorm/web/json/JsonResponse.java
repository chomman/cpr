package cz.nlfnorm.web.json;

/**
 * Objekt, ktory sluzi na Ajax response
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 */
public class JsonResponse {
	
	 	private String status = JsonStatus.ERROR;
	    private Object result = null;
	    private Object data = null;

	    public String getStatus() {
	        return status;
	    }
	    public void setStatus(String status) {
	        this.status = status;
	    }
	    public Object getResult() {
	        return result;
	    }
	    public void setResult(Object result) {
	        this.result = result;
	    }
		public Object getData() {
			return data;
		}
		public void setData(Object data) {
			this.data = data;
		}
	    
	    
}
