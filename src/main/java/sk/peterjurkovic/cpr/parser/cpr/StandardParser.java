package sk.peterjurkovic.cpr.parser.cpr;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sk.peterjurkovic.cpr.dto.LinkDto;
import sk.peterjurkovic.cpr.entities.AssessmentSystem;
import sk.peterjurkovic.cpr.entities.NotifiedBody;
import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.entities.StandardChange;
import sk.peterjurkovic.cpr.entities.StandardCsn;
import sk.peterjurkovic.cpr.services.AssessmentSystemService;
import sk.peterjurkovic.cpr.services.NotifiedBodyService;
import sk.peterjurkovic.cpr.services.StandardService;

public class StandardParser extends CprParser {
	
	public static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");	

	private List<Standard> standards  = new ArrayList<Standard>();
	private List<NotifiedBody> notifiedBodies;
	private List<AssessmentSystem> assessmentSystems;
	
	private NotifiedBodyService notifiedBodyService;
	private AssessmentSystemService assessmentSystemService;
	private StandardService standardService;
	
	@Override
	public void parse(String location) {
		Document doc = getDocument(location);
		Elements tables = doc.select("table.MsoNormalTable");
		logger.info("count of tables : "  + tables.size());
		notifiedBodies = notifiedBodyService.getAllNotifiedBodies();
		assessmentSystems = assessmentSystemService.getAllAssessmentSystems();
		if(tables.size() == 2){
			processTable ( tables.get(1) );
		}
	}
	
	@Override
	public void processRow(Elements tds) {
		ListIterator<Element> it =  tds.listIterator();
		int index = 0;
		Standard standard = new Standard();
		while (it.hasNext()) {
			Element td = it.next();
			switch(index){
				case  0:
					parseStandardCodes(standard, td.select("p"));
					standardService.saveOrUpdate(standard);
				break;
				case  1:
					parseStandardCsns(standard, td.select("p"));
				break;
				case 2:
					 parseNames(standard,  td.select("p"));
				break;
				case 3:
					parseDates(standard,  td.select("p"), true);
				break;
				case 4:
					parseDates(standard,  td.select("p"), false);
				break;
				case 5:
					parseNotifiedBodies(standard,  td.select("a"));
				break;
				case 6:
					parseAssesmentsSystems(standard,  td.select("a"));
				break;
				case 7:
					Element td2 = it.next();
					String[] mandates = removeChanges(td.text()).split(" ");
					String[] cd = removeChanges(td2.text()).split(" ");
					if(mandates.length == 1 && cd.length == 1){
						
					}else if(mandates.length == 2 && cd.length == 2){
						
					}else{
						logger.warn("CAN NOT DETERMINE standardGroups. " + td.text() + " / " + td2.text());
					}
					
					
					
				break;
			}
			
			index++;
		}
		logger.info("Staving standard: " + standard.getStandardId());
		standardService.saveOrUpdate(standard);
		//standards.add(standard);
	}
	
	private String removeChanges(String val){
		return trim(val.replaceAll("\\((.*)\\)", ""));
	}
	
	private void parseAssesmentsSystems(Standard standard, Elements aList){
		if(aList.size() > 0){
			List<LinkDto> links =  processLinks(aList);
			for(LinkDto l : links){
				AssessmentSystem as = findAsByNoCode(l.getAnchorText());
				if(as == null){
					throw new IllegalArgumentException("Assessment systems body with code " + l.getAnchorText() + " was not found");
				}
				standard.getAssessmentSystems().add(as);
			}
		}
	}
	
	private void parseNotifiedBodies(Standard standard, Elements aList){
		if(aList.size() > 0){
			List<LinkDto> links =  processLinks(aList);
			for(LinkDto l : links){
				String[] codes = l.getAnchorText().split("\\s\\(");
				if(codes.length == 0){
					final String noCode = StringUtils.trim(codes[0]);
					final String aoCode = StringUtils.trim(codes[1].replace(")", ""));
					NotifiedBody nb = findByNoCode(noCode);
					if(nb == null){
						throw new IllegalArgumentException("Notified body with code " + noCode + " was not found");
					}
					if(StringUtils.isBlank(nb.getAoCode())){
						nb.setAoCode(aoCode);
						nb.setNandoCode(getNandoCode(l.getHref()));
						notifiedBodyService.updateNotifiedBody(nb);
					}
					standard.getNotifiedBodies().add(nb);
				}
			}
			
		}
	}
	
	
	private void parseDates(Standard standard, Elements pList, boolean isStartValidity){
		ListIterator<Element> it =  pList.listIterator();
		int index = 0;
		while (it.hasNext()) {
			Element pElement = it.next();
			String val = trim(pElement.text()); 
			if(StringUtils.isNotBlank(removeWhiteSpaces(val))){
				LocalDate date = parseDate(val);
				if(index == 0){
					if(isStartValidity){
						standard.setStartValidity(date);
					}else{
						standard.setStopValidity(date);
					}
					
				}else{
					if(standard.getStandardChanges().size() == 0){
						throw new IllegalArgumentException("Standard change does not exists, but date " + val + " yes");
					}
					Set<StandardChange> changes = standard.getStandardChanges();
					int j = 0;
					for(StandardChange sch : changes){
						if(index - 1 == j){
							if(isStartValidity){
								sch.setStartValidity(date);
							}else{
								sch.setStopValidity(date);
							}
						}
						j++;
					}
					
				}
				
				
				index++;
			}
		}
	}

