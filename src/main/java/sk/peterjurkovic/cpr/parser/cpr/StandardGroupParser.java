package sk.peterjurkovic.cpr.parser.cpr;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import sk.peterjurkovic.cpr.dto.LinkDto;
import sk.peterjurkovic.cpr.entities.CommissionDecision;
import sk.peterjurkovic.cpr.entities.Mandate;
import sk.peterjurkovic.cpr.entities.StandardGroup;
import sk.peterjurkovic.cpr.entities.StandardGroupMandate;
import sk.peterjurkovic.cpr.services.CommissionDecisionService;
import sk.peterjurkovic.cpr.services.MandateService;
import sk.peterjurkovic.cpr.services.StandardGroupMandateService;
import sk.peterjurkovic.cpr.services.StandardGroupService;

public class StandardGroupParser extends CprParser {
	
	@Autowired
	private StandardGroupService standardGroupService;
	@Autowired
	private MandateService mandateService;
	@Autowired
	private CommissionDecisionService commissionDecisionService;
	@Autowired
	private StandardGroupMandateService standardGroupMandateService;
	
	private Logger logger = Logger.getLogger(getClass());
	
	private Set<CommissionDecision> commissionList = new HashSet<CommissionDecision>();
	private Set<Mandate> mandateList = new HashSet<Mandate>();
	private List<StandardGroup> standardGroups = new ArrayList<StandardGroup>();
	
	public void parse(String location){
		Document doc = getDocument(location);
		Elements tables = doc.select("table.MsoNormalTable");
		processTable ( tables.get(0) );
	}
			
		
	public void processRow(Elements tds){
		
		ListIterator<Element> it =  tds.listIterator();
		
		StandardGroup standardGroup = new StandardGroup();
		CommissionDecision cd = new CommissionDecision();
		cd.setChanged(new LocalDateTime());
		cd.setCreated(new LocalDateTime());
		
		int index = 0;
		while (it.hasNext()) {
			Element td = it.next();
			
			if(index == 0){
				String code = td.text().replaceAll("\\.", "");
				code = code.replaceAll("\\p{Z}", "");
				code = code.trim();
				standardGroup.setCode(code);
			}
			
			if(index == 1){
				String czechName = td.select("b").first().text();
				String enghlishName = td.select("i").first().text();
				standardGroup.setCzechName(removeBrackets(czechName));
				standardGroup.setEnglishName(removeBrackets(enghlishName));
				if(standardGroupService != null){
					standardGroupService.saveOrdUpdateStandardGroup(standardGroup);
				}
			}
			
			if(index == 2){
				Elements links =  td.select("a");
				List<LinkDto> mandateLinks =  processLinks(links);
				Set<StandardGroupMandate> mandates = createMandates(mandateLinks, false, standardGroup);
				standardGroup.setStandardGroupMandates(mandates);
			}
			
			if(index == 3 ){
				Elements links =  td.select("a");
				List<LinkDto> mandateLinks =  processLinks(links);
				Set<StandardGroupMandate> mandates = createMandates(mandateLinks, true, standardGroup);
				standardGroup.getStandardGroupMandates().addAll(mandates);
			}
			
			
			
			if(index == 4 ){
				Element link =  td.select("a").first();
				LinkDto a = extractLink(link);
				if(a != null){
					cd.setCzechFileUrl(a.getHref());
					cd.setCzechLabel(clean(a.getAnchorText()));
				}
			}
			
			if(index == 5 ){
				Elements els =  td.select("a");
				List<LinkDto> links =  processLinks(els);
				int j = 0;
				for(LinkDto a : links){
					if(j == 0){
						cd.setEnglishLabel(clean(a.getAnchorText()));
						cd.setEnglishFileUrl(a.getHref());
					}else{
						cd.setDraftAmendmentLabel(clean(a.getAnchorText()));
						cd.setDraftAmendmentUrl(a.getHref());
					}
					j++;
				}
			}
			
			index++;
		}
		if(cd.getCzechLabel() != null || cd.getEnglishLabel() != null ){
			cd = insert(cd);
			standardGroup.setCommissionDecision(cd);
		}
		if(standardGroupService != null){
			standardGroupService.mergeStandardGroup(standardGroup);
		}
		
		if(!standardGroups.add(standardGroup)){
			logger.info("ERROR: Group was not aded " + standardGroup.getCode());
		}else{
			logger.info("GROUP CREATED: " + standardGroup.getCode());
		}
	}
	
	
	private String clean(String val){
		val = val.replace("(konsolidované znění)", "");
		val = val.replace("(konsolidované znění", "");
		return StringUtils.trim(val);
	}
	
	private Set<StandardGroupMandate> createMandates(List<LinkDto> links, boolean isComplement, StandardGroup sg){
		Validate.notNull(links);
		Set<StandardGroupMandate> mandates = new HashSet<StandardGroupMandate>();
		for(LinkDto link : links){
			StandardGroupMandate sgm = new StandardGroupMandate();
			Mandate m = new Mandate();
			m.setChanged(new LocalDateTime());
			m.setCreated(new LocalDateTime());
			m.setEnabled(true);
			m.setMandateFileUrl(link.getHref());
			m.setMandateName(link.getAnchorText());
			m = insert(m);
			sgm.setMandate(m);
			sgm.setComplement(isComplement);
			sgm.setStandardGroup(sg);
			if(standardGroupMandateService != null){
				standardGroupMandateService.create(sgm);
			}
			mandates.add(sgm);
		}
		return mandates;
	}
	
	private String removeBrackets(String val){
		if(StringUtils.isNotBlank(val)){
			val = val.trim();
			if(val.charAt(0) == '('){
				val = val.substring(1, val.length()-1);
			}
			if(val.charAt(val.length() - 1) == ')'){
				val = val.substring(1, val.length() - 2);
			}
		}
		return val;
	}
	
	
	private Mandate insert(Mandate m){
		for(Mandate savedMandate : mandateList){
			if(savedMandate.getMandateName().equals(m.getMandateName())){
				return savedMandate;
			}
		}
		if(mandateService != null){
			mandateService.saveOrUpdateMandate(m);
		}
		mandateList.add(m);
		return m;
	}
	
	private CommissionDecision insert(CommissionDecision cd){
		for(CommissionDecision saveDC : commissionList){
			if(saveDC.getCzechLabel().equals(cd.getCzechLabel()) ){
				logger.info("Commision decision already exists: " + cd.getCzechLabel());
				return saveDC;
			}
		}
		logger.info(cd);
		if(commissionDecisionService != null){
			commissionDecisionService.saveOrUpdate(cd);
		}
		commissionList.add(cd);
		return cd;
	}


	public List<StandardGroup> getStandardGroups() {
		return standardGroups;
	}


	public void setStandardGroupService(StandardGroupService standardGroupService) {
		this.standardGroupService = standardGroupService;
	}


	public void setMandateService(MandateService mandateService) {
		this.mandateService = mandateService;
	}


	public void setCommissionDecisionService(
			CommissionDecisionService commissionDecisionService) {
		this.commissionDecisionService = commissionDecisionService;
	}


	public void setStandardGroupMandateService(
			StandardGroupMandateService standardGroupMandateService) {
		this.standardGroupMandateService = standardGroupMandateService;
	}


	public void setLogger(Logger logger) {
		this.logger = logger;
	}


	public void setCommissionList(Set<CommissionDecision> commissionList) {
		this.commissionList = commissionList;
	}


	public void setMandateList(Set<Mandate> mandateList) {
		this.mandateList = mandateList;
	}


	
	
	
}
