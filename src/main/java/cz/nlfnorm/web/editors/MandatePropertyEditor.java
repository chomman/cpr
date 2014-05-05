package cz.nlfnorm.web.editors;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.nlfnorm.services.MandateService;

@Component
public class MandatePropertyEditor extends PropertyEditorSupport{
	
	
	@Autowired
	private MandateService mandateService;
	
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		
		if(StringUtils.isBlank(text)){
			setValue(null);
			return;
		}
		
		Long id = null;
		try {
            id = Long.parseLong(text);
        } catch (NumberFormatException nfe) {
            setValue(null);
            return;
        }
		setValue(mandateService.getMandateById(id));
	}
	
	
}
