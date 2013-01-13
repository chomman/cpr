package sk.peterjurkovic.cpr.web.json;

/**
 * Objekt, ktory sluzi na Ajax response
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 */
public class JsonResponse {
	
	 	private String status = JsonStatus.ERROR;
	    private Object result = null;

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
	    
}
