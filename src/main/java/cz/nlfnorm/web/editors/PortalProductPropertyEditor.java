package cz.nlfnorm.web.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.nlfnorm.services.PortalProductService;

@Component
public class PortalProductPropertyEditor extends PropertyEditorSupport{
	
	@Autowired
	private PortalProductService portalProductService;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Long id = null;
		try {
			id = Long.parseLong(text);
		} catch (Exception e) {
			setValue(null);
			return;
		}
		setValue(portalProductService.getById(id));
	}
	
	
	
}
