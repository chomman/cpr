package sk.peterjurkovic.cpr.parser;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.NodeVisitor;

import sk.peterjurkovic.cpr.dto.CsnTerminologyDto;
import sk.peterjurkovic.cpr.parser.traverse.TerminologyVisitor;

public class NewTerminologyParserImpl implements TerminologyParser {

	Logger logger = Logger.getLogger(getClass());
	
	@Override
	public CsnTerminologyDto parse(String html, TikaProcessContext tikaProcessContext) {
		
		Document doc = Jsoup.parse(html);
		
		Element el = doc.select("tr").first();
		
		int tableColumSize = el.select("td").size();
		
		NodeVisitor terminologyVisitor = new TerminologyVisitor(tableColumSize);
		
		
		doc.traverse(terminologyVisitor);
		
		logger.info("Count of td : " + tableColumSize);
		
		
		
		return null;
	}

}
