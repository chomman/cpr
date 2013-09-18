package sk.peterjurkovic.cpr.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import sk.peterjurkovic.cpr.dto.CsnTerminologyDto;
import sk.peterjurkovic.cpr.entities.CsnTerminology;
import sk.peterjurkovic.cpr.enums.CsnTerminologyLanguage;

public class NewTerminologyParserImpl implements TerminologyParser {

	Logger logger = Logger.getLogger(getClass());
	
	private List<CsnTerminology> czechTerminologies = new ArrayList<CsnTerminology>();
	
	private List<CsnTerminology> englishTerminologies = new ArrayList<CsnTerminology>();
	
	private final Pattern regexPattern = Pattern.compile("^(\\d+(\\.\\d+)*)+\\s*(.*)$", Pattern.MULTILINE | Pattern.DOTALL);
	
	private boolean prevTDisBlank = false;
	
	private String middleCellContent = null;
	
	@Override
	public CsnTerminologyDto parse(String html, TikaProcessContext tikaProcessContext) {
		
		Document doc = Jsoup.parse(html);
		Elements tables = doc.select("table");
		
		if(tables.select("table").size() > 0){
			ListIterator<Element> iterator =  tables.listIterator();
			try{
				while(iterator.hasNext()){
					extractTable(iterator.next());
				}
				
				logger.info("Count of terminologies: " + czechTerminologies.size() + " / " + englishTerminologies.size());

				for(int i = 0; i < czechTerminologies.size(); i++){
					
					if(czechTerminologies.get(i) != null){
						logger.info(czechTerminologies.get(i).getSection() + " / " + czechTerminologies.get(i).getTitle());
					}
					
					if(englishTerminologies.get(i) != null){
						logger.info(englishTerminologies.get(i).getSection() + " / " + englishTerminologies.get(i).getTitle());
					}
				}
				
				return new CsnTerminologyDto(czechTerminologies, englishTerminologies);
			}catch(Exception e){
				logger.warn(e.getMessage());
			}
			
		}
		
		
		
		return null;
	}
	
	public void extractTable(Element table){
		Validate.notNull(table);
		
		int collsSize = table.select("tr").first().select("td").size(); 
		
		Elements tds = table.select("td");
		
		
		if(tds.size() > 0){	
			ListIterator<Element> iterator =  tds.listIterator();
			int j = 0;
			while(iterator.hasNext()){
				Element td = iterator.next();
				if((j == 0 || j == 2) && StringUtils.isNotBlank(td.text())){
					
					if(j == 0){
						processCell(td,  CsnTerminologyLanguage.CZ);
					}else{
						processCell(td,  CsnTerminologyLanguage.EN);
					}
					
				}else{
					if(prevTDisBlank && StringUtils.isNotBlank(td.html())){
						logger.info("cell before is bnank. This cells content is: " + td.html());
						middleCellContent = td.html();
					}
				}
				
				if(j ==  (collsSize - 1)){
					j = 0;
				}else{
					j++;
				}
			}
			findContentAfterTable(table);
		}else{
			logger.info("v tabulke bolo najdenych 0 td elelemntov");
		}
	}
	
	
	
	private void processCell(Element cell, CsnTerminologyLanguage lang){
		Validate.notNull(cell);
		
		if(StringUtils.isBlank(cell.html())){
			if(lang.equals(CsnTerminologyLanguage.CZ)){
				prevTDisBlank = true;
			}else if(prevTDisBlank && lang.equals(CsnTerminologyLanguage.EN) && StringUtils.isNotBlank(middleCellContent)){
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
			appendContent(cell.outerHtml(), lang);
			logger.info("B element not found: " + cell);
		}else{
			if(bElements.size() == 1){
				Element b = bElements.first();
				String bContent = b.text();
				
				if(StringUtils.isNotBlank(bContent)){
					 Matcher matcher = regexPattern.matcher(bContent.trim());
					 CsnTerminology terminology = null;
					 if (matcher.matches()) {
						 terminology = createNewTerminology(matcher, lang);
						 if(terminology != null){
							 terminology.setContent(cell.html().replace(bContent, ""));
							 saveTerminology(terminology);
						 }						 
					 }else{
						 logger.info("Nenasla sa zhoda: " + bContent);
					 }
				}
				
			}else{
				traverse(cell, lang);
				
			}
			
		}
		
	}
	
	private void traverse(Element cell, CsnTerminologyLanguage lang){
		if( cell == null){
			return;
		}
		Elements els = cell.children();
		logger.info(els.size());
		if(els.size() > 0){
			ListIterator<Element> iterator =  els.listIterator();
			while(iterator.hasNext()){
				Element currentElement = iterator.next();
				logger.info(currentElement.outerHtml());
				 Matcher matcher = regexPattern.matcher(currentElement.firstElementSibling().text().trim());
				 if(matcher.matches()){
					 logger.info("Match: " + matcher.group(0) );
				 }
			}
		
		}
		/*
		 * for(Node node : childrenNodes){
			if (node instanceof TextNode) {
				logger.info(((TextNode) node).text());
		    }
		}
		 * */
		
	}
	
	
	private void findContentAfterTable(Element table) {
		Validate.notNull(table);
		
		Element nextElement = table.nextElementSibling();
		int pomBreak = 0; 
		while(nextElement != null && nextElement.nodeName().equals("p") && pomBreak < 500){
			String html = nextElement.outerHtml();
			appendContent(html, CsnTerminologyLanguage.CZ);
			appendContent(html, CsnTerminologyLanguage.EN);
			nextElement = nextElement.nextElementSibling();
			pomBreak++;
		}
	}
	
	
	private CsnTerminology createNewTerminology(Matcher matcher, CsnTerminologyLanguage lang){
		String section = matcher.group(1);
		String title = matcher.group(3);
		
		if(StringUtils.isNotBlank(section) && StringUtils.isNotBlank(title)){
			 CsnTerminology newTerminology = new CsnTerminology();
			 newTerminology.setLanguage(lang);
			 newTerminology.setSection(section.trim());
			 newTerminology.setTitle(title.trim());
			 return newTerminology;
		}
		return null;
	}
	
	
	
	private void appendContent(String content, CsnTerminologyLanguage lang){
		if(StringUtils.isBlank(content)){
			logger.info("nothing to append");
			return ;
		}
		if(lang.equals(CsnTerminologyLanguage.EN) && CollectionUtils.isNotEmpty(englishTerminologies)){
			// appendneme text k predchadzajucej terminologii, ku ktorej patri
			CsnTerminology updateTerminology = englishTerminologies.get(englishTerminologies.size() -1);
			updateTerminology.setContent(updateTerminology.getContent() + content );
		}else if(lang.equals(CsnTerminologyLanguage.CZ) && CollectionUtils.isNotEmpty(czechTerminologies)){
			CsnTerminology updateTerminology = englishTerminologies.get(englishTerminologies.size() -1);
			updateTerminology.setContent(updateTerminology.getContent() + content );
		}
	}
	
	private void saveTerminology(CsnTerminology terminology){
		Validate.notNull(terminology);
		if(terminology.getLanguage().equals(CsnTerminologyLanguage.CZ)){
			czechTerminologies.add(terminology);
		}else{
			englishTerminologies.add(terminology);
		}
	}


}
