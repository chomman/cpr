package cz.nlfnorm.web.controllers.fontend;

import java.util.HashMap;
import java.util.Map;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.entities.Webpage;
import cz.nlfnorm.enums.WebpageType;
import cz.nlfnorm.utils.UserUtils;

public abstract class PortalWebpageControllerSupport extends WebpageControllerSupport {
	
	private final static Long MAIN_NAV_ID = 75l;
	private final static Long SUB_NAV_ID = 84l;

	
	public PortalWebpageControllerSupport(){
		setViewDirectory("/portal/");
	}
	
	@Override
	protected Map<String, Object> prepareModel(Webpage webpage){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webpage", webpage);
		
		if(webpage.getWebpageType().equals(WebpageType.NEWS_CATEGORY)){
			model.put("newsItems", webpageService.getLatestPublishedNews(30) );
		}
		model.put(PORTAL_MODEL_KEY, true);
		model.put("mainnav", webpageService.getChildrensOfNode(MAIN_NAV_ID, true));
		model.put("subnav", webpageService.getChildrensOfNode(SUB_NAV_ID, true));
		model.put("rootwebpage", webpageService.getWebpageByCode(Constants.PORTAL_URL));
		model.put("news", webpageService.getLatestPublishedNews(4) );
		model.put("portalParam", Constants.PORTAL_ID_PARAM_KEY);
		
		final User user = UserUtils.getLoggedUser();
		if(user != null){
			model.put("loggedUser", user);
		}else{
			model.put("registrationPage", webpageService.getWebpageByCode("registrace"));
		}
		return model;
	}
	
}
