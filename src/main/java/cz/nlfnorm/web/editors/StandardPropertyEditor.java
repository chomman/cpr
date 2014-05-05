package cz.nlfnorm.web.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.nlfnorm.entities.Standard;
import cz.nlfnorm.services.StandardService;

@Component
public class StandardPropertyEditor extends PropertyEditorSupport {
	
	@Autowired
	private StandardService standardService;
	
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Long id = null;
		try {
            id = Long.parseLong(text);
        } catch (NumberFormatException nfe) {
            setValue(null);
            return;
        }
		setValue(standardService.getStandardById(id));
	}
	
	@Override
	public String getAsText() {
		Standard standard = (Standard)getValue();
		if(standard != null){
			return standard.getId().toString();
		}
		return "";
	}
}
