package cz.nlfnorm.web.controllers.fontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import cz.nlfnorm.entities.Webpage;
import cz.nlfnorm.enums.WebpageType;
import cz.nlfnorm.exceptions.PageNotFoundEception;
import cz.nlfnorm.exceptions.PortalAccessDeniedException;
import cz.nlfnorm.services.WebpageService;
import cz.nlfnorm.utils.UserUtils;
import cz.nlfnorm.utils.WebpageUtils;
import freemarker.log.Logger;

public class WebpageControllerSupport {
	
	public static final String CURRENCY_PARAM = "currency";
	public static final String SOURCE_PARAM = "source";
	public final static String PORTAL_MODEL_KEY = "isPortal";
	public final static String EN_PREFIX = "/en/";
	public final static String WEBPAGE_MODEL_KEY = "webpageModel";
	private final static String PRODUCT_KEY = "pid";
	
	protected Logger logger = Logger.getLogger(getClass().getName());

	private String viewDirectory;
	
	@Autowired
	protected WebpageService webpageService;
	
	
	public Webpage getWebpage(final Long id) throws PageNotFoundEception, PortalAccessDeniedException{
		return test( webpageService.getWebpageById(id) );
	}
	
	
	public Webpage getWebpage(final String code) throws PageNotFoundEception, PortalAccessDeniedException{
		return test( webpageService.getWebpageByCode(code) );
	}
	
	
	
	private Webpage test(Webpage webpage) throws PageNotFoundEception, PortalAccessDeniedException{
		if(webpage == null || !webpage.isEnabled()){
			throw new PageNotFoundEception();
		}

		if(!UserUtils.isPortalAuthorized() && WebpageUtils.isOnlyForRegistraged(webpage)){
			throw new PortalAccessDeniedException("You have not perrmission to acccess webpage: " + webpage.getId());
		}
		return webpage;
	}
	
	protected String resolveViewFor(final Webpage webpage) {
		if(webpage.getWebpageModule() != null){
			return "forward:" + webpage.getWebpageModule().getFourwardUrl();
		}
		final WebpageType webpageType = webpage.getWebpageType();
		Validate.notNull(webpageType);
		return getViewDirectory() + "web/"+ webpageType.getViewName();
	}


	public String getViewDirectory() {
		return viewDirectory;
	}


	public void setViewDirectory(String viewDirectory) {
		this.viewDirectory = viewDirectory;
	}
	
	protected Map<String, Object> prepareModel(Webpage webpage){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webpage", webpage);
		return model;
	}
	
	
	protected String appendModelAndGetView(ModelMap modelMap, Webpage webpage){	
		appendModel(modelMap, webpage);
		return resolveViewFor(webpage);
	}
	
	protected void appendModel(ModelMap modelMap, Webpage webpage) {
		modelMap.put(WEBPAGE_MODEL_KEY, prepareModel(webpage));
	}
	
	public void appendSelectedProduct(Map<String, Object> model, HttpServletRequest request){
		final Long pid = getProduct(request);
		if(pid != null){
			model.put(PRODUCT_KEY, pid);
		}
	}
	
	public Long getProduct(HttpServletRequest request){
		final String strProductId = request.getParameter(PRODUCT_KEY);
		if(StringUtils.isNotBlank(strProductId)){
			try{
				return Long.valueOf(strProductId);
			}catch(NumberFormatException e){}
		}
		return null;
	}
	
}
