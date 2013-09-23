package sk.peterjurkovic.cpr.web.controllers.admin;

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

import sk.peterjurkovic.cpr.dto.CsnTerminologyDto;
import sk.peterjurkovic.cpr.dto.FileUploadItemDto;
import sk.peterjurkovic.cpr.entities.Csn;
import sk.peterjurkovic.cpr.entities.CsnTerminologyLog;
import sk.peterjurkovic.cpr.parser.NewTerminologyParserImpl;
import sk.peterjurkovic.cpr.parser.TerminologyParser;
import sk.peterjurkovic.cpr.parser.TikaProcessingContext;
import sk.peterjurkovic.cpr.parser.WordDocumentParser;
import sk.peterjurkovic.cpr.services.CsnService;
import sk.peterjurkovic.cpr.services.CsnTerminologyLogService;
import sk.peterjurkovic.cpr.services.CsnTerminologyService;


@Controller
public class CsnTerminologyImportController extends SupportAdminController {
	
	private static final int TAB_INDEX = 1;
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
	private String showImportPage(){
		
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
				String baseName = getClasificationSymbol(file.getOriginalFilename());
				if(baseName.matches("\\d{4,7}")){
					Csn csn = csnService.getByClassificationSymbol(baseName);
					if(csn == null){
						result.reject("csn.terminology.import.error.notfound", new Object[]{baseName}, "") ;
					}else{
						TikaProcessingContext tikaProcessingContext = new TikaProcessingContext();
						CsnTerminologyLog log = tikaProcessingContext.getLog();
						tikaProcessingContext.getLog().setFileName(file.getOriginalFilename());
						long start = System.currentTimeMillis();
						try{
							
							
							tikaProcessingContext.setCsnId(csn.getId());
							tikaProcessingContext.setContextPath(request.getContextPath());
							tikaProcessingContext.getLog().setCsn(csn);
							String docAsHtml = wordDocumentParser.parse(file.getInputStream(), tikaProcessingContext);
							if(StringUtils.isNotBlank(docAsHtml)){
								tikaProcessingContext.logDomParsing();
								TerminologyParser terminologyParser = new NewTerminologyParserImpl();
								CsnTerminologyDto terminologies = terminologyParser.parse(docAsHtml, tikaProcessingContext);
								if(terminologies != null){
									
									terminologies.setCsn(csn);
									log.setCzCount(terminologies.getCzechTerminologies().size());
									log.setEnCount(terminologies.getEnglishTerminologies().size());
									
									csnTerminologyService.saveTerminologies(terminologies);
									csnService.saveOrUpdate(csn);
									log.setDuration(System.currentTimeMillis() - start);
									log.setSuccess(true);
									terminologyLogService.createWithUser(log);
								}
							}
						} catch (Exception  e) {
							log.logError(String.format("dokument %1$s se nepodařilo importovat, duvod: %2$s",  file.getOriginalFilename(), e.getMessage()));
						}
						log.updateImportStatus();
						terminologyLogService.createWithUser(log);
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
		
		
		
		return getViewName();
	}
	
	
	
	
	
	private String getClasificationSymbol(String fileName){
		return FilenameUtils.getBaseName(fileName);
	}
	
}
