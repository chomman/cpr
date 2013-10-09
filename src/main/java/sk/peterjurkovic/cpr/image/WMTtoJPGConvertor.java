package sk.peterjurkovic.cpr.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.wmf.tosvg.WMFHeaderProperties;
import org.apache.batik.transcoder.wmf.tosvg.WMFTranscoder;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import sk.peterjurkovic.cpr.services.FileService;

public class WMTtoJPGConvertor {
	 
	JPEGTranscoder trans = new JPEGTranscoder();
	@Autowired
	private FileService fileService;
	
	public void read(){
		File file = new File("/home/peto/tmp/cpr/csn-100/" );
		if(file.exists() && file.isDirectory()){
			for(File f : file.listFiles()){
				if(FilenameUtils.isExtension(f.getName(), "wmf")){
					convert(f.getName());
				}
			}
		}
	}
	
	public void convert(String filename){
		String inputFile = "/home/peto/tmp/cpr/csn-100/" + filename;
		String outputFile = "/home/peto/tmp/cpr/csn-100/" + StringUtils.replace(filename, "wmf", "svg");
		  
		try {
			File wmfFile = new File(inputFile);
			FileInputStream fis = new FileInputStream(wmfFile);
	        TranscoderInput input = new TranscoderInput(fis);
	        OutputStream stream = new FileOutputStream(outputFile);
	        TranscoderOutput output = new TranscoderOutput(stream);
	        WMFTranscoder transcoder = new WMFTranscoder();
	        transcoder.addTranscodingHint(WMFTranscoder.KEY_WIDTH, new Float(150));
	        transcoder.transcode(input,output);
		 
		} catch (Exception e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
