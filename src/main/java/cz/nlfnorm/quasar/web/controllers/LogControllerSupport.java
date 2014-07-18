package cz.nlfnorm.quasar.web.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;

import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.quasar.constants.AuditorFilter;
import cz.nlfnorm.quasar.entities.Company;
import cz.nlfnorm.quasar.entities.EacCode;
import cz.nlfnorm.quasar.entities.LogItem;
import cz.nlfnorm.quasar.entities.NandoCode;
import cz.nlfnorm.quasar.enums.LogStatus;
import cz.nlfnorm.quasar.services.CompanyService;
import cz.nlfnorm.quasar.services.EacCodeService;
import cz.nlfnorm.quasar.services.NandoCodeService;
import cz.nlfnorm.quasar.services.PageableLogService;
import cz.nlfnorm.quasar.services.PartnerService;
import cz.nlfnorm.quasar.web.forms.CompanyForm;
import cz.nlfnorm.quasar.web.forms.LogItemForm;
import cz.nlfnorm.utils.RequestUtils;
import cz.nlfnorm.utils.UserUtils;
import cz.nlfnorm.web.editors.IdentifiableByLongPropertyEditor;
import cz.nlfnorm.web.editors.LocalDateEditor;

public abstract class LogControllerSupport extends QuasarSupportController {

	protected final static String ITEM_ID_PARAM_NAME = "iid";
	
	@Autowired
	protected CompanyService companyService;
	@Autowired
	protected LocalDateEditor localDateEditor;
	@Autowired
	protected NandoCodeService nandoCodeService;
	@Autowired
	protected EacCodeService eacCodeService;
	@Autowired
	protected PartnerService partnerService;
	
	
	@Override
	protected String getViewDir() {
		return super.getViewDir() + "logs/";
	}


	@SuppressWarnings("rawtypes")
	public Map<String, Object> handlePageRequest(HttpServletRequest request, PageableLogService service, String url) {
		Map<String, Object> model = new HashMap<>();
		Map<String, Object> criteria = RequestUtils.getRequestParameterMap(request);
		final boolean isQuasarAdmin = UserUtils.getLoggedUser().isQuasarAdmin();
		if(!isQuasarAdmin){
			criteria.put(AuditorFilter.AUDITOR, UserUtils.getLoggedUser().getId());
		}
		final int currentPage = RequestUtils.getPageNumber(request);
		@SuppressWarnings("unchecked")
		final PageDto page = service.getPage(criteria, currentPage);
		if(page.getCount() > 0){
			model.put("paginationLinks", getPaginationItems(request, criteria, page.getCount(), url));
			model.put("logs", page.getItems());
		}
		model.put("statuses", LogStatus.getAll());
		model.put("params", criteria);
		model.put("isQuasarAdmin", isQuasarAdmin);
		model.put("partners", partnerService.getAll());
		return model;
	}

    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Company.class, new IdentifiableByLongPropertyEditor<Company>( companyService ));
		binder.registerCustomEditor(LocalDate.class, this.localDateEditor);
	}
    
    protected boolean isLogItemIdSet(HttpServletRequest request){
		return request.getParameter(ITEM_ID_PARAM_NAME) != null;
	}
    
    protected Long getItemId(HttpServletRequest request){
    	final String strId = request.getParameter(ITEM_ID_PARAM_NAME);
    	if(StringUtils.isNotBlank(strId)){
    		try{
    			return Long.valueOf(strId);
    		}catch(NumberFormatException e){}
    	}
    	return null;
    }
    
    /**
     * Sets EAC codes, which are represented as comma separated string.
     *  e.g.: "EAC 1, EAC 2"
     *  
     *  Method parse this string sequence, split it into separated code and for each one try out 
     *  corresponding persisted code.
     * 
     * @param Form 
     * @param Log item
     */
    protected void setEacCodes(final LogItemForm form, final LogItem item){
		item.clearEacCodes();
		if( StringUtils.isNotBlank(form.getStringEacCodes()) ){
			final String[] codeList = form.getStringEacCodes().split(",");
			for(final String code : codeList){
				final EacCode eacCode = eacCodeService.getByCode(code);
				Validate.notNull(eacCode);
				item.addEacCode(eacCode);
			}
		}
	}
	
    /**
     * Sets NANDO codes, which are represented as comma separated string.
     *  e.g.: "MD 0100, MD 0200"
     *  
     *  Method parse this string sequence, split it into separated code and for each one try out 
     *  corresponding persisted code.
     * 
     * @param Form 
     * @param Log item
     */
    protected void setNandoCodes(final LogItemForm form, final LogItem item){
		item.clearNandoCodes();
		if( StringUtils.isNotBlank(form.getStringNandoCodes()) ){
			final String[] codeList = form.getStringNandoCodes().split(",");
			for(final String code : codeList){
				final NandoCode nandoCode = nandoCodeService.getByNandoCode(code);
				Validate.notNull(nandoCode);
				item.addNandoCode(nandoCode);
			}
		}
	}
	
    /**
     * Set company into log item. If the Company is not set, but is set company name, 
     * before create new company, will search in existing companies and try to find companies 
     * which has company name like given name. If company was found, will be sets, instead of create new one.
     * 
     * 
     * @param form
     * @param item
     * @return TRUE, if was found company with given name in existing company, FALSE otherwise.
     */
    protected boolean setAndCreateCompany(final LogItemForm form, final CompanyForm item){
		if(form.getCompany() != null){
			// company was selected, use this
			item.setCompany(form.getCompany());
			return false;
		}
		if(StringUtils.isNotBlank(form.getCompanyName())){
			Company company = companyService.findByName(form.getCompanyName());
			if(company != null){
				// company was found
				item.setCompany(company);
				return true;
			}else{
				// not found, create new
				company = new Company(StringUtils.trim(form.getCompanyName()));
				companyService.create(company);
				item.setCompany(companyService.findByName(form.getCompanyName()));
			}
		}
		return false;
	}
}
