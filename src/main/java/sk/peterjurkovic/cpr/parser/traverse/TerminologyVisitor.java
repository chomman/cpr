package sk.peterjurkovic.cpr.parser.traverse;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeVisitor;

import sk.peterjurkovic.cpr.enums.CsnTerminologyLanguage;

public class TerminologyVisitor implements NodeVisitor {
	

	private Logger logger = Logger.getLogger(getClass());
	private int tableColumSize = 0;
	private int tdIndex = -1;
	
	private CsnTerminologyLanguage lang;
	
	public TerminologyVisitor(int tableColumSize){
		this.tableColumSize = tableColumSize;
	}
	
	@Override
	public void head(Node node, int depth) {
		if(node.nodeName().equals("td")){		
			if(tdIndex == 0 || tdIndex % tableColumSize == 0){
				lang = CsnTerminologyLanguage.CZ;
			}
			tdIndex++;
		}
		
		if(tdIndex > -1 && node.nodeName().equals("b")){
			logger.info(node.toString() + " / " + tdIndex + "  / " + lang.getCode());
			if(lang.equals(CsnTerminologyLanguage.CZ)){
				lang = CsnTerminologyLanguage.EN;
			}
		}
		
	}

	@Override
	public void tail(Node arg0, int arg1) {
		// TODO Auto-generated method stub

	}

}
