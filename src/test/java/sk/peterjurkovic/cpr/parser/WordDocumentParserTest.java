package sk.peterjurkovic.cpr.parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import sk.peterjurkovic.cpr.dao.AbstractTest;

public class WordDocumentParserTest extends AbstractTest {
	
	@Autowired
	private WordDocumentParser wordDocumentParser;

	
	@Test
	public void testParsing(){
		long start = System.currentTimeMillis();
		try{
			TikaProcessContext context = new TikaProcessContext();
			InputStream is =  new FileInputStream("/home/peto/tmp/cpr/csn-3/6927.doc");
			String html = wordDocumentParser.parse(is, context);
			
			
			Assert.assertTrue(html.contains("<h1 class=\"footer\">"));
		
		} catch (IOException e) {

		}catch(MaxUploadSizeExceededException e){

		}
		logger.info(String.format(
	            "------------ Processing took %s millis\n\n",
	            System.currentTimeMillis() - start));
	}
	
	
	
}
