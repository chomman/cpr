package sk.peterjurkovic.cpr.web.controllers.admin.cpr;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sk.peterjurkovic.cpr.entities.StandardCsn;
import sk.peterjurkovic.cpr.entities.StandardCsnChange;
import sk.peterjurkovic.cpr.exceptions.ItemNotFoundException;
import sk.peterjurkovic.cpr.services.StandardCsnService;
import sk.peterjurkovic.cpr.services.StandardService;
import sk.peterjurkovic.cpr.utils.UserUtils;
import sk.peterjurkovic.cpr.web.controllers.admin.SupportAdminController;
import sk.peterjurkovic.cpr.web.editors.LocalDateEditor;

/**
 * Controller spracovavajuci poziadavky Zmien ČSN
 * 
 * @author Peter Jurkovič
 * @date Jan 3, 2014
 *
 */
@Controller
public class StandardCsnController extends SupportAdminController {
	
	private static final int CPR_TAB_INDEX = 1;
	private static final String SUCCESS_PARAM = "successCreate";
	private static final String MAPPING_URL = "/admin/cpr/standard-csn/{csnId}/change/{id}";
	
	
	@Autowired
	private StandardCsnService standardCsnService;
	@Autowired
	private StandardService standardService;
	@Autowired
	private LocalDateEditor localDateEditor;
	
	public StandardCsnController(){
		setViewName("cpr/standard-csn-change");
	}
	
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(LocalDate.class, this.localDateEditor);
    }
	
	
	@RequestMapping( value = MAPPING_URL, method = RequestMethod.GET)
	public String showStandardCsnChangeForm(
			HttpServletRequest request,
			@PathVariable Long csnId, 
			@PathVariable Long id, ModelMap map) throws ItemNotFoundException {
		
		StandardCsn csn = getCsn(csnId);
		StandardCsnChange form = null;
		if(id == null || id == 0){
			form = new StandardCsnChange();
			form.setId(0l);
		}else{
			form = csn.getStandardCsnChangeById(id);
			if(form == null){
				createItemNotFoundError("Změna "+ csn.getCsnName() + " s ID: " +id+" se v systému nenachází");
			}
		}
		if(StringUtils.isNotBlank(request.getParameter(SUCCESS_PARAM))){
			map.put(SUCCESS_PARAM, true);
		}
		prepareModel(csn, form, map);
        return getViewName();
	}
	
	
	
	@RequestMapping( value = MAPPING_URL, method = RequestMethod.POST)
	public String processSubmit(
			@PathVariable Long csnId, 
			@PathVariable Long id,
			@Valid StandardCsnChange form, BindingResult result, ModelMap model
			) throws ItemNotFoundException {
		
		StandardCsn csn = getCsn(csnId);
		
		if(result.hasErrors()){
			prepareModel(csn, form, model);
			return getViewName();
		}
		return "redirect:/admin/cpr/standard-csn/"+csnId+"/change/" + createOrUpdate(form, csn) + "?"+SUCCESS_PARAM+"=1";
	}
	
	
	
	
	private long createOrUpdate(StandardCsnChange form, StandardCsn csn) throws ItemNotFoundException{
		StandardCsnChange csnChange = null;
		if(form.getId() == 0 || form.getId() == null){
			csnChange = new StandardCsnChange();
			csnChange.setCreatedBy(UserUtils.getLoggedUser());
			csn.getStandardCsnChanges().add(csnChange);
		}else{
			csnChange = csn.getStandardCsnChangeById(form.getId());
			if(csnChange == null){
				createItemNotFoundError("CSN change was not found [id="+form.getId()+"]");
			}
		}
		csnChange.setStandardCsn(csn);
		csnChange.setCsnOnlineId(form.getCsnOnlineId());
		csnChange.setChangeCode(form.getChangeCode());
		csnChange.setDate(form.getDate());
		csnChange.setNote(form.getNote());
		csnChange.setChanged(new LocalDateTime());
		csnChange.setChangedBy(UserUtils.getLoggedUser());
		standardCsnService.saveOrUpdate(csn);
		return form.getId();
	}
	
	private StandardCsn getCsn(final long csnId) throws ItemNotFoundException{
		StandardCsn csn = standardCsnService.getCsnById(csnId);
		if(csn == null){
			createItemNotFoundError("CSN s ID: "+ csnId + " se v systému nenachází");
		}
		return csn;
	}
	
	private void prepareModel(StandardCsn csn, StandardCsnChange form, ModelMap map){
		Map<String, Object> model = new HashMap<String, Object>();
		map.addAttribute("csn", csn);
		map.addAttribute("standardCsnChange", form);
		model.put("standard", standardService.getStandardByCsn(csn));
		model.put("tab", CPR_TAB_INDEX);
		map.put("model", model); 
	}
	
	
	
}