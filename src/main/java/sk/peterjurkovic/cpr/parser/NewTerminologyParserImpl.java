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
import org.jsoup.select.Elements;

import sk.peterjurkovic.cpr.dto.CsnTerminologyDto;
import sk.peterjurkovic.cpr.entities.CsnTerminology;
import sk.peterjurkovic.cpr.enums.CsnTerminologyLanguage;

public class NewTerminologyParserImpl implements TerminologyParser {

	Logger logger = Logger.getLogger(getClass());
	
	private List<CsnTerminology> czechTerminologies = new ArrayList<CsnTerminology>();
	
	private List<CsnTerminology> englishTerminologies = new ArrayList<CsnTerminology>();
	
	private final Pattern regexPattern = Pattern.compile("^(\\d+(\\.\\d+)*)+\\s*(.*)$", Pattern.MULTILINE | Pattern.DOTALL);
	
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
			}catch(Exception e){
				logger.warn(e.getMessage());
			}
			
		}
		
		logger.info("Count of terminologies: " + czechTerminologies.size() + " / " + englishTerminologies.size());
		
		
		
		return null;
	}
	
	public void extractTable(Element table){
		Validate.notNull(table);
		
		int collsSize = table.select("tr").first().select("td").size(); 
		
		Elements tds = table.select("td");
		logger.info("tds size: " + tds.size());
		
		
		if(tds.size() > 0){	
			ListIterator<Element> iterator =  tds.listIterator();
			int j = 0;
			while(iterator.hasNext()){
				Element td = iterator.next();
				logger.info(j +" / "+td.text());
				if((j == 0 || j == 2) && StringUtils.isNotBlank(td.text())){
					
					if(j == 0){
						processCell(td,  CsnTerminologyLanguage.CZ);
					}else{
						processCell(td,  CsnTerminologyLanguage.EN);
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
				logger.info("Bunka obsahuje viac b elementov: " + cell.html());
			}
			
		}
		
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
		
		logger.info(section + " / " + title);
		
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
