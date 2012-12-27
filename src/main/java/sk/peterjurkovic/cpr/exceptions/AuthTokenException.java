package sk.peterjurkovic.cpr.exceptions;


@SuppressWarnings("serial")
public class AuthTokenException extends Exception {
	

	public AuthTokenException(String message){
		super(message);
	}
	
	
	public AuthTokenException(String message, Throwable cause){
			super(message, cause);
	}
	
	public AuthTokenException( Throwable cause){
		super(cause);
}
}
