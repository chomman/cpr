package sk.peterjurkovic.cpr.parser;

import sk.peterjurkovic.cpr.dto.CsnTerminologyDto;

public interface TerminologyParser {
	
	CsnTerminologyDto parse(String html, TikaProcessingContext tikaProcessingContext);
	
	
}
