package cz.nlfnorm.quasar.web.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cz.nlfnorm.dto.FileUploadItemDto;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.quasar.constants.AuditorFilter;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.CategorySpecificTraining;
import cz.nlfnorm.quasar.entities.NandoCode;
import cz.nlfnorm.quasar.entities.TrainingLog;
import cz.nlfnorm.quasar.security.AccessUtils;
import cz.nlfnorm.quasar.services.AuditorService;
import cz.nlfnorm.quasar.services.TrainingLogService;
import cz.nlfnorm.quasar.web.validators.TrainingLogValidator;
import cz.nlfnorm.services.FileService;
import cz.nlfnorm.services.impl.FileServiceImpl;
import cz.nlfnorm.utils.UserUtils;

@Controller
public class TrainingLogController extends LogControllerSupport{
	
	private final static String SAVE_DIR =  FileServiceImpl.QUASAR_DIR_NAME + File.separatorChar +"logs";
	
	private final static String UPLOAD_ERROR_PARAM = "uploadError";
	private final static String ACTION_NANDO_CODE_ADD = "codeAdd";
	private final static String ACTION_NANDO_CODE_REMOVE = "codeRemove";
	private final static String ACTION_ASSIGN_WORKER = "assign";
	private final static String ACTION_UNASSIGN_WORKER = "unassign";
	
	private final static int TAB = 12;
	private final static String LIST_MAPPING_URL = "/admin/quasar/training-logs";
	public final static String EDIT_MAPPING_URL = "/admin/quasar/training-log/{id}";
	
	@Autowired
	private TrainingLogService trainingLogService;
	@Autowired
	private AuditorService auditorService;
	@Autowired
	private TrainingLogValidator trainingLogValidator;
	@Autowired
	private FileService fileService;
	
	public TrainingLogController(){
		setTableItemsView("training-log-list");
		setEditFormView("training-log-edit");
	}
	
	@InitBinder
	@Override
	public void initBinder(WebDataBinder binder) {
		super.initBinder(binder);
	}
	
	@RequestMapping(LIST_MAPPING_URL)
	public String handleListOfTrainingLogs(ModelMap modelMap, HttpServletRequest request) {
		Map<String, Object> model = handlePageRequest(request, trainingLogService, LIST_MAPPING_URL);
		appendTabNo(model, TAB);
		appendModel(modelMap, model);
		return getTableItemsView();
	}
	
	@RequestMapping(value = EDIT_MAPPING_URL, method = RequestMethod.GET)
	public String handleTraningLogEdit(
			ModelMap modelMap, @PathVariable long id, 
			HttpServletRequest request,
			@RequestParam(required = false) String action) throws ItemNotFoundException{
		if(id == 0){
			id = createNewTrainingLog();
			return successUpdateRedirect(EDIT_MAPPING_URL.replace("{id}", id+""));
		}
		final TrainingLog log = getTrainingLog(id);
		
		if(isSucceded(request) || processAction(log, action, request)){
			appendSuccessCreateParam(modelMap);
		}else{
			modelMap.put("uploadError", request.getParameter(UPLOAD_ERROR_PARAM) != null);
		}

		prepareModelFor(log, modelMap);
		return getEditFormView();
	}
	
	@RequestMapping(value = EDIT_MAPPING_URL, method = RequestMethod.POST)
	public String processSubmitTrainingLog(
			ModelMap modelMap, 
			@Valid @ModelAttribute(COMMAND) TrainingLog form,
			BindingResult result,
			@PathVariable long id, 
			HttpServletRequest request) throws ItemNotFoundException{
		boolean hasErrors = result.hasErrors();
		if(!hasErrors){
			trainingLogValidator.validate(form, result);
			hasErrors = result.hasErrors();
			if(!hasErrors){
				update(form);
				appendSuccessCreateParam(modelMap);
			}
		}
		prepareModelFor(form, modelMap);
		return getEditFormView();
	}
	
	@RequestMapping(value = EDIT_MAPPING_URL +"/upload", method = RequestMethod.GET)
	public String removeAttachement(@PathVariable long id) throws ItemNotFoundException{
		final TrainingLog log = getTrainingLog(id);
		if(fileService.removeFile(SAVE_DIR + File.separatorChar + log.getAttachment())){
			log.setAttachment(null);
			perists(log);
		}
		return successUpdateRedirect(getEditUrl(id));
	}
	
	@RequestMapping(value = EDIT_MAPPING_URL +"/upload", method = RequestMethod.POST)
	public String processUpload(
			ModelMap modelMap, 
			FileUploadItemDto form,
			BindingResult result,
			@PathVariable long id, 
			HttpServletRequest request) throws ItemNotFoundException{
		try {
			uploadFile(form.getFileData(), id);
			return successUpdateRedirect(getEditUrl(id));
		} catch (Exception e) {
			return "redirect:" + getEditUrl(id) +"?"+UPLOAD_ERROR_PARAM+"=1#upload";
		}		
	}
	
	private void uploadFile(MultipartFile file, final long auditLogId) throws IOException, ItemNotFoundException{
		Validate.notEmpty(file.getOriginalFilename());
		InputStream content = null;
		final TrainingLog log = getTrainingLog(auditLogId);
		try{
			content = file.getInputStream();
			final String logFileName = getFileNameFor(file.getOriginalFilename(),auditLogId);
			fileService.saveFile(logFileName, content, SAVE_DIR , true);
			log.setAttachment(logFileName);
			perists(log);
		}finally{
			if(content != null){
	    		content.close();
	    	}
		}
	}
	
