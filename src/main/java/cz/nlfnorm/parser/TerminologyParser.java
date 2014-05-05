package cz.nlfnorm.parser;

import cz.nlfnorm.dto.CsnTerminologyDto;

public interface TerminologyParser {
	
	CsnTerminologyDto parse(String html, TikaProcessingContext tikaProcessingContext);
	
	
}
