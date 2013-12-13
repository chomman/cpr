package sk.peterjurkovic.cpr.parser.cpr;

import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sk.peterjurkovic.cpr.dto.LinkDto;
import sk.peterjurkovic.cpr.entities.Mandate;
import sk.peterjurkovic.cpr.entities.StandardGroup;
import sk.peterjurkovic.cpr.entities.StandardGroupMandate;

public class StandardGroupParser extends CprParser {
	
	private Logger logger = Logger.getLogger(getClass());
	
	public void parse(String location){
		Document doc = getDocument(location);
		Elements tables = doc.select("table.MsoNormalTable");
		processTable ( tables.get(0) );
	}
	
	
	

		
		
		
	public void processRow(Elements tds){
		ListIterator<Element> it =  tds.listIterator();
		StandardGroup standardGroup = new StandardGroup();
		int index = 0;
		while (it.hasNext()) {
			Element td = it.next();
			
			if(index == 0){
				standardGroup.setCode(td.text().trim());
			}
			
			if(index == 1){
				String czechName = td.select("b").first().text();
				String enghlishName = td.select("i").first().text();
				standardGroup.setCzechName(czechName);
				standardGroup.setEnglishName(enghlishName);
				logger.info(czechName + " / " + enghlishName);
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
				standardGroup.setStandardGroupMandates(mandates);
			}
			
			index++;
		}
		
		
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
			sgm.setMandate(m);
			sgm.setComplement(isComplement);
			sgm.setStandardGroup(sg);
			mandates.add(sgm);
		}
		
		return mandates;
	}
	
}
