package sk.peterjurkovic.cpr.parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import sk.peterjurkovic.cpr.dao.AbstractTest;

public class WordDocumentParserTest extends AbstractTest {
	
	@Autowired
	private WordDocumentParser wordDocumentParser;
	@Autowired
	private TerminologyParser terminologyParser;
	
	@Test
	public void testParsing(){
		long start = System.currentTimeMillis();
		try{
			TikaProcessContext tikaProcessContext = new TikaProcessContext();
			tikaProcessContext.setCsnId(3l);
			tikaProcessContext.setContextPath("/cpr/");
			InputStream is =  new FileInputStream("/home/peto/Desktop/tn.doc");
			String html = wordDocumentParser.parse(is, tikaProcessContext);
			
			Assert.assertEquals(true, StringUtils.isNotBlank(html));
			
			terminologyParser.parse(html, tikaProcessContext);
			
			//logger.info(html);
		} catch (IOException e) {

		}catch(MaxUploadSizeExceededException e){

		}
		logger.info(String.format(
	            "------------ Processing took %s millis\n\n",
	            System.currentTimeMillis() - start));
	}
	
	
	
}
