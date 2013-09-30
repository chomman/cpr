package sk.peterjurkovic.cpr.parser;

import java.util.ArrayList;
import java.util.Iterator;
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
	
	protected List<CsnTerminology> czechTerminologies = new ArrayList<CsnTerminology>();
	
	protected List<CsnTerminology> englishTerminologies = new ArrayList<CsnTerminology>();
	
	private final Pattern sectionMatcherRegex = Pattern.compile("^(\\d+(\\.\\d+)*)+\\s*(.*)$", Pattern.MULTILINE | Pattern.DOTALL);
	
	private final Pattern sectionCodeMatcherRegex = Pattern.compile("^(\\d+\\.\\d+(\\.\\d+)*)$", Pattern.MULTILINE | Pattern.DOTALL);
	
	protected boolean czCollisBlank = false;
	
	protected String middleCellContent = null;
	
	
	
	@Override
	public CsnTerminologyDto parse(String html, TikaProcessingContext tikaProcessingContext) {
		Validate.notNull(html);
		Validate.notNull(tikaProcessingContext);
		
		
		Document doc = Jsoup.parse(html);
		Elements tables = doc.select("table");
		
		if(tables.select("table").size() > 0){
			ListIterator<Element> iterator =  tables.listIterator();
			try{
				while(iterator.hasNext()){
					extractTable(iterator.next());
				}
				compareLanguages(tikaProcessingContext);
				
				/*
				 * 
				for(int i = 0; i < czechTerminologies.size(); i++){
					
					if(czechTerminologies.get(i) != null){
						logger.info(tmp(czechTerminologies.get(i).getSection()) + " / " + tmp(czechTerminologies.get(i).getTitle()));
					}
					
					if(englishTerminologies.get(i) != null){
						logger.info(tmp(englishTerminologies.get(i).getSection()) + " / " + tmp(englishTerminologies.get(i).getTitle()));
					}
				}
				 * */
				
				logger.info("Count of terminologies: " + czechTerminologies.size() + " / " + englishTerminologies.size());
				return new CsnTerminologyDto(czechTerminologies, englishTerminologies);
			}catch(Exception e){
				logger.warn(e.getMessage());
			}
			
		}
		
		
		
		return null;
	}
	
	public String tmp(String str){
		if(StringUtils.isBlank(str)){
			return "---";
		}
		return str;
	}
	
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
					}else if(index == engTdIndex){
						processCell(td,  CsnTerminologyLanguage.EN);
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
	
	
	
	protected int identifyCollsOffset(Element table, int tdSizeOnRow) {
		
		if(tdSizeOnRow == 2){
			// tabulka ma len dva stlpce
			// takze je iste, ze druhy obsahuje ENG terminy
			return 1;
		}
		Elements trs = table.select("tr");
		int trsSize = trs.size();
		if(trsSize > 0){
			int firstIndexEmpty = 0;
			ListIterator<Element> trIterator =  trs.listIterator();
			
			while(trIterator.hasNext() ){
				Element tr = trIterator.next();
				Elements tds = tr.select("td");
				if(tds.size() > 0){
					ListIterator<Element> tdIterator =  tds.listIterator();
					int index = 0;
					while(tdIterator.hasNext()){
						Element td = tdIterator.next();
						if(index == 1 && StringUtils.isNotBlank(removeEmptyTags(td.html()))){
							String c = removeEmptyTags(td.html());
							logger.info("BUNKA NIE JE PRAZDNA: " + c);
							firstIndexEmpty++;
						}
						index++;
					}
				}
			}
			
			if(Math.floor(trsSize / 2) <  firstIndexEmpty){
				// pocet neprazdnych buniek v druhom stlpci je vacsi 
				// ako polovica z celkoveho poctu riadkov tabulky
				// tz., ze anglicke terminy sa nachadzaju v riadku s indexom 1
				logger.info("Pocet neprazdnych buniek v druhom stlpci: "+Math.floor(trsSize / 2) + " / " + firstIndexEmpty);
				return 1;
			}
		}
		return 2;
	}
	
	

	protected void processCell(Element cell, CsnTerminologyLanguage lang){
		Validate.notNull(cell);
		
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
			appendContent(cell.outerHtml(), lang);
			logger.info("B element not found: " + cell);
		}else{
			if(bElements.size() == 1){
				Element b = bElements.first();
				String bContent = b.text();
				
				if(StringUtils.isNotBlank(bContent)){
					 Matcher matcher = sectionMatcherRegex.matcher(bContent.trim());
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
	
	protected void traverse(Element cell, CsnTerminologyLanguage lang){
		if( cell == null){
			return;
		}
		Elements els = cell.children();
		if(els.size() > 0){
			ListIterator<Element> iterator =  els.listIterator();
			while(iterator.hasNext()){
				// pomocna premena, pozivana v pripade ak je kod sekcie oddeleny medzerov
				
				Element currentElement = iterator.next();
				Elements childrens  = currentElement.children();
				Element b = null;
				if(childrens.size() > 0){
					b = childrens.first();
				}				
				
				if(b != null && b.tagName().equals("b")){
					String bContent = b.html().trim();
					if(StringUtils.isNotBlank(bContent)){
						 Matcher sectionMatcher = sectionMatcherRegex.matcher(bContent);
						 Matcher sectionCodeMatcher = sectionCodeMatcherRegex.matcher(bContent);
						
						 if(sectionCodeMatcher.matches()){
							 // cislo sekcie bungky a nazov sekcie je oddeleny medzerov
							 // nasledujuci p element musi obsahovat b element
							 // preto sa zrovna skontroluje nalsedujuci element
							 if(iterator.hasNext()){
								 
							 Element nextElementSibling = currentElement.nextElementSibling();
							 Elements nextElementChildrens  = nextElementSibling.children();
								Element nextBelement = null;
								// element p obsahuje
								if(nextElementChildrens.size() == 1){
									nextBelement = childrens.first();
								}
								// ak nasledujuci element existuje, a je type B, 
								if(nextBelement != null && nextBelement.tagName().equals("b")){
									String section = bContent;
									String sectionTitle = nextElementSibling.text();
									CsnTerminology terminology = createNewTerminology(section, sectionTitle, lang);
									if(terminology != null){
										saveTerminology(terminology);
									}
								}else{
									// rollback
									iterator.previous();
								}
							}	
						 }else if(sectionMatcher.matches()){
							 // multiple terminology in one cell
							 CsnTerminology terminology = createNewTerminology(sectionMatcher, lang);
							 logger.info("FOUND in multiple and match: " + bContent);
							 if(terminology != null){
								 terminology.setContent(currentElement.outerHtml(). replace(b.outerHtml(), ""));
								 saveTerminology(terminology);
							 }	
						 }
						 else{
							 logger.info("B content no match Appending to previous: " + currentElement.outerHtml());
							 appendContent(currentElement.outerHtml(), lang);
						 }
					}
				}else{
					logger.info("outer Appending to previous: " + currentElement.outerHtml());
					 appendContent(currentElement.outerHtml(), lang);
				}
			}
		
		}
	}
	

	
	
	protected void findContentAfterTable(Element table) {
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
	
	
	protected CsnTerminology createNewTerminology(Matcher matcher, CsnTerminologyLanguage lang){
		String section = matcher.group(1);
		String title = matcher.group(3);
		return createNewTerminology(section, title, lang);
	}
	
	protected CsnTerminology createNewTerminology(String section, String title, CsnTerminologyLanguage lang){
		if(StringUtils.isNotBlank(section) && StringUtils.isNotBlank(title)){
			 CsnTerminology newTerminology = new CsnTerminology();
			 newTerminology.setLanguage(lang);
			 newTerminology.setSection(trim(section.trim()));
			 newTerminology.setTitle(removePitPairAndTrim(title));
			 newTerminology.setContent("");
			 return newTerminology;
		}
		return null;
	}
	
	protected String trim(String str){
		str = StringUtils.trimToEmpty(str); //str.trim().replaceAll("	", "");
		return str.replaceAll("^\\p{Z}+", "");
	}
	
	protected String removePitPairAndTrim(String str){
		if(StringUtils.isNotBlank(str)){
			str = trim(str);
			if(str.endsWith(":")){
				return str.substring(0, str.length() - 2);
			}
			return str;
		}
		return "";
	}
	
	protected void appendContent(String content, CsnTerminologyLanguage lang){
		if(StringUtils.isBlank(content)){
			logger.info("nothing to append");
			return ;
		}
		if(lang.equals(CsnTerminologyLanguage.EN) && CollectionUtils.isNotEmpty(englishTerminologies)){
			// appendneme text k predchadzajucej terminologii, ku ktorej patri
			CsnTerminology updateTerminology = englishTerminologies.get(englishTerminologies.size() -1);
			updateTerminology.setContent(updateTerminology.getContent() + content );
		}else if(lang.equals(CsnTerminologyLanguage.CZ) && CollectionUtils.isNotEmpty(czechTerminologies)){
			CsnTerminology updateTerminology = czechTerminologies.get(czechTerminologies.size() -1);
			updateTerminology.setContent(updateTerminology.getContent() + content );
		}
	}
	
	protected void saveTerminology(CsnTerminology terminology){
		Validate.notNull(terminology);
		if(terminology.getLanguage().equals(CsnTerminologyLanguage.CZ)){
			czechTerminologies.add(terminology);
		}else{
			englishTerminologies.add(terminology);
		}
	}
	
	protected String removeEmptyTags(String content){
		return content.replaceAll("(<b>(\\pZ)*</b>)", "")
				.replaceAll("(<p>(\\pZ)*</p>)", "")
				.replaceAll("\\p{Z}", "");
	}

	protected String compareLanguages(TikaProcessingContext context){
		int czSize = czechTerminologies.size();
		int enSize = englishTerminologies.size();

		if(czSize > 0 && enSize > 0 && czSize != enSize){
			
			ArrayList<CsnTerminology> czList = cloneList(czechTerminologies);
			ArrayList<CsnTerminology> enList = cloneList(englishTerminologies);
			
			removeSuccessfully(czList, enList);
			
			for(CsnTerminology t : czList){
				context.logAlert(String.format("Anglický termín k českému termínu: <b>%1$s / %2$s</b> se nepodařilo spracovat", t.getSection(), t.getTitle()));
			}
			
			for(CsnTerminology t : enList){
				context.logAlert(String.format("Český termín k angickému termínu: <b>%1$s / %2$s</b> se nepodařilo spracovat", t.getSection(), t.getTitle()));
			}
		}
		
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	protected ArrayList<CsnTerminology> cloneList(List<CsnTerminology> list){
		return (ArrayList<CsnTerminology>)((ArrayList<CsnTerminology>) list).clone();
	}
	
	protected void removeSuccessfully(ArrayList<CsnTerminology> czList, ArrayList<CsnTerminology> enList){
		Iterator<CsnTerminology> czIterator = czList.iterator();
		while (czIterator.hasNext()) {
			CsnTerminology czTerminology = czIterator.next();
			Iterator<CsnTerminology> enIterator = enList.iterator();
			while(enIterator.hasNext()){
				CsnTerminology enTerminology = enIterator.next();
				if(enTerminology.getSection().equals(czTerminology.getSection())){
					czIterator.remove();
					enIterator.remove();
					break;
				}
			}
		}
	}
}
