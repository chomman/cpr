package cz.nlfnorm.web.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.constants.ImageFormat;
import cz.nlfnorm.image.Image;
import cz.nlfnorm.image.ImageLoader;
import cz.nlfnorm.services.FileService;


@Controller
public class ImageController {
	
	private static final String REPLACE_PATTERN = "^/image/(s|n|r)/(\\d+/)?";
	
	private Logger logger = Logger.getLogger(getClass());
	private static final String CACHE_CONTROL_HEADER = "Cache-Control";
    private static final String CACHE_CONTROL_VALUE = "public, max-age=315360000, post-check=315360000, pre-check=315360000";
    private static final String ETAG_HEADER = "ETag";
    private static final String ETAG_VALUE = "2740050219";
    private static final String EXPIRES_HEADER = "Expires";
    private static final String IF_MODIFIED_SINCE_HEADER = "If-Modified-Since";
    private static final String IF_NONE_MATCH_HEADER = "If-None-Match";
    private static final String LAST_MODIFIED_HEADER = "Last-Modified";
        
	@Autowired
	private FileService fileService;
		
	@RequestMapping(value = Constants.IMAGE_URL_PREFIX+ImageFormat.IMAGE_NORMAL +"/**")
	public @ResponseBody byte[] showImage(
			HttpServletRequest request, 
			HttpServletResponse response){
		
		if(isNotModified(request, response)){
			return null;
		}

		final String path = getImageAbsolutePath(request);
		File file = new File(path);
		if(file.exists()){
			try {
				byte[] image = getNotModifiedImage(file);
				setCachingResponseHeaders(response);
				return image;
			} catch (IOException e) {
			  logger.warn("Obrazok sa nepodarilo spracovat: " + e.getMessage());
			}
		}
		
		return null;
	}
	
	@RequestMapping(value = Constants.IMAGE_URL_PREFIX+ImageFormat.IMAGE_SQUARE +"/{size}/**")
	public @ResponseBody byte[] showImage(
			@PathVariable int size,
			HttpServletRequest request, 
			HttpServletResponse response){
		
		if(isNotModified(request, response)){
			return null;
		}

		File file = new File(getImageAbsolutePath(request));
		if(file.exists()){
			try {
				byte[] image = getSquareImage(file, size);
				setCachingResponseHeaders(response);
				return image;
			} catch (IOException e) {
			  logger.warn("Obrazok sa nepodarilo spracovat: " + e.getMessage());
			}
		}
		
		return null;
	}
	
	@RequestMapping(value = Constants.IMAGE_URL_PREFIX+ImageFormat.IMAGE_RESIZED +"/{size}/**")
	public @ResponseBody byte[] getResizedImage(
			@PathVariable int size,
			HttpServletRequest request, 
			HttpServletResponse response){
		
		if(isNotModified(request, response)){
			return null;
		}
		File file = new File(getImageAbsolutePath(request));
		if(file.exists()){
			try {
				byte[] image = getResizedImage(file, size);
				setCachingResponseHeaders(response);
				return image;
			} catch (IOException e) {
			  logger.warn("Obrazok sa nepodarilo spracovat: " + e.getMessage());
			}
		}
		return null;
	}
	
	
	private byte[] getNotModifiedImage(File file) throws IOException{
		FileInputStream in = new FileInputStream(file);
		return IOUtils.toByteArray(in);
	}
	
	
	private byte[] getResizedImage(File file, int newWidth) throws IOException{
		Image image = ImageLoader.fromFile(file);
		image = image.getResizedToWidth(newWidth);
		return image.getImage();
	}
	
	
	private byte[] getSquareImage(File file, int size) throws IOException{
		Image image = ImageLoader.fromFile(file);
		image = image.getResizedToSquare(size,  0.1);
		return image.getImage();
	}
		
	private void setCachingResponseHeaders(HttpServletResponse response) {
        Calendar date = Calendar.getInstance();
        response.setHeader(CACHE_CONTROL_HEADER, CACHE_CONTROL_VALUE);
        response.setDateHeader(LAST_MODIFIED_HEADER, date.getTimeInMillis());
        response.setHeader(ETAG_HEADER, ETAG_VALUE);
        date.add(Calendar.YEAR, 10);
        response.setDateHeader(EXPIRES_HEADER, date.getTimeInMillis());
    }
	
	
	private String getImageAbsolutePath(HttpServletRequest request){
		return fileService.getFileSaveDir() + File.separatorChar + getFileLocation(request);
	}
	
	
	private boolean isNotModified(HttpServletRequest request, HttpServletResponse response){
		 if (request.getHeader(IF_MODIFIED_SINCE_HEADER) != null || request.getHeader(IF_NONE_MATCH_HEADER) != null) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return true;
        }
		 return false;
	}
	
	private String getFileLocation(HttpServletRequest request){
		String fileLocation = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		return fileLocation.replaceFirst(REPLACE_PATTERN, "");
	}
	
}