	private void parseStandardCodes(Standard standard,Elements pList){
		if(pList.size() == 0){
			throw new IllegalArgumentException("Standard ID collum is empty, WTF?");
		}
		if(pList.size() == 1){
			standard.setStandardId(trim(pList.first().text().trim().replace(": ", ":")));
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
				sch.setStandard(standard);
				sch.setChangeCode(vals.get(1));
				standard.getStandardChanges().add(sch);
			}else{
				if(trim(vals.get(1)).startsWith("nahrazena")){
					standard.setStandardId(vals.get(2));
					standard.setReplacedStandardId(vals.get(0));
					
					if(vals.size() == 4){
						if(trim(vals.get(3)).startsWith("zatím neharmonizována")){
							standard.setText("zatím neharmonizována");
						}
					}
				}else if(trim(vals.get(1)).equals("nahrazuje")){
					standard.setStandardId(vals.get(0));
					standard.setReplacedStandardId(vals.get(2));
				}else{
					standard.setStandardId(vals.get(0));
					for(int i = 1; i < vals.size(); i ++){
						StandardChange sch = new StandardChange();
						sch.setChangeCode(removeWhiteSpaces(vals.get(i).replace("/", "")));
						sch.setStandard(standard);
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
					csn.setCsnName(cleanCsnName(a.getAnchorText()));
					csn.setCsnOnlineId(parseCatalogNo(a.getHref()));
					String note = getExtractNote(a.getAnchorText());
					if(note != null){
						csn.setNote(note);
					}
					if(StringUtils.isBlank(csn.getCsnOnlineId())){
						logger.warn("INVALI CSN ONLINE ID Standard: " + standard.getStandardId());
					}
					csnList.add(csn);
				}
			}else if(aList.size() == 1){
				LinkDto link = extractLink(aList.first());
				if(link == null){
					throw new IllegalArgumentException("CSN link value is empty: " + pElement.text());
				}
				StandardCsn csn = new StandardCsn();
				csn.setCsnName(cleanCsnName(pText));
				csn.setCsnOnlineId(parseCatalogNo(link.getHref()));
				if(StringUtils.isBlank(csn.getCsnOnlineId())){
					logger.warn("INVALI CSN ONLINE ID Standard: " + standard.getStandardId());
				}
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
			return "";
		}
		String r =  hrefValue.substring(hrefValue.indexOf("?k=") + 3, hrefValue.length());
		if(!isCatalogIdValid(r)){
			return "";
		}
		return r;
	}
	
	private LocalDate parseDate(String val){
		return FORMATTER.parseLocalDate(val);
	}
	
	
	private NotifiedBody findByNoCode(String code){
		for(NotifiedBody nb : notifiedBodies){
			if(nb.getNoCode().equals(code)){
				return nb;
			}
		}
		return null;
	}
	
	private String getNandoCode(String hrefVal){
		if(StringUtils.isNotBlank(hrefVal)){
			return hrefVal.substring(hrefVal.indexOf("refe_cd=") + 8, hrefVal.length());
		}
		return null;
	}
	
	
	private AssessmentSystem findAsByNoCode(String code){
		for(AssessmentSystem as : assessmentSystems){
			if(as.getAssessmentSystemCode().equals(code)){
				return as;
			}
		}
		return null;
	}

	public NotifiedBodyService getNotifiedBodyService() {
		return notifiedBodyService;
	}

	public void setNotifiedBodyService(NotifiedBodyService notifiedBodyService) {
		this.notifiedBodyService = notifiedBodyService;
	}

	public AssessmentSystemService getAssessmentSystemService() {
		return assessmentSystemService;
	}

	public void setAssessmentSystemService(
			AssessmentSystemService assessmentSystemService) {
		this.assessmentSystemService = assessmentSystemService;
	}

	public StandardService getStandardService() {
		return standardService;
	}

	public void setStandardService(StandardService standardService) {
		this.standardService = standardService;
	}
	
	
	public boolean isCatalogIdValid(String val){
		if(StringUtils.isBlank(val)){
			return true;
		}
		return val.matches("(^[0-9]{1,10}$|)*");
	}
	
	private String cleanCsnName(String name){
		name = name.replace("nahrazena", "");
		name = name.replace("Nahrazena", "");
		name = name.replaceAll("\\(\\d{6}\\)", "");
		name = trim(name);
		return name;
	}
	
	private String getExtractNote(String csnName){
		if(csnName.indexOf("(") != -1 && csnName.indexOf(")") != -1){
			return trim(csnName.substring(csnName.indexOf("("), csnName.length()));
		}
		return null;
	}
}
