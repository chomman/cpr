package sk.peterjurkovic.cpr.parser.cpr;

import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sk.peterjurkovic.cpr.entities.StandardGroup;

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
			
			index++;
		}
		
		
	}
	
	
	
	
}
