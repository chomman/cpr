package sk.peterjurkovic.cpr.parser.cpr;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dto.LinkDto;
import sk.peterjurkovic.cpr.dto.StandardDto;
import sk.peterjurkovic.cpr.entities.AssessmentSystem;
import sk.peterjurkovic.cpr.entities.NotifiedBody;
import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.entities.StandardChange;
import sk.peterjurkovic.cpr.entities.StandardCsn;
import sk.peterjurkovic.cpr.entities.StandardCsnChange;
import sk.peterjurkovic.cpr.entities.StandardGroup;
import sk.peterjurkovic.cpr.enums.StandardStatus;
import sk.peterjurkovic.cpr.services.AssessmentSystemService;
import sk.peterjurkovic.cpr.services.NotifiedBodyService;
import sk.peterjurkovic.cpr.services.StandardCsnService;
import sk.peterjurkovic.cpr.services.StandardGroupService;
import sk.peterjurkovic.cpr.services.StandardService;
import sk.peterjurkovic.cpr.utils.CodeUtils;

public class StandardParser extends CprParser {
	
	public static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");	
	
	private static final Pattern CLASSIFICATION_SYMBOL_PATTERN = Pattern.compile(".*\\((\\d+)\\).*");

	private List<Standard> standards  = new ArrayList<Standard>();
	private List<NotifiedBody> notifiedBodies;
	private List<AssessmentSystem> assessmentSystems;
	
	private NotifiedBodyService notifiedBodyService;
	private AssessmentSystemService assessmentSystemService;
	private StandardService standardService;
	private StandardGroupService standardGroupService;
	private StandardCsnService standardCsnService;
	
	@Override
	@Transactional
	public void parse(String location) {
		Document doc = getDocument(location);
		Elements tables = doc.select("table.MsoNormalTable");
		if(notifiedBodyService != null){
			notifiedBodies = notifiedBodyService.getAllNotifiedBodies();
		}
		if(assessmentSystemService != null){
			assessmentSystems = assessmentSystemService.getAllAssessmentSystems();
		}
		if(tables.size() == 2){
			processTable ( tables.get(1) );
		}
	}
	
	@Override
	public void processRow(Elements tds) {
		ListIterator<Element> it =  tds.listIterator();
		int index = 0;
		StandardDto standardDto = new StandardDto();
		while (it.hasNext()) {
			Element td = it.next();
			switch(index){
				case  0:
					parseStandardCodes(standardDto, td.select("p"));
					standardDto.setCurrent(	persist(standardDto.getCurrent()) );
					
				break;
				case  1:
					parseStandardCsns(standardDto.getCurrent(), td.select("p"));
				break;
				case 2:
					parseNames(standardDto.getCurrent(),  td.select("p"));
				break;
				case 3:
					parseDates(standardDto.getCurrent(),  td.select("p"), true);
				break;
				case 4:
					parseDates(standardDto.getCurrent(),  td.select("p"), false);
				break;
				case 5:
					parseNotifiedBodies(standardDto.getCurrent(),  td.select("a"));
				break;
				case 6:
					parseAssesmentsSystems(standardDto.getCurrent(),  td.select("a"));
				break;
				case 7:
					parseStandardGroups(standardDto.getCurrent(), td, it.next());
				break;
			}
			
			index++;
		}
		
		if(standardDto.getReplacedStandard() != null){
			logger.info("Updating canceled standard: " + standardDto.getReplacedStandard().getStandardId());
			standardDto.updateReferences();
			persist(standardDto.getReplacedStandard());
		}
		
		if(standardDto.getCurrent().getStandardId().equals("EN 197-1:2011")){
			logger.info("EN 197-1:2011");
		}
		logger.info("Staving standard: " + standardDto.getCurrent().getStandardId());
		if(standardService != null){
			standardService.saveOrUpdate(standardDto.getCurrent());
		}
		standards.add(standardDto.getCurrent());
	}
	
	private void parseStandardGroups(Standard standard, Element mandateCell, Element commissionDecisionCell){
		if(standardGroupService == null){
			return;
		}
		String[] mandates = removeChanges(mandateCell.text()).split(" ");
		String[] cd = removeChanges(commissionDecisionCell.text()).split(" ");
		if(cd.length == 1){
			if(!findAndAdd(standard, mandates[0], cd[0])){
				logger.info("Standard groups was not found for: " +  mandates[0] + cd[0]);
			}
		}else if(cd.length == 2){
			if(!findAndAdd(standard, mandates[0], cd[0]) && 
			   !findAndAdd(standard, mandates[1], cd[1])){
				logger.info("Standard groups was not found, switching objects");
				if(!findAndAdd(standard, mandates[0], cd[1]) && 
				   !findAndAdd(standard, mandates[1], cd[0])){
					logger.info("Standard groups was not found after switched");
				}else{
					logger.info("Standard groups FOUND.");
				}
			}
		}else{
			logger.warn("CAN NOT DETERMINE standardGroups. " + mandateCell.text() + " / " + commissionDecisionCell.text());
		}
	}
	