	private String getFileNameFor(final String fileName, final long id){
		return "training-log-" + id + "." +	FilenameUtils.getExtension(fileName);
	}
	
	private void update(TrainingLog form) throws ItemNotFoundException{
		final TrainingLog log = getTrainingLog(form.getId());
		log.setMdd(form.getMdd());
		log.setIvd(form.getIvd());
		log.setAimd(form.getAimd());
		log.setIso13485(form.getIso13485());
		log.setIso9001(form.getIso9001());
		log.setNb1023Procedures(form.getNb1023Procedures());
		log.setSubject(form.getSubject());
		log.setDate(form.getDate());
		log.setDescription(form.getDescription());
		perists(log);
	}
	
	private void prepareModelFor(final TrainingLog form, ModelMap modelMap) throws ItemNotFoundException{
		final Map<String, Object> model = new HashMap<>();
		final TrainingLog log = trainingLogService.getById(form.getId()); 
		final boolean isEditable =  isEditable(log);
		model.put("log", log);
		model.put("statusType", ChangeLogStatusController.ACTION_TRAINING_LOG);
		if(isEditable){
			model.put("unassignedAuditors", trainingLogService.getUnassignedAuditorsFor(log));
			model.put("unassignedNandoCodes",trainingLogService.getAllUnassignedNandoCodesForLog(log)); 
			model.put("codeAdd",ACTION_NANDO_CODE_ADD);
			model.put("codeRemove",ACTION_NANDO_CODE_REMOVE);
			model.put("assign", ACTION_ASSIGN_WORKER);
			model.put("unassign", ACTION_UNASSIGN_WORKER);
			model.put("isManager", AccessUtils.isLoggedUserPartnerManager());
		}
		model.put("isEditable", isEditable);
		modelMap.put(COMMAND, form);
		appendTabNo(model, TAB);
		appendModel(modelMap, model);
	}
	
	private boolean isEditable(final TrainingLog log){
		return log.isEditable() && log.getChangedBy().equals(UserUtils.getLoggedUser());
	}
	
	
	
	private boolean processAction(final TrainingLog log, final String action,final  HttpServletRequest request){
		if(StringUtils.isBlank(action)){
			return false;
		}
		final Long id = getItemId(request);
		if(action.equals(ACTION_UNASSIGN_WORKER)){
			return removeWorkerFormLog(log, id);
		}else if(action.equals(ACTION_ASSIGN_WORKER)){
			return addWorkerToLog(log, id);
		}else if(action.equals(ACTION_NANDO_CODE_ADD)){
			return createCategorySpecificTraining(log, id, getHoursParamFormRequest(request));
		}else if(action.equals(ACTION_NANDO_CODE_REMOVE)){
			removeCategoriSpecificTraining(log, id);
		}
		return false;
	}
	
	
	private boolean removeCategoriSpecificTraining(final TrainingLog log, final Long cstId){
		if(log.removeCategorySpecificTraining(cstId)){
			perists(log);
			return true;
		}
		return false;
	}
	
	
	private boolean createCategorySpecificTraining(final TrainingLog log, final Long nandoCodeId, int hours){
		final NandoCode code = nandoCodeService.getById(nandoCodeId);
		if(code != null && hours > 0){
			final CategorySpecificTraining cst = new CategorySpecificTraining(log, code, hours);
			if(log.addCategorySpecificTraining(cst)){
				// if was successfully added, persists
				perists(log);
				return true;
			}
		}
		// otherwise CST exists with given NANDO code
		return false;
	}
	
	private boolean addWorkerToLog(final TrainingLog log, final Long userId){
		final Auditor auditor = auditorService.getById(userId);
		if(auditor != null){
			log.addAuditor(auditor);
			perists(log);
			return true;
		}
		return false;
	}
	
	private boolean removeWorkerFormLog(final TrainingLog log, final Long userId){
		if(log.removeAuditor(userId)){
			perists(log);
			return true;
		}
		return false;
	}
	
	private Long createNewTrainingLog(){
		if(AccessUtils.isLoggedUserPartnerManager()){
			return trainingLogService.createNew();
		}
		return trainingLogService.createNewToLoginedUser();
	}
	
	
	private int getHoursParamFormRequest(HttpServletRequest request){
		return Integer.valueOf(request.getParameter("hours"));
	}
	

	
	@Override
	protected void preparePageCriteria(Map<String, Object> criteria) {
		final User user = UserUtils.getLoggedUser();
		if(!user.isQuasarAdmin()){
			criteria.put(AuditorFilter.CREATED_BY, user.getId());
			criteria.put(AuditorFilter.AUDITOR, user.getId());
		}
	}
	
	private TrainingLog getTrainingLog(final long id) throws ItemNotFoundException{
		final TrainingLog trainingLog = trainingLogService.getById(id);
		if(trainingLog == null){
			throw new ItemNotFoundException();
		}
		final User user = UserUtils.getLoggedUser();
		if(AccessUtils.isLoggedUserAuthorizedTo(trainingLog) || 
		   AccessUtils.isUserPartnerManager(user)){
			return trainingLog;
		}
		if(trainingLog.containsAuditor(user.getId())){
			return trainingLog;
		}
		throw new AccessDeniedException("Use has not rights to access: " +user.toString() + trainingLog.toString());
	}
	
	private void perists(final TrainingLog log){
		trainingLogService.updateAndSetChanged(log);
	}
	
	private String getEditUrl(final long id){
		return EDIT_MAPPING_URL.replace("{id}", id+"");
	}
}
