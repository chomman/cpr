package sk.peterjurkovic.cpr.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.dto.PageDto;
import sk.peterjurkovic.cpr.entities.CsnTerminology;
import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.exceptions.PageNotFoundEception;
import sk.peterjurkovic.cpr.services.CsnService;
import sk.peterjurkovic.cpr.services.CsnTerminologyService;
import sk.peterjurkovic.cpr.services.WebpageService;
import sk.peterjurkovic.cpr.utils.RequestUtils;
import sk.peterjurkovic.cpr.web.pagination.PageLink;
import sk.peterjurkovic.cpr.web.pagination.PaginationLinker;

/**
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Controller
public class PublicCsnTerminologyController {

	public static final String PUBLIC_CSN_TERMINOLOGY_URL = "/terminologicky-slovnik";
	

	
	@Autowired
	private WebpageService webpageService;
	@Autowired
	private CsnTerminologyService csnTerminologyService;
	@Autowired
	private CsnService csnService;
	
	
	@RequestMapping(PUBLIC_CSN_TERMINOLOGY_URL)
	public String showSearchForm(ModelMap modelMap, HttpServletRequest request) throws PageNotFoundEception{
		
		Webpage webpage = webpageService.getWebpageByCode(PUBLIC_CSN_TERMINOLOGY_URL);
		if(webpage == null || !webpage.getEnabled()){
			throw new PageNotFoundEception();
		}
		int currentPage = RequestUtils.getPageNumber(request);
		Map<String, Object> model = prepareBaseModel(webpage);
		Map<String, Object> params = RequestUtils.getRequestParameterMap(request);
		PageDto page = csnTerminologyService.getCsnTerminologyPage(currentPage, params);
		
		if(page.getCount() > 0){
			model.put("paginationLinks", getPaginationItems(request, params, currentPage, page.getCount(), webpage));
			model.put("page", page.getItems() );
		}
		
		modelMap.put("model", model);
		return "/public/csn/terminology-search";
	}
	
	
	@RequestMapping(PUBLIC_CSN_TERMINOLOGY_URL+ "/{id}")
	public String showTerminologyDetial(ModelMap modelMap, @PathVariable Long id, HttpServletRequest request) throws PageNotFoundEception{
		
		Webpage webpage = webpageService.getWebpageByCode(PUBLIC_CSN_TERMINOLOGY_URL);
		CsnTerminology termonology = csnTerminologyService.getById(id);
		if(webpage == null || !webpage.getEnabled() || termonology == null || !termonology.getEnabled()){
			throw new PageNotFoundEception();
		}
		String lang = RequestUtils.getLangParameter(request);
		Map<String, Object> model = prepareBaseModel(webpage);
		model.put("subtab", webpage.getId());
		model.put("terminology", termonology);
		if(StringUtils.isNotBlank(lang)){
			model.put("lang", lang);
			model.put("terminologies", csnService.getTerminologyByCsnAndLang(termonology.getCsn(), lang));
		}
		modelMap.put("model", model);
		return "/public/csn/terminology-detail";
	}
	
	
	
	private  List<PageLink> getPaginationItems(HttpServletRequest request, Map<String, Object> params, int currentPage, int count, Webpage webpage){
		PaginationLinker paginger = new PaginationLinker(request, params);
		paginger.setUrl("/"+webpage.getCode());
		paginger.setCurrentPage(currentPage);
		paginger.setRowCount(count);
		return paginger.getPageLinks(); 
	}

	
	/**
	 * Pripravi zakladny model pre view
	 * 
	 * @param Webpage sekcia systemu
	 * @return pripraveny model
	 */
	private Map<String, Object> prepareBaseModel(Webpage webpage){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webpage", webpage);
		return model;
	}
}
