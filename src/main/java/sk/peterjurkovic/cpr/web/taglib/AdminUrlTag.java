package sk.peterjurkovic.cpr.web.taglib;

import sk.peterjurkovic.cpr.constants.Constants;


public class AdminUrlTag extends UrlTag {
	
	private static final long serialVersionUID = 1323232653265354161L;

	@Override
	public void appendUrl(StringBuilder url){
		url.append(getRequestContext().getContextPath()).append("/");
		url.append(Constants.ADMIN_PREFIX);
		url.append(getHref());
	}
	
}
