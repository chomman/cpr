package sk.peterjurkovic.cpr.web.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sk.peterjurkovic.cpr.constants.ImageType;
import sk.peterjurkovic.cpr.image.Image;
import sk.peterjurkovic.cpr.image.ImageLoader;
import sk.peterjurkovic.cpr.services.FileService;


@Controller
public class ImageController {
	
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
	

	
	@RequestMapping(value = "/image/"+ImageType.IMAGE_NORMAL +"/{dir}/{name:.*}")
	public @ResponseBody byte[] showImage(
			@PathVariable String name, 
			@PathVariable String dir, 
			HttpServletRequest request, 
			HttpServletResponse response){
		
		if(isNotModified(request, response)){
			return null;
		}
		
		if(StringUtils.isNotBlank(name)){
			String path = getImageAbsolutePath(request, dir, name);
			File file = new File(path);
			if(file.exists()){
				try {
					byte[] image = getNotModifiedImage(file);
					setCachingResponseHeaders(response);
					return image;
				} catch (IOException e) {
				  logger.warn("Obrazok " + name +" sa nepodarilo spracovat: " + e.getMessage());
				}
			}
		}
		return null;
	}
	
	@RequestMapping(value = "/image/"+ImageType.IMAGE_SQUARE +"/{size}/{dir}/{name:.*}")
	public @ResponseBody byte[] showImage(
			@PathVariable int size,
			@PathVariable String name, 
			@PathVariable String dir,
			HttpServletRequest request, 
			HttpServletResponse response){
		
		if(isNotModified(request, response)){
			return null;
		}
		if(StringUtils.isNotBlank(name)){
			File file = new File(getImageAbsolutePath(request,dir, name));
			if(file.exists()){
				try {
					byte[] image = getSquareImage(file, size);
					setCachingResponseHeaders(response);
					return image;
				} catch (IOException e) {
				  logger.warn("Obrazok " + name +" sa nepodarilo spracovat: " + e.getMessage());
				}
			}
		}
		return null;
	}
	
	@RequestMapping(value = "/image/"+ImageType.IMAGE_RESIZED +"/{size}/{dir}/{name:.*}")
	public @ResponseBody byte[] getResizedImage(
			@PathVariable int size,
			@PathVariable String dir,
			@PathVariable String name, 
			HttpServletRequest request, 
			HttpServletResponse response){
		
		if(isNotModified(request, response)){
			return null;
		}
		if(StringUtils.isNotBlank(name)){
			File file = new File(getImageAbsolutePath(request,dir, name));
			if(file.exists()){
				try {
					byte[] image = getResizedImage(file, size);
					setCachingResponseHeaders(response);
					return image;
				} catch (IOException e) {
				  logger.warn("Obrazok " + name +" sa nepodarilo spracovat: " + e.getMessage());
				}
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
	
	
	
	private String getImageAbsolutePath(HttpServletRequest request, String dir, String name){
		Validate.notNull(name);
		return fileService.getFileSaveDir() + File.separatorChar + dir +  File.separatorChar+ name;
	}
	
	private boolean isNotModified(HttpServletRequest request, HttpServletResponse response){
		 if (request.getHeader(IF_MODIFIED_SINCE_HEADER) != null || request.getHeader(IF_NONE_MATCH_HEADER) != null) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return true;
        }
		 return false;
	}
	
}
