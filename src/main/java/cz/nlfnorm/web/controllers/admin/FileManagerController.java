package cz.nlfnorm.web.controllers.admin;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import cz.nlfnorm.dto.FileUploadItemDto;
import cz.nlfnorm.services.FileService;
import cz.nlfnorm.services.impl.FileServiceImpl;
import cz.nlfnorm.validators.admin.ImageValidator;
import cz.nlfnorm.web.json.JsonResponse;
import cz.nlfnorm.web.json.JsonStatus;

@Controller
public class FileManagerController extends AdminSupportController {
	
	private static final String UPLOAD_TYPE_PARAM = "uploadType";
	private static final String TINY_MCE_SELECTOR_PARAM = "selector";
	
	private static final int IMAGE_UPLOAD = 1;
	private static final int DOCUMET_UPLOAD = 2;

	@Autowired
	private FileService fileService;
	
	@Autowired
	private ImageValidator imageValidator;
	
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
		final String tinyMceSelector = request.getParameter("selector");
		final int uploadType = getUploadType(request);
		if(StringUtils.isNotBlank(tinyMceSelector)){
			MultipartFile file = form.getFileData();
			if (null != file) {
				String fileName = file.getOriginalFilename();
				if(StringUtils.isBlank(fileName) || (uploadType == IMAGE_UPLOAD && !imageValidator.validate(fileName))){
					modelMap.put("hasErrors", true );
				}else{
					InputStream content = null;
					try {
						content = file.getInputStream();
						fileService.saveFile(fileName, content, form.getSaveDir());
					} catch (IOException e) {
						logger.warn("Nahravany obrazok: "+ fileName+ " sa neodarilo ulozit: "
								+ e.getMessage());
						modelMap.put("hasErrors", true );
					}catch(MaxUploadSizeExceededException e){
						logger.warn("Nahravany obrazok: "+ fileName+ " sa neodarilo ulozit: " + e.getMessage());
						modelMap.put("hasErrors", true );
					}finally {
					    try {
					    	if(content != null){
					    		content.close();
					    	}
					    } catch (IOException e) {
					    	logger.warn("Nastala chyba pri zatvaratni streamu subor: "+ fileName+ " - " + e.getMessage());
					    }
					}
				}
				
			}
		}else{
			logger.warn("Opener window selector is not set: " + request.getQueryString());
		}
		prepareModel(form, modelMap, request);
		return getViewName();
	}

	private void prepareModel(FileUploadItemDto form, ModelMap modelMap, HttpServletRequest request) {
		final int uploadType =  getUploadType(request);
		modelMap.addAttribute(COMMAND, form );
		if(uploadType == IMAGE_UPLOAD){
			modelMap.put("images", fileService.getImagesFromDirectory(form.getSaveDir()));
		}else{
			modelMap.put("documets", fileService.readDirectory(form.getSaveDir()));
		}
		modelMap.put(TINY_MCE_SELECTOR_PARAM, request.getParameter(TINY_MCE_SELECTOR_PARAM));
		modelMap.put(UPLOAD_TYPE_PARAM, uploadType);
	}

	
	private String determineDir(HttpServletRequest request){
		final int type = getUploadType(request);
		switch(type){
			case DOCUMET_UPLOAD :
				return FileServiceImpl.DOCUMENTS_DIR_NAME;
			case IMAGE_UPLOAD :
				return FileServiceImpl.IMAGES_DIR_NAME;
			default :
				throw new IllegalArgumentException("Unknown dir type " + type );
		}
	}
	
	private int getUploadType(HttpServletRequest request){
		return Integer.valueOf(request.getParameter(UPLOAD_TYPE_PARAM));
	}
}
