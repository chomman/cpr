package sk.peterjurkovic.cpr.exceptions;

@SuppressWarnings("serial")
public class PageNotFoundEception extends Exception {
	
	public PageNotFoundEception(){
		super();
	}
	
	public PageNotFoundEception(String message){
		super(message);
	}
	
	
	public PageNotFoundEception(String message, Throwable cause){
			super(message, cause);
	}
	
	public PageNotFoundEception( Throwable cause){
		super(cause);	
	}
	
}
