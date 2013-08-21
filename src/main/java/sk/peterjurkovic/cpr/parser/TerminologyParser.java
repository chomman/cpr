package sk.peterjurkovic.cpr.parser;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import sk.peterjurkovic.cpr.entities.CsnTerminology;
import sk.peterjurkovic.cpr.enums.CsnTerminologyLanguage;

/**
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 * @date Aug 20, 2013
 *
 */
@Component("terminologyParser")
public class TerminologyParser {

	private Logger logger = Logger.getLogger(getClass());
	
	private static final String BASE_XPATH_QUERY = "/table/tbody/tr/td";
	
	private static final String REMOVE_EMPTY_TAGS_REGEX = "<(\\w+)>(\\s|&nbsp;)*<\\/\\1>";
	

	private List<CsnTerminology> czechTerminologies = new ArrayList<CsnTerminology>();
	
	private List<CsnTerminology> englishTerminologies = new ArrayList<CsnTerminology>();
	
	public void parse(String html, TikaProcessContext tikaProcessContext){
		
		
		
		Document doc = convertStringToDocument(html);
    	
    	if(doc != null){
    		logger.info("W3C Document uspesne vytvoreny.");

    		TransformerFactory tf = TransformerFactory.newInstance();
    		Transformer transformer = null;
			try {
				transformer = tf.newTransformer();
			} catch (TransformerConfigurationException e) {
				logger.warn("W3C nepodarilo sa vytvorit instanciu transformera, duvod " + e.getMessage());
				return;
			}
    		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
    		

    		XPath xPath = XPathFactory.newInstance().newXPath();
	    	NodeList tds = null;
			try {
				tds = (NodeList)xPath.evaluate(BASE_XPATH_QUERY , doc.getDocumentElement(), XPathConstants.NODESET);
			} catch (XPathExpressionException e) {
				logger.warn("W3C nepodarilo sa vytvorit xPath dotaz \""+BASE_XPATH_QUERY+"\", duvod: " + e.getMessage());
				return;
			}
	    
	    	
	    	for (int i = 0; i < tds.getLength(); ++i) {
	    		if(i == 0 ||  i % 5 == 0){
	    			// i = 0 > cesky termin
	    			Node czNode = (Node) tds.item(i);
	    			createNewTerminology(czNode, CsnTerminologyLanguage.CZ);
	    			// i + 2 > anglicky termin 
	    			Node enNode = (Node) tds.item(i+2);
	    			createNewTerminology(enNode, CsnTerminologyLanguage.EN);
	    		} 
	    	    
	    	}
	    	
	    	logger.info("W3C Successfuly finished! Count of new EN: " +englishTerminologies.size() + " / CZ: " + czechTerminologies.size());
	    	
	    	int max = czechTerminologies.size();
	    	
	    	if(englishTerminologies.size() > max){
	    		max = englishTerminologies.size();
	    	}
	    	
	    	for(int i = 0 ; i < max; i++){
	    		CsnTerminology cz = czechTerminologies.get(i);
	    		CsnTerminology en = englishTerminologies.get(i);
	    		logger.info(i +" / "+getSection(cz) + " / "+ getSection(en));
	    	}
	    	
	    	
    	}
		
	}
	
	public String getSection(CsnTerminology t){
		if(t != null){
			return t.getSection();
		}
		return " - ";
	}
	
	
	public void createNewTerminology(Node node, CsnTerminologyLanguage language){
		if(isValidNode(node)){
			String content = nodeToString(node);
			
			List<Element> elements = elements(node);
			for(Element e : elements){

				NodeList b = e.getElementsByTagName("b");
				if(b != null && b.getLength() > 0){
					// jedna sa o novy termin, alebo divider
					
					String title = b.item(0).getTextContent();
					
					logger.info("T/D: "+ title);
					
					if(StringUtils.isNotBlank(title) && title.matches("^\\s?\\d{1}\\.\\d{1,2}\\.\\d{1,3}(\\s*)(.|\\s)*$")){
						
						title = title.replaceAll("\\n", " ");
						
						String[] splitedTitle = title.trim().split("(?<=\\d\\.\\d{1,2}\\.\\d{1,3}\\s)");
						if(splitedTitle.length == 2){
							CsnTerminology newTerminology = new CsnTerminology();
							newTerminology.setLanguage(language);
							
							if(StringUtils.isNotBlank(splitedTitle[0])){
								newTerminology.setSection(splitedTitle[0].trim());
								content = content.replace(splitedTitle[0], "");
							}
							
							if(StringUtils.isNotBlank(splitedTitle[1])){
								newTerminology.setTitle(splitedTitle[1].trim());
								content = content.replace(splitedTitle[1], "");
							}
							logger.info("Sekce: " + splitedTitle[0]);
							logger.info("Nazev: " + splitedTitle[1]);
							if(StringUtils.isNotBlank(content)){
								content = cleanEmptyElements(content);
								newTerminology.setContent(content);
								
								if(language.equals(CsnTerminologyLanguage.EN)){
									englishTerminologies.add(newTerminology);
								}else if(language.equals(CsnTerminologyLanguage.CZ)){
									czechTerminologies.add(newTerminology);
								}
							}
						}else{
							logger.warn("W3C titulok sa nepodarilo rozdelit na sekciu a nazov. Hodnota: "+ title);
						}
					}else{
						logger.info("W3C hodnta: " + title + " sa nevyhovuje titulku");
					}
					
				}else{
					// terminologia je rozdelena do viacerych buniek
					if(language.equals(CsnTerminologyLanguage.EN) && CollectionUtils.isNotEmpty(englishTerminologies)){
						// appendneme text k predchadzajucej terminologii, ku ktorej patri
						CsnTerminology updateTerminology = englishTerminologies.get(englishTerminologies.size() -1);
						updateTerminology.setContent(updateTerminology.getContent() + content );
					}
				}
			}
			
		}
	}
	
	public static List<Element> elements(Node parent) {
	    List<Element> result = new LinkedList<Element>();
	    NodeList nl = parent.getChildNodes();
	    for (int i = 0; i < nl.getLength(); ++i) {
	        if (nl.item(i).getNodeType() == Node.ELEMENT_NODE)
	            result.add((Element) nl.item(i));
	    }
	    return result;
	}
	
	private boolean isValidNode(Node node){
		if(node != null && StringUtils.isNotBlank(node.getTextContent())  && node.hasChildNodes()){
			return true;
		}
		return false;
	}
	
	
	private String nodeToString(Node node) {
	    StringWriter sw = new StringWriter();
	    try {
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.transform(new DOMSource(node), new StreamResult(sw));
	    } catch (TransformerException e) {
	       logger.warn("W3C Nepodaril sa konvertovat uzol na String html. Duvod: " + e.getMessage());
	    }
	    return sw.toString();
	  }
	
	
	private Document convertStringToDocument(String html) {
        Validate.notEmpty(html);
		try 
        { 
        	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        	DocumentBuilder db = dbf.newDocumentBuilder();
        	Document doc = db.parse(new InputSource(new StringReader(html) ));
        	doc.getDocumentElement().normalize();
        	return doc;
        } catch (Exception e) {
        	logger.warn("W3C Nepodarilo sa konvertovat String html na Document.");;
        }
        return null;
    }
	
	private String cleanEmptyElements(String nodeContent){
		return nodeContent.replaceAll(REMOVE_EMPTY_TAGS_REGEX, "");
	}
}
