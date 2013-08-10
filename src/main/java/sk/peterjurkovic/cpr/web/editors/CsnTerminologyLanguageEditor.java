package sk.peterjurkovic.cpr.web.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.stereotype.Component;

import sk.peterjurkovic.cpr.enums.CsnTerminologyLanguage;

/**
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Component
public class CsnTerminologyLanguageEditor extends PropertyEditorSupport{

	@Override
	public void setAsText(String code) throws IllegalArgumentException {
		CsnTerminologyLanguage language = CsnTerminologyLanguage.getByCode(code);
		setValue(language);
	}
}
