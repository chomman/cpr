package sk.peterjurkovic.cpr.parser.cpr;

import java.io.IOException;
import java.util.ListIterator;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
	
	
	public abstract void processRow(Elements tds);
	
	public abstract void parse(String location);
	
}
