package cz.nlfnorm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value=HttpStatus.NOT_FOUND)
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
