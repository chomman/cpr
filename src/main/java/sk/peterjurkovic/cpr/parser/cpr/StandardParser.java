package sk.peterjurkovic.cpr.parser.cpr;

import java.util.ListIterator;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class StandardParser extends CprParser {
	
	private int counter = 0;
	
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
		logger.info("parsing row: " + counter);
		int index = 0;
		while (it.hasNext()) {
			Element td = it.next();
			if(index == 0){
				logger.info(td.text());
			}
			index++;
		}
	}

	

}
