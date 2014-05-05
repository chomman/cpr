package cz.nlfnorm.exceptions;

@SuppressWarnings("serial")
public class PortalAccessDeniedException extends Exception {
	
	public PortalAccessDeniedException(String message){
		super(message);
	}
		
	public PortalAccessDeniedException(String message, Throwable cause){
			super(message, cause);
	}
	
	public PortalAccessDeniedException( Throwable cause){
		super(cause);
	}
	
}
