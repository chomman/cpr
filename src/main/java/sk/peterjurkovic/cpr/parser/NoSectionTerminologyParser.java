package sk.peterjurkovic.cpr.parser;

import java.util.ListIterator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sk.peterjurkovic.cpr.entities.CsnTerminology;
import sk.peterjurkovic.cpr.enums.CsnTerminologyLanguage;


public class NoSectionTerminologyParser extends NewTerminologyParserImpl implements TerminologyParser{
	
	private int counterCz = 1;
	private int counterEn = 1;
	
	@Override
	protected void extractTable(Element table){
		Validate.notNull(table);
		
		Elements trs = table.select("tr");
		
		if(trs.size() > 0){
			
			int engTdIndex = identifyCollsOffset(table, trs.first().select("td").size());
			logger.info("EN position index is: " + engTdIndex);
			
			ListIterator<Element> trIterator =  trs.listIterator();
			while(trIterator.hasNext()){
				Element tr = trIterator.next();
				Elements tds = tr.select("td");
					
				ListIterator<Element> tdIterator =  tds.listIterator();
				int index = 0;
				while (tdIterator.hasNext()) {
					Element td = tdIterator.next();
					
					if(index == 0){
						processCell(td,  CsnTerminologyLanguage.CZ);
						counterCz++;
					}else if(index == engTdIndex){
						processCell(td,  CsnTerminologyLanguage.EN);
						counterEn++;
					}else if(engTdIndex == 2 && czCollisBlank && StringUtils.isNotBlank(removeEmptyTags(td.html())) ){
						logger.info("cell before is bnank. This cells content is: " + td.html());
						middleCellContent = td.html();
					}
					
					index++;
				}
			}
			findContentAfterTable(table);
		}else{
			logger.info("v tabulke bolo najdenych 0 td elelemntov");
		}
	}
	
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
		
		Elements bElements = cell.select("b");
		if(bElements.size() == 0){
			appendContent(cell.html(), lang);
			logger.info("B element not found: " + cell);
		}else{
			Elements childrenCellsElements = cell.children();
			if(childrenCellsElements.size() > 0){
				Element firstChildrenElement = childrenCellsElements.first();
				if(firstChildrenElement.select("b").size() == 1){
					CsnTerminology terminology = createNewTerminology(firstChildrenElement.text(), lang);
					if(terminology != null){
						
						terminology.setContent(cell.html());
						saveTerminology(terminology);
					}
				}
			}
		}
	}
	

	protected CsnTerminology createNewTerminology(String title, CsnTerminologyLanguage lang) {
		if(StringUtils.isNotBlank(title)){
			CsnTerminology newTerminology = new CsnTerminology();
			 newTerminology.setLanguage(lang);
			 newTerminology.setSection(nextSectionCode(lang));
			 newTerminology.setTitle(removePitPairAndTrim(title));
			 newTerminology.setContent("");
			 return newTerminology;
		}
		return null;
	}
	
	
	
	private String nextSectionCode(CsnTerminologyLanguage lang){
		if(lang.equals(CsnTerminologyLanguage.CZ)){
			return (counterCz)+"";
		}else{
			return (counterEn)+"";
		}
	}
}
