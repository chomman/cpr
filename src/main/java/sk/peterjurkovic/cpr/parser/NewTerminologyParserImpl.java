package sk.peterjurkovic.cpr.parser;

import java.util.ListIterator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sk.peterjurkovic.cpr.dto.CsnTerminologyDto;

public class NewTerminologyParserImpl implements TerminologyParser {

	Logger logger = Logger.getLogger(getClass());
	
	private int counter = 0;
	
	@Override
	public CsnTerminologyDto parse(String html, TikaProcessContext tikaProcessContext) {
		
		Document doc = Jsoup.parse(html);
		Elements tables = doc.select("table");
		
		if(tables.select("table").size() != 0){
			ListIterator<Element> iterator =  tables.listIterator();
			
			while(iterator.hasNext()){
				extractTable(iterator.next());
			}
			
		}
		
		
		
		return null;
	}
	
	public void extractTable(Element table){
		Validate.notNull(table);
		if(table.select("tr").size() != 0){
			
			Element el = table.select("tr").first();
			
			int tableColumSize = el.select("td").size();
						
			logger.info("Count of td : " + tableColumSize);
			
			Elements elements = el.select("td");
			
			ListIterator<Element> iterator =  elements.listIterator();
			int j = 0;
			while(iterator.hasNext()){
				Element td = iterator.next();
				
				if(j == 0 || j == 2 && StringUtils.isNotBlank(td.text())){
					logger.info(td.text());
											
				}
				if(j ==  (tableColumSize - 1)){
					j = 0;
					if(!iterator.hasNext()){
						findPictureAfterTable(table);
					}
				}else{
					j++;
				}
			}
			
		}else{
			logger.info("tr element nebol najdeny");
		}
	}

	private void findPictureAfterTable(Element td) {
		Element nextElement = td.nextElementSibling();
		while(nextElement != null && nextElement.nodeName().equals("p")){
			logger.info("NEXT CONTENT: " +nextElement.html());
			nextElement = nextElement.nextElementSibling();
		}
	}


}
