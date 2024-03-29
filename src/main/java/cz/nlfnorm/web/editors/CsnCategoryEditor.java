package cz.nlfnorm.web.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.nlfnorm.services.CsnCategoryService;

/**
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 * @date 04.08.2013
 */
@Component
public class CsnCategoryEditor extends PropertyEditorSupport {
	
	@Autowired
	private CsnCategoryService csnCategoryService;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Long id = null;
		try {
			id = Long.parseLong(text);
		} catch (Exception e) {
			setValue(null);
			return;
		}
		setValue(csnCategoryService.getById(id));
	}
	
}
