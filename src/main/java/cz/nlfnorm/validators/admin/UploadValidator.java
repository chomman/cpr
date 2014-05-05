package cz.nlfnorm.validators.admin;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cz.nlfnorm.dto.FileUploadItemDto;


public class UploadValidator implements Validator  {
	
	private static transient Logger logger = Logger.getLogger(UploadValidator.class);
	
	// Content types the user can upload
    private static final String[] ACCEPTED_CONTENT_TYPES = new String[] {
            "application/pdf",
            "application/doc",         
            "application/msword",
            "application/rtf",         
            "text/richtext" ,
            "text/rtf" ,
            "text/plain" ,
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document" ,
            "application/vnd.sun.xml.writer" ,
            "application/x-soffice" ,
            };
     
    private static final String[] ACCEPTED_EXTENSIONS = new String[] {
        "doc",
        "pdf",
        "docx",
        "rtf", 
        "txt", 
    };
    
    private static final String[] ACCEPTED_IMAGE_EXTENSIONS = new String[] {
        "jpg",
        "jpeg",
        "gif",
        "png", 
    };
	
	@Override
	public boolean supports(Class<?> clazz) {
		return FileUploadItemDto.class.equals(clazz);

	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

	

}
