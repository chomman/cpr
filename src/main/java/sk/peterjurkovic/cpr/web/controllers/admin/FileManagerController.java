package sk.peterjurkovic.cpr.web.controllers.admin;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.dto.FileDto;
import sk.peterjurkovic.cpr.services.FileService;

@Controller
public class FileManagerController extends SupportAdminController {

	@Autowired
	private FileService fileService;
	
	private static final String COMMAND = "command";

	public FileManagerController() {

		setViewName("file-manager");
	}

	@RequestMapping(value = "/admin/file-manager.htm", method = RequestMethod.GET)
	public String showManager(ModelMap modelMap, HttpServletRequest request) {

		String dir = request.getParameter("dir");

		if (StringUtils.isBlank(dir)) {
			dir = Constants.DEFAULT_IMAGE_SAVE_DIR;
		}
		
		FileDto form = new FileDto();
		form.setSaveDir(dir);
		prepareModel(form, modelMap);
		
		return getViewName();
	}

	@RequestMapping(value = "/admin/file-manager.htm", method = RequestMethod.POST)
	public String save(@ModelAttribute(COMMAND) FileDto form, ModelMap modelMap, HttpServletRequest request) {
		
		String dir = request.getParameter("dir");

		if (StringUtils.isBlank(dir)) {
			dir = Constants.DEFAULT_IMAGE_SAVE_DIR;
		}
		
		List<MultipartFile> files = form.getFiles();

		if (null != files && files.size() > 0) {
			for (MultipartFile multipartFile : files) {

				String fileName = multipartFile.getOriginalFilename();
				try {
					InputStream content = multipartFile.getInputStream();
					fileService.saveFile(fileName, content, dir);
				} catch (IOException e) {
					logger.error("Nahravany obrazok sa neodarilo ulozit: "
							+ e.getMessage());
				}
			}
		}

		prepareModel(form, modelMap);
		return getViewName();
	}

	private void prepareModel(FileDto form, ModelMap modelMap) {
		modelMap.addAttribute(COMMAND, form );
		modelMap.put("images", fileService.getImagesFromDirectory(form.getSaveDir()));
	}

}
