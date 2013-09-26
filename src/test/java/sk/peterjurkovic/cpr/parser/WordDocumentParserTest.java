package sk.peterjurkovic.cpr.parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
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
	
	
	
	
	@Test
	public void testParsing(){
		long start = System.currentTimeMillis();
		try{
			TikaProcessingContext tikaProcessingContext = new TikaProcessingContext();
			tikaProcessingContext.setCsnId(3l);
			tikaProcessingContext.setContextPath("/cpr/");
			InputStream is =  new FileInputStream("/home/peto/Desktop/n/p61600.doc");
			String html = null;
			try{
				html = wordDocumentParser.parse(is, tikaProcessingContext);
			}catch(Exception e){
				e.printStackTrace();
				return;
			}
			File f = new File("/home/peto/Desktop/output.html");
			if (!f.exists()) {
				f.createNewFile();
			}
			FileWriter fw = new FileWriter(f.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("<html><head><meta charset=\"utf-8\" /><script src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js\"></script></head><body>"+html+"</body></html>");
			bw.close();
			Assert.assertEquals(true, StringUtils.isNotBlank(html));
			NewTerminologyParserImpl terminologyParser =  new NewTerminologyParserImpl();
			terminologyParser.parse(html, tikaProcessingContext);
			
			//logger.info(html);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}catch(MaxUploadSizeExceededException e){
			
		}
		logger.info(String.format(
	            "------------ Processing took %s millis\n\n",
	            System.currentTimeMillis() - start));
	}
	
	
	
}
