package sk.peterjurkovic.cpr.web.taglib;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

@Configurable
public class StandardCsnUrlTag extends UrlTag {
	
	@Value("#{config.csnonline.url}")
	private String csnOnlineUrl;
	private Object object;
	private boolean editable = false;
	
	
	
	
}
