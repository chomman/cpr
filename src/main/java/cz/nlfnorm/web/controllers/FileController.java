package cz.nlfnorm.web.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.nlfnorm.exceptions.PageNotFoundEception;
import cz.nlfnorm.services.FileService;
import cz.nlfnorm.utils.RequestUtils;


@Controller
public class FileController {
	
	 
	@Autowired
	private FileService fileService;
	protected static Logger logger = Logger.getLogger(FileController.class);
	
	@RequestMapping("/f/**")
	public void hladneFileDownload(	HttpServletRequest request,	HttpServletResponse  response) throws PageNotFoundEception{
	
		final String fileLocation = RequestUtils.getPartOfUrlAfterPattern(request, "/f/");
		final File file = fileService.getFile(fileLocation);
		if(file != null){
			try {
				response.setContentType(determineContentType(file));
				response.setContentLength((int) file.length());
				final String headerKey = "Content-Disposition";
		        final String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
		        response.setHeader(headerKey, headerValue);
		        response.setDateHeader("Expires",Long.MAX_VALUE);
		        FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
			} catch (IOException e) {
				logger.warn(String.format("Downloading failed [%1$s]", fileLocation),e);
			}	
			
		}else{
			throw new PageNotFoundEception();
		}		
	}
	
	
	private String determineContentType(final File file) throws IOException{
		 return  Files.probeContentType(file.toPath());
	}
	
}
