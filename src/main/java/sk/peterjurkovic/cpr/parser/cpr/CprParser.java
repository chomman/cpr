package sk.peterjurkovic.cpr.parser.cpr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sk.peterjurkovic.cpr.dto.LinkDto;

public abstract class CprParser {
	
	protected boolean skipFirstRow = true;
	
	public Document getDocument(final String location){
		if(StringUtils.isNotBlank(location)){
			try {
				return Jsoup.connect(location).get();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	public void processTable(Element table ){
		if(table != null){
			Elements trs = table.select("tr");
			
			if(trs.size() == 0){
				return ;
			}
			
			ListIterator<Element> trIterator =  trs.listIterator();
			int i=0;
			while(trIterator.hasNext()){
				i++;
				Element tr = trIterator.next();
				if(skipFirstRow && i == 1){
					continue;
				}
				processRow( tr.select("td"));
				
			}
		}
	}
	
	public LinkDto extractLink(Element a){
		if(a != null){
			LinkDto data = new LinkDto();
			data.setHref( a.attr("href") );
			data.setAnchorText(a.text().trim()); 
			return data;
		}
		return null;		
	}
	
	public List<LinkDto> processLinks(Elements links){
		Validate.notNull(links);
		List<LinkDto> linkList = new ArrayList<LinkDto>(); 
		ListIterator<Element> tdIterator =  links.listIterator();
		while (tdIterator.hasNext()) {
			LinkDto data = extractLink(tdIterator.next());
			if(data != null){
				linkList.add(data);
			}
		}
		return linkList;
	}
	
	
	public abstract void processRow(Elements tds);
	
	public abstract void parse(String location);
	
}
