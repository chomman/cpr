package cz.nlfnorm.web.editors;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.nlfnorm.services.CommissionDecisionService;

/**
 * Property editor konvertujuci rozhodnutie komisie
 * 
 * @see CommissionDecision
 * @author Peter Jurkovič
 * @date Dec 9, 2013
 *
 */
@Component
public class CommissionDecisionEditor extends PropertyEditorSupport{
	
	@Autowired
	private CommissionDecisionService commissionDecisionService;
	
	
	
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
		setValue(commissionDecisionService.getById(id));
	}
	
	
}
