package cz.nlfnorm.web.controllers.admin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import cz.nlfnorm.context.ContextHolder;
import cz.nlfnorm.dto.FileUploadItemDto;
import cz.nlfnorm.services.FileService;
import cz.nlfnorm.services.impl.FileServiceImpl;
import cz.nlfnorm.utils.CodeUtils;
import cz.nlfnorm.utils.RequestUtils;
import cz.nlfnorm.validators.admin.ImageValidator;
import cz.nlfnorm.web.json.JsonResponse;
import cz.nlfnorm.web.json.JsonStatus;

@Controller
public class FileManagerController extends AdminSupportController {
	
	private static final String UPLOAD_TYPE_PARAM = "uploadType";
	private static final String TINY_MCE_SELECTOR_PARAM = "selector";
	private static final String FOLDER_PARAM = "dir";
	
	private static final int IMAGE_UPLOAD = 1;
	private static final int DOCUMET_UPLOAD = 2;

	@Autowired
	private FileService fileService;
	@Autowired
	private ImageValidator imageValidator;
	@Autowired
	private MessageSource messageSource;
	
	
	private static final String COMMAND = "command";

	public FileManagerController() {
		setViewName("file-manager");
	}
	
	@RequestMapping(value = "/admin/file/remove", method = RequestMethod.GET)
	public @ResponseBody JsonResponse removeFile(@RequestBody @RequestParam("url") String url){
		JsonResponse response = new JsonResponse();
		if(fileService.removeFile(url)){
			response.setStatus(JsonStatus.SUCCESS);
		}
		return response;
	}

	@RequestMapping(value = "/admin/file-manager.htm", method = RequestMethod.GET)
	public String showManager(ModelMap modelMap, HttpServletRequest request) {
		final String dir = determineDir(request);
		final String selector = request.getParameter("selector");
		FileUploadItemDto form = new FileUploadItemDto();
		if(StringUtils.isNotBlank(selector)){
			form.setSaveDir(dir);
		}else{
			logger.warn("Opener window selector is not set: " + request.getQueryString());
		}
		prepareModel(form, modelMap, request);
		return getViewName();
	}

	
	
	@RequestMapping(value = "/admin/file-manager.htm", method = RequestMethod.POST)
	public String save(@ModelAttribute(COMMAND) FileUploadItemDto form, BindingResult result,  ModelMap modelMap, HttpServletRequest request) {
		form.setSaveDir(determineDir(request));	
		try{
			if(form.getFileData() != null){
				modelMap.put("fileUpload", true);
				uploadFile(form, isImageUpload(request));
			}else{
				modelMap.put("newDir", true);
				createNewDir(request, form);
			}
		}catch(IllegalArgumentException e){
			modelMap.put("errors", e.getMessage());
		}catch(MaxUploadSizeExceededException e){
			modelMap.put("errors", getMessage("error.upload.maxSize"));
		}catch (Exception e) {
			modelMap.put("errors", getMessage("error.upload.failed"));
		}
		prepareModel(form, modelMap, request);
		return getViewName();
	}

	
	private void prepareModel(FileUploadItemDto form, ModelMap modelMap, HttpServletRequest request) {
		final int uploadType =  getUploadType(request);
		modelMap.addAttribute(COMMAND, form );
		if(uploadType == IMAGE_UPLOAD){
			modelMap.put("documets", fileService.getImagesFromDirectory(form.getSaveDir()));
		}else{
			modelMap.put("documets", fileService.readDirectory(form.getSaveDir()));
		}
		modelMap.put(TINY_MCE_SELECTOR_PARAM, request.getParameter(TINY_MCE_SELECTOR_PARAM));
		modelMap.put(UPLOAD_TYPE_PARAM, uploadType);
		modelMap.put("parentDir", determineParentDir(request) );
		modelMap.put("currentDir", getCurrentDir(request) );
		modelMap.put("url", buildCurrentDirUrl(request) );
		modelMap.put("parentUrl", buildParentDirUrl(request) );
	}

	
	private void createNewDir(HttpServletRequest request, final FileUploadItemDto form){
		if(StringUtils.isBlank(form.getNewDir())){
			throw new IllegalArgumentException(getMessage("error.newDir.blank"));
		}
		String newDirLocation = determineDir(request) + "/" + CodeUtils.generateProperFilename(form.getNewDir());
		File dir = new File(fileService.getFileSaveDir() + newDirLocation);
		if(dir.exists() && dir.isDirectory()){
			throw new IllegalArgumentException(getMessage("error.newDir.exists"));
		}else{
			fileService.createDirectory(newDirLocation);
			form.setNewDir(null);
		}
	}
	
	private void uploadFile(final FileUploadItemDto form, final boolean isImage) throws IOException{
			validate(form, isImage);
			InputStream content = null;
			String fileName = form.getFileData().getOriginalFilename();
			try {
				content = form.getFileData().getInputStream();
				fileService.saveFile(fileName, content, form.getSaveDir());
			}finally {
			    	if(content != null){
			    		content.close();
			    	}
			}
	}
	
	private void validate(final FileUploadItemDto form, final boolean isImage){
		final MultipartFile file = form.getFileData();
		if(file == null || StringUtils.isBlank(file.getOriginalFilename())){
			throw new IllegalArgumentException(getMessage("error.upload.emptyFile"));
		}else if(isImage && !imageValidator.validate(file.getOriginalFilename())){
			throw new IllegalArgumentException(getMessage("error.upload.notImage"));
		}
	}
		

	
	private String determineDir(HttpServletRequest request){
		final int type = getUploadType(request);
		switch(type){
			case DOCUMET_UPLOAD :
				return FileServiceImpl.DOCUMENTS_DIR_NAME + getCurrentDir(request);
			case IMAGE_UPLOAD :
				return FileServiceImpl.IMAGES_DIR_NAME + getCurrentDir(request);
			default :
				throw new IllegalArgumentException("Unknown dir type " + type );
		}
	}
	
	private String getCurrentDir(HttpServletRequest request){
		final String dir = request.getParameter(FOLDER_PARAM);
		if(StringUtils.isBlank(dir)){
			return "";
		}
		return dir;
	}
	
	
	private String determineParentDir(HttpServletRequest request){
		String dir = getCurrentDir(request);
		if(StringUtils.isBlank(dir)){
			return "";
		}
		return dir.substring(0, dir.lastIndexOf("/"));
	}
	
	private int getUploadType(HttpServletRequest request){
		return Integer.valueOf(request.getParameter(UPLOAD_TYPE_PARAM));
	}
	
	private String buildParentDirUrl(HttpServletRequest request){
		return buildUrl(request, determineParentDir(request));
	}
	
	private String buildCurrentDirUrl(HttpServletRequest request){
		return buildUrl(request, getCurrentDir(request));
	}
	
	private String buildUrl(HttpServletRequest request, String dir){
		final String url = RequestUtils.getRequestParams(request, FOLDER_PARAM);
		return url + "&" + FOLDER_PARAM  + "=" + dir;
	}
	
	private String getMessage(final String code){
		return messageSource.getMessage(code,  null, ContextHolder.getLocale());
	}
	
	private boolean isImageUpload(HttpServletRequest request){
		return getUploadType(request) == IMAGE_UPLOAD;
	}
}
