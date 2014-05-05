package cz.nlfnorm.web.controllers.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import cz.nlfnorm.dto.FileUploadItemDto;
import cz.nlfnorm.entities.Csn;
import cz.nlfnorm.entities.CsnTerminologyLog;
import cz.nlfnorm.parser.WordDocumentParser;
import cz.nlfnorm.services.CsnService;
import cz.nlfnorm.services.CsnTerminologyLogService;
import cz.nlfnorm.services.CsnTerminologyService;


@Controller
public class CsnTerminologyImportController extends SupportAdminController {
	
	private static final int TAB_INDEX = 4;
	private static final String MAPPING_URL = "/admin/csn/terminology/import";
	private static final String IMPORT_MODEL_ATTR = "uploadForm";
	
	@Autowired
	private CsnService csnService;
	@Autowired
	private WordDocumentParser wordDocumentParser;
	@Autowired
	private CsnTerminologyLogService terminologyLogService;
	@Autowired
	private CsnTerminologyService csnTerminologyService;
	
	
	public CsnTerminologyImportController(){
		setViewName("csn/csn-terminology-import");
	}
	
	@RequestMapping(value = MAPPING_URL, method =  RequestMethod.GET)
	private String showImportPage(ModelMap modelMap){
		prepareModel(modelMap);
		return getViewName();
	}
	
	
	
	@RequestMapping(value = MAPPING_URL, method =  RequestMethod.POST)
	private String processImport(
			@ModelAttribute(IMPORT_MODEL_ATTR) FileUploadItemDto uploadForm,
			BindingResult result,
			ModelMap modelMap,
			HttpServletRequest request
			){
		
		MultipartFile file = uploadForm.getFileData();
		if(file != null && StringUtils.isNotBlank(file.getOriginalFilename())){
			
			if(FilenameUtils.isExtension(file.getOriginalFilename(), "doc")){
				String baseName = getCatalogIdOfFilename(file.getOriginalFilename());
				if(baseName.matches("\\d{4,7}")){
					Csn csn = csnService.getByCatalogId(baseName);
					if(csn == null){
						result.reject("csn.terminology.import.error.notfound", new Object[]{baseName}, "") ;
					}else{
						CsnTerminologyLog log = csnTerminologyService.processImport(file, csn, request.getContextPath());
						modelMap.put("log", log);
					}
				}else{
					result.reject("csn.terminology.import.error.cs");
				}
				
			}else{
			 result.reject("error.file.format.doc");
			}
		}else{
			result.reject("error.file.blank");
		}
		
		prepareModel(modelMap);
		return getViewName();
	}
	
	
	private void prepareModel(ModelMap modelMap){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("tab", TAB_INDEX);
		modelMap.put("model", model);
	}
	
	
	private String getCatalogIdOfFilename(String fileName){
		return FilenameUtils.getBaseName(fileName);
	}
	
}
