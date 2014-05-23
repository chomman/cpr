package cz.nlfnorm.parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import cz.nlfnorm.AbstractTestConfig;
import cz.nlfnorm.parser.NoSectionTerminologyParser;
import cz.nlfnorm.parser.SingleSectionTerminologyParser;
import cz.nlfnorm.parser.TerminologyParser;
import cz.nlfnorm.parser.TikaProcessingContext;
import cz.nlfnorm.parser.WordDocumentParser;


public class WordDocumentParserTest extends AbstractTestConfig {
	
	@Autowired
	private WordDocumentParser wordDocumentParser;
	
	private Logger logger = Logger.getLogger(getClass());
	
	
	
	@Test
	public void testParsing(){
		long start = System.currentTimeMillis();
		try{
			TikaProcessingContext tikaProcessingContext = new TikaProcessingContext();
			tikaProcessingContext.setCsnId(100l);
			tikaProcessingContext.setContextPath("/cpr/");
			InputStream is =  new FileInputStream("/home/peto/Desktop/n/v82316.doc");
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
			TerminologyParser terminologyParser = new NoSectionTerminologyParser(); // new NewTerminologyParserImpl();
			terminologyParser.parse(html, tikaProcessingContext);
			((SingleSectionTerminologyParser)terminologyParser).compareLanguages(tikaProcessingContext);
			//logger.info(html);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}catch(MaxUploadSizeExceededException e){
			
		}
		
	}
	
	
	
}