	private boolean findAndAdd(Standard standard, String mandate, String commissionDecision){
		StandardGroup sg = standardGroupService.findByMandateAndCommissionDecision(mandate, commissionDecision);
		if(sg == null){
			logStandardGroup(mandate, commissionDecision);
		}else{
			standard.getStandardGroups().add(sg);
			return true;
		}
		return false;
	}
	
	private void logStandardGroup(String td1, String td2){
		logger.warn("StandardGroup was not found: " + td1 + " / " + td2);
	}
	
	private String removeChanges(String val){
		return trim(val.replaceAll("\\((.*)\\)", "").replaceAll(",", ""));
	}
	
	private void parseAssesmentsSystems(Standard standard, Elements aList){
		if(aList.size() > 0 && assessmentSystemService != null){
			List<LinkDto> links =  extractLinks(aList);
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
		if(aList.size() > 0 && notifiedBodyService != null){
			List<LinkDto> links =  extractLinks(aList);
			for(LinkDto l : links){
				String[] codes = l.getAnchorText().split("\\s\\(");
				if(codes.length == 2){
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
				
					//standard.getNotifiedBodies().add(nb);
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

	private void parseStandardCodes(StandardDto standardDto ,Elements pList){
		if(pList.size() == 0){
			throw new IllegalArgumentException("Standard ID collum is empty, WTF?");
		}
		Standard standard = standardDto.getCurrent();
		if(pList.size() == 1){
			standard.setStandardId(trim(clearStandardCode(pList.first().text())));
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
				sch.setChangeCode(clearStandardCode(vals.get(1)));
				standard.getStandardChanges().add(sch);
			}else{
				if(trim(vals.get(1)).contains("nahrazena")){
					// index 2 obsauje kod novej eHN, index 0 stara, nahradena eHN
					createWithCanceled(standardDto, vals.get(2), vals.get(0));
					if(vals.size() == 4){
						if(trim(vals.get(3)).contains("zatím neharmonizována")){
							standard.setStandardStatus(StandardStatus.NON_HARMONIZED);
						}
					}
				}else if(trim(vals.get(1)).equals("nahrazuje")){
					// index 0 obsauje kod novej eHN, index 2 stara, nahradena eHN
					createWithCanceled(standardDto, vals.get(0), vals.get(2));
				}else{
					standard.setStandardId(clearStandardCode(vals.get(0)));
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
	
	private void createWithCanceled(StandardDto standardDto, String newStanadard,String canceledStandard){
		standardDto.getCurrent().setStandardId(clearStandardCode(newStanadard));
		if(standardDto.getCurrent().getId() == null){
			persist(standardDto.getCurrent());
		}
		standardDto.createCopy();
		standardDto.getReplacedStandard().setStandardId(clearStandardCode(canceledStandard));
		standardDto.setReplacedStandard( persist(standardDto.getReplacedStandard()) );
		//standardDto.getCurrent().setReplaceStandard(standardDto.getReplacedStandard());
	}
	
	private Standard persist(Standard s){
		if(standardService != null){
			s.setCode(CodeUtils.toSeoUrl(s.getStandardId()));
			Standard persisted = standardService.getStandardByCode(s.getCode());
			if(persisted == null){
				standardService.saveOrUpdate(s);
				return s;
			}
			return persisted;
		}
		return s;
	}
	
	private String clearStandardCode(String name){
		return name.trim().replaceAll(":\\s+", ":");
	}
	
	
	private StandardCsn createCsn(String name, String href){
		StandardCsn csn = new StandardCsn();
		csn.setCsnName(cleanCsnName(name));
		csn.setCsnOnlineId(parseCatalogNo(href));
		if(StringUtils.isBlank(csn.getCsnOnlineId())){
			logger.warn("INVALI CSN ONLINE ID of CSN: " + name);
		}
		return persist(csn);
		
	}
	
	
	private void createCsnChange(String name, String href, List<StandardCsn> csnList){
		StandardCsnChange csnChange = new StandardCsnChange();
		csnChange.setCsnName(cleanCsnName(name));
		csnChange.setCsnOnlineId(parseCatalogNo(href));
		csnChange.setCreated(new LocalDateTime());
		if(StringUtils.isBlank(csnChange.getCsnOnlineId())){
			logger.warn("INVALI CSN ONLINE ID of CSN: " + name);
		}
		StandardCsn csn = csnList.get(csnList.size() - 1);
		csnChange.setStandardCsn(csn);
		String cs = parseClassificationSymbol(name);
		if(StringUtils.isNotBlank(cs)){
			csn.setClassificationSymbol(cs);
		}
		csn.getStandardCsnChanges().add(csnChange);
		if(standardCsnService != null){
			standardCsnService.saveOrUpdate(csn);
		}
	}
	
	
	private void parseStandardCsns(Standard standard, Elements pList){
		ListIterator<Element> it =  pList.listIterator();
		List<StandardCsn> csnList = new ArrayList<StandardCsn>();
		
		Integer replaceIndex = null;
		while (it.hasNext()) {
			Element pElement = it.next();
			String pText = trim(pElement.text());
			
			if(pText.startsWith("nahrazena") && csnList.size() > 0){
				StandardCsn csn = csnList.get(csnList.size() - 1);
				csn.setStandardStatus(StandardStatus.CANCELED_HARMONIZED);
			}
			Elements aList = pElement.select("a");
			if(aList.size() > 0){
				List<LinkDto> links = extractLinks(aList);
				for(LinkDto link : links){
					if(pText.contains("Oprava") || pText.contains("Změna")){
						createCsnChange((links.size() > 1 ? link.getAnchorText() : pText), link.getHref(), csnList);
					}else{
						csnList.add(createCsn(pText, link.getHref()));
					}
				}
				if(replaceIndex != null && csnList.size() - 1 == replaceIndex){
					csnList.get(replaceIndex).setStandardStatus(StandardStatus.CANCELED_HARMONIZED);
					StandardCsn prevCsn = csnList.get(replaceIndex - 1);
					csnList.get(replaceIndex).setReplaceStandardCsn(prevCsn);
					prevCsn.setNote(prevCsn.getNote()+ " "+ pText);
					prevCsn.setReplaceStandardCsn(csnList.get(replaceIndex));
				}
			}else if(aList.size() == 0){ 
				
				if(csnList.size() > 0){
					
					StandardCsn csn = csnList.get(csnList.size()-1);
					csn.setClassificationSymbol(parseClassificationSymbol(pText));
					if(csn.getNote() == null){
						csn.setNote(pText);
					}else{
						if(!csn.getNote().equals(pText)){
							csn.setNote(csn.getNote() + " " + pText);
						}
					}
					if(pText.startsWith("Nahrazuje")){
						replaceIndex = csnList.size();
					}
				}
				
			}
			
			if(csnList.size() > 1 && 
					csnList.get(csnList.size() - 2).getStandardStatus().equals(StandardStatus.CANCELED_HARMONIZED) && 
					csnList.get(csnList.size() - 2).getReplaceStandardCsn() == null){
				// predchadzajuca CSN je zrusena, ale nema nastavenu CSN, ktora ju nahradzuje
				// preto ju je nutne nastavit
				csnList.get(csnList.size() - 2).setReplaceStandardCsn(csnList.get(csnList.size() - 2));
			}
		}
		standard.getStandardCsns().addAll(csnList);
	}
	
	private String parseClassificationSymbol(String pText){
		Matcher matches =  CLASSIFICATION_SYMBOL_PATTERN.matcher(pText);
		if(matches.find()){
			return matches.group(1);
		}
		return null;
	}
	
	private StandardCsn persist(StandardCsn csn){
		if(standardCsnService != null){
			 StandardCsn persited =	standardCsnService.getByCatalogNo(csn.getCsnOnlineId());
			 if(persited == null){
				 standardCsnService.saveOrUpdate(csn);
				 return csn;
			 }
			 return persited;
		}
		return csn;
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
			if(hrefValue.contains("nahled")){
				int start = hrefValue.lastIndexOf("/");
				int end  = hrefValue.lastIndexOf("_n");
				r = hrefValue.substring(start +1, end );
				if(isCatalogIdValid(r)){
					return r;
				}
			}
			return "";
		}
		return r;
	}
	
	private LocalDate parseDate(String val){
		return FORMATTER.parseLocalDate(val);
	}
	
	
	private NotifiedBody findByNoCode(String code){
		if(CollectionUtils.isEmpty(notifiedBodies )){
			return null;
		}
		for(NotifiedBody nb : notifiedBodies){
			if(nb.getNoCode().equals(trim(code))){
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
		if(CollectionUtils.isEmpty(assessmentSystems)){
			return null;
		}
		for(AssessmentSystem as : assessmentSystems){
			if(as.getAssessmentSystemCode().equals(code)){
				return as;
			}
		}
		return null;
	}


	public void setNotifiedBodyService(NotifiedBodyService notifiedBodyService) {
		this.notifiedBodyService = notifiedBodyService;
	}

	public void setAssessmentSystemService(
			AssessmentSystemService assessmentSystemService) {
		this.assessmentSystemService = assessmentSystemService;
	}

	public void setStandardService(StandardService standardService) {
		this.standardService = standardService;
	}
	

	public void setStandardGroupService(StandardGroupService standardGroupService) {
		this.standardGroupService = standardGroupService;
	}

	
	public void setStandardCsnService(StandardCsnService standardCsnService) {
		this.standardCsnService = standardCsnService;
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
