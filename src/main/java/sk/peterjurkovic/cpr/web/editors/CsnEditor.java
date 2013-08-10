package sk.peterjurkovic.cpr.web.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sk.peterjurkovic.cpr.entities.Csn;
import sk.peterjurkovic.cpr.services.CsnService;

/**
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 * @date 10.08.2013
 */

@Component
public class CsnEditor extends PropertyEditorSupport {
 
	@Autowired 
	private CsnService csnService;
	
	@Override
	public void setAsText(String strId) throws IllegalArgumentException {
		Long id = null;
		try {
			id = Long.parseLong(strId);
		} catch (Exception e) {
			setValue(null);
			return;
		}
		Csn csn =  csnService.getById(id);
		if(csn == null){
			new IllegalArgumentException("ČSN with ID: " + strId + " was not found.");
		}
		setValue(csn);
	}
	
}
