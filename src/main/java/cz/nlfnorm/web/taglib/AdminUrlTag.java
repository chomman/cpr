package cz.nlfnorm.web.taglib;

import cz.nlfnorm.constants.Constants;


public class AdminUrlTag extends UrlTag {
	
	private static final long serialVersionUID = 1323232653265354161L;
	private Boolean activeTab;
	
	@Override
	protected int doStartTagInternal() throws Exception {
		if(activeTab != null && activeTab){
			setCssClass( getCssClass() != null ? 
					 getCssClass() : "" + " active");
		}
		return super.doStartTagInternal();
	}
	
	@Override
	public void appendUrl(StringBuilder url){
		url.append(getRequestContext().getContextPath()).append("/");
		url.append(Constants.ADMIN_PREFIX);
		url.append(getHref());
	}

	public Boolean getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(Boolean activeTab) {
		this.activeTab = activeTab;
	}
	
	
	
}
