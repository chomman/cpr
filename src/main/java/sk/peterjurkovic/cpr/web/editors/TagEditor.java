package sk.peterjurkovic.cpr.web.editors;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import sk.peterjurkovic.cpr.entities.Tag;

@Component
public class TagEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String tagName) throws IllegalArgumentException {
		
		if(StringUtils.isNotBlank(tagName)){
			Tag tag = new Tag();
			tag.setName(tagName);
			setValue(tag);
		}else{
			setValue(null);
		}		

	}
	
}
