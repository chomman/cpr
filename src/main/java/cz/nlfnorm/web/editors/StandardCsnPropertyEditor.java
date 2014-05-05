package cz.nlfnorm.web.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.nlfnorm.entities.StandardCsn;
import cz.nlfnorm.services.StandardCsnService;

@Component
public class StandardCsnPropertyEditor extends PropertyEditorSupport{
	
	@Autowired
	private StandardCsnService standardCsnService;
	
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Long id = null;
		try {
            id = Long.parseLong(text);
        } catch (NumberFormatException nfe) {
            setValue(null);
            return;
        }
		setValue(standardCsnService.getCsnById(id));
	}
	
	@Override
	public String getAsText() {
		StandardCsn csn = (StandardCsn)getValue();
		if(csn != null){
			return csn.getId().toString();
		}
		return "";
	}
	
}
