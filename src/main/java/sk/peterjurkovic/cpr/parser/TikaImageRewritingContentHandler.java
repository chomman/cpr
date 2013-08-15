package sk.peterjurkovic.cpr.parser;

import org.apache.tika.sax.ContentHandlerDecorator;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class TikaImageRewritingContentHandler extends ContentHandlerDecorator {
	 
	private String imageFolder;
    private String imagePrefix;
     
    public TikaImageRewritingContentHandler(ContentHandler handler, String imageFolder, String imagePrefix) {
        super(handler);
        this.imageFolder = imageFolder;
        this.imagePrefix = imagePrefix;
    }

     @Override
     public void startElement(String uri, String localName, String qName,
           Attributes origAttrs) throws SAXException {
        
        if("img".equals(localName)) {
           AttributesImpl attrs;
           if(origAttrs instanceof AttributesImpl) {
              attrs = (AttributesImpl)origAttrs;
           } else {
              attrs = new AttributesImpl(origAttrs);
           }
           
           for(int i=0; i<attrs.getLength(); i++) {
              if("src".equals(attrs.getLocalName(i))) {
                 String src = attrs.getValue(i);
                 if(src.startsWith("embedded:")) {
                    String newSrc = "";
                    if(imageFolder != null)
                       newSrc += imageFolder + "/";
                    if(imagePrefix != null)
                       newSrc += imagePrefix;
                    newSrc += src.substring(src.indexOf(':')+1);
                    attrs.setValue(i, newSrc);
                 }
              }
           }
           super.startElement(uri, localName, qName, attrs);
        } else {
           // For any other tag, pass through as-is
           super.startElement(uri, localName, qName, origAttrs);
        }
     }
}