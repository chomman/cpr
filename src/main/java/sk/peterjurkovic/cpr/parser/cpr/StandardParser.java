package sk.peterjurkovic.cpr.parser.cpr;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sk.peterjurkovic.cpr.dto.LinkDto;
import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.entities.StandardChange;
import sk.peterjurkovic.cpr.entities.StandardCsn;

public class StandardParser extends CprParser {
	
	private int counter = 0;
	List<Standard> standards  = new ArrayList<Standard>();
	
	@Override
	public void parse(String location) {
		Document doc = getDocument(location);
		Elements tables = doc.select("table.MsoNormalTable");
		logger.info("count of tables : "  + tables.size());
		if(tables.size() == 2){
			processTable ( tables.get(1) );
		}
	}
	
	@Override
	public void processRow(Elements tds) {
		ListIterator<Element> it =  tds.listIterator();
		counter++;
		int index = 0;
		Standard standard = new Standard();
		while (it.hasNext()) {
			Element td = it.next();
			switch(index){
				case  0:
					parseStandardCodes(standard, td.select("p"));
				break;
				case  1:
					parseStandardCsns(standard, td.select("p"));
				break;
				case 2:
					 parseNames(standard,  td.select("p"));
				break;
			}
			
			index++;
		}
		standards.add(standard);
	}

	private void parseStandardCodes(Standard standard,Elements pList){
		if(pList.size() == 0){
			throw new IllegalArgumentException("Standard ID collum is empty, WTF?");
		}
		if(pList.size() == 1){
			standard.setStandardId(trim(pList.first().text().trim()));
		}else{
			List<String> vals = new ArrayList<String>();
			ListIterator<Element> it =  pList.listIterator();
			while (it.hasNext()) {
				Element pElement = it.next();
				vals.add(trim(pElement.text()));
			}
			
			if(vals.size()  == 2 ){
				standard.setStandardId(vals.get(0));
				StandardChange sch = new StandardChange();
				sch.setChangeCode(vals.get(1));
				standard.getStandardChanges().add(sch);
			}else{
				if(trim(vals.get(1)).equals("nahrazena")){
					standard.setStandardId(vals.get(2));
					standard.setReplacedStandardId(vals.get(0));
					
					if(vals.size() == 4){
						if(vals.get(3).equals("zatím neharmonizována")){
							standard.setText("zatím neharmonizována");
						}
					}
				}else if(trim(vals.get(1)).equals("nahrazuje")){
					standard.setStandardId(vals.get(0));
					standard.setReplacedStandardId(vals.get(2));
				}else{
					if(vals.get(0).equals("EN 13229: 2001")){
						logger.info("EN 13229: 2001");
					}
					standard.setStandardId(vals.get(0));
					for(int i = 1; i < vals.size(); i ++){
						StandardChange sch = new StandardChange();
						sch.setChangeCode(vals.get(i));
						standard.getStandardChanges().add(sch);
					}
				}
			
			}
			
		}
	}
	
	private void parseStandardCsns(Standard standard, Elements pList){
		ListIterator<Element> it =  pList.listIterator();
		List<StandardCsn> csnList = new ArrayList<StandardCsn>();
		
		if(standard.getStandardId().equals("EN 13229: 2001")){
			logger.info("EN 13229: 2001");
		}
		Integer replaceIndex = null;
		while (it.hasNext()) {
			Element pElement = it.next();
			String pText = trim(pElement.text());
			
			if(pText.startsWith("nahrazena") && csnList.size() > 0){
				StandardCsn csn = csnList.get(csnList.size() - 1);
				csn.setCanceled(true);
			}
			
			Elements aList = pElement.select("a");
			if(aList.size() > 1){
				List<LinkDto> links =  processLinks(aList);
				for(LinkDto a : links){
					StandardCsn csn = new StandardCsn();
					csn.setCsnName(a.getAnchorText());
					csn.setCsnOnlineId(parseCatalogNo(a.getHref()));
					csnList.add(csn);
				}
			}else if(aList.size() == 1){
				LinkDto link = extractLink(aList.first());
				if(link == null){
					throw new IllegalArgumentException("CSN link value is empty: " + pElement.text());
				}
				StandardCsn csn = new StandardCsn();
				csn.setCsnName(trim(pText.replace("nahrazena", "")));
				csn.setCsnOnlineId(parseCatalogNo(link.getHref()));
				csnList.add(csn);
				if(replaceIndex != null && csnList.size() - 1 == replaceIndex){
					csnList.get(replaceIndex).setCanceled(true);
					StandardCsn prevCsn = csnList.get(replaceIndex - 1);
					prevCsn.setNote(prevCsn.getNote()+ " "+ pText);
				}
			}else if(aList.size() == 0){ 
				
				if(csnList.size() > 0){
					
					StandardCsn csn = csnList.get(csnList.size()-1);
					
					if(csn.getNote() == null){
						csn.setNote(pText);
					}else{
						csn.setNote(csn.getNote() + " " + pText);
					}
					if(pText.startsWith("Nahrazuje")){
						replaceIndex = csnList.size();
					}
				}
				
			}
		}
		standard.getStandardCsns().addAll(csnList);
	}
	

	
	public void parseNames(Standard standard, Elements pList){
		if(pList.size() != 2){
			throw new IllegalArgumentException("Czech and egnlish name was not found");
		}
		Element p1 =  pList.first();
		Element p2 = p1.nextElementSibling();
		
		if(StringUtils.isBlank(p1.text()) || p2 == null || StringUtils.isBlank(p2.text())){
			throw new IllegalArgumentException("Czech and egnlish name was not found");
		}
		standard.setCzechName(trim(p1.text()));
		standard.setEnglishName(trim(p2.text()));
	}
	
	
	public List<Standard> getStandards() {
		return standards;
	}

	private String parseCatalogNo(String hrefValue){
		if(StringUtils.isBlank(hrefValue)){
			return null;
		}
		String r =  hrefValue.substring(hrefValue.indexOf("?k=") + 3, hrefValue.length());
		return r;
	}
}
