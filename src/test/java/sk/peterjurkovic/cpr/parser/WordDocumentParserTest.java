package sk.peterjurkovic.cpr.parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.jsoup.safety.Whitelist;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import sk.peterjurkovic.cpr.dao.AbstractTest;

public class WordDocumentParserTest extends AbstractTest {
	
	@Autowired
	private WordDocumentParser wordDocumentParser;
	@Autowired
	private TerminologyParserImpl terminologyParserImpl;
	
	
	
	@Test
	public void testParsing(){
		long start = System.currentTimeMillis();
		try{
			TikaProcessContext tikaProcessContext = new TikaProcessContext();
			tikaProcessContext.setCsnId(3l);
			tikaProcessContext.setContextPath("/cpr/");
			InputStream is =  new FileInputStream("/home/peto/Desktop/tn.doc");
			String html = wordDocumentParser.parse(is, tikaProcessContext);
			File f = new File("/home/peto/Desktop/output.html");
			if (!f.exists()) {
				f.createNewFile();
			}
			FileWriter fw = new FileWriter(f.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("<html><head><meta charset=\"utf-8\" /></head><body>"+html+"</body></html>");
			bw.close();
			Assert.assertEquals(true, StringUtils.isNotBlank(html));
			
			terminologyParserImpl.parse(html, tikaProcessContext);
			
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
