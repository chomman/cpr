package sk.peterjurkovic.cpr.web.controllers.admin;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.dto.FileUploadItemDto;
import sk.peterjurkovic.cpr.services.FileService;
import sk.peterjurkovic.cpr.validators.admin.ImageValidator;

@Controller
public class FileManagerController extends SupportAdminController {

	@Autowired
	private FileService fileService;
	
	@Autowired
	private ImageValidator imageValidator;
	
	private static final String COMMAND = "command";

	public FileManagerController() {
		setViewName("file-manager");
	}

	@RequestMapping(value = "/admin/file-manager.htm", method = RequestMethod.GET)
	public String showManager(ModelMap modelMap, HttpServletRequest request) {

		String dir = request.getParameter("dir");
		String selector = request.getParameter("selector");
		FileUploadItemDto form = new FileUploadItemDto();
		if(StringUtils.isNotBlank(selector)){
			if (StringUtils.isBlank(dir)) {
				dir = Constants.DEFAULT_IMAGE_SAVE_DIR;
			}
			form.setSaveDir(dir);
		}else{
			logger.warn("Opener window selector is not set: " + request.getQueryString());
			selector = "";
		}
		
		prepareModel(form, modelMap, selector);
		
		return getViewName();
	}

	
	
	@RequestMapping(value = "/admin/file-manager.htm", method = RequestMethod.POST)
	public String save(@ModelAttribute(COMMAND) FileUploadItemDto form, BindingResult result,  ModelMap modelMap, HttpServletRequest request) {
		
		form.setSaveDir(request.getParameter("dir"));
		String selector = request.getParameter("selector");

		if (StringUtils.isBlank(form.getSaveDir())) {
			form.setSaveDir( Constants.DEFAULT_IMAGE_SAVE_DIR );
		}
		if(StringUtils.isNotBlank(selector)){
			MultipartFile file = form.getFileData();
			if (null != file) {
				String fileName = file.getOriginalFilename();
				if(StringUtils.isBlank(fileName) || !imageValidator.validate(fileName)){
					modelMap.put("hasErrors", true );
				}else{
					try {
						InputStream content = file.getInputStream();
						fileService.saveFile(fileName, content, form.getSaveDir());
					} catch (IOException e) {
						logger.warn("Nahravany obrazok: "+ fileName+ " sa neodarilo ulozit: "
								+ e.getMessage());
						modelMap.put("hasErrors", true );
					}catch(MaxUploadSizeExceededException e){
						logger.warn("Nahravany obrazok: "+ fileName+ " sa neodarilo ulozit: " + e.getMessage());
						modelMap.put("hasErrors", true );
					}
				}
				
			}
		}else{
			logger.warn("Opener window selector is not set: " + request.getQueryString());
			selector = "";
		}

		prepareModel(form, modelMap, selector);
		return getViewName();
	}

	private void prepareModel(FileUploadItemDto form, ModelMap modelMap, String selector) {
		modelMap.addAttribute(COMMAND, form );
		modelMap.put("images", fileService.getImagesFromDirectory(form.getSaveDir()));
		modelMap.put("selector", selector);
	}

}
