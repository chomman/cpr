package sk.peterjurkovic.cpr.parser;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sk.peterjurkovic.cpr.entities.CsnTerminology;
import sk.peterjurkovic.cpr.enums.CsnTerminologyLanguage;


public class SingleSectionTerminologyParser extends NoSectionTerminologyParser   {

	
	@Override
	protected void processCell(Element cell, CsnTerminologyLanguage lang) {
		if(StringUtils.isBlank(cell.html())){
			if(lang.equals(CsnTerminologyLanguage.CZ)){
				czCollisBlank = true;
			}else if(czCollisBlank && lang.equals(CsnTerminologyLanguage.EN) && StringUtils.isNotBlank(middleCellContent)){
				// ak je bunka s ceskym a anglickym terminom prazdna, ale stredna 
				// bunka obsahuje obsah, pripojimeho k predchadzajucemu anglickemu a ceskemu terminu
				appendContent(middleCellContent, CsnTerminologyLanguage.CZ);
				appendContent(middleCellContent, CsnTerminologyLanguage.EN);
			}
			logger.info("cell is blank");
			return;
		}
	
		Elements childrens = cell.children();
		int childrenSize = cell.children().size();
		Element firstChild = null;
		if(childrenSize == 1){
			firstChild = childrens.first();
		}
		if(firstChild != null && firstChild.tagName().equals("b") && childrenSize == 1){
			logger.info("SECTION: " + cell.text());
			CsnTerminology terminology = createNewTerminology(cell.text(), lang);
			if(terminology != null){
				terminology.setContent(cell.html());
				saveTerminology(terminology);
			}
		}else{
			logger.info("appending content: " + cell.html());
			appendContent(cell.html(), lang);
		}
		
	}
	
}
