package sk.peterjurkovic.cpr.exceptions;

@SuppressWarnings("serial")
public class CollisionException extends Exception {
	
	public CollisionException(String message){
		super(message);
	}
	
	
	public CollisionException(String message, Throwable cause){
			super(message, cause);
	}
	
	public CollisionException( Throwable cause){
		super(cause);	
	}
	
}
