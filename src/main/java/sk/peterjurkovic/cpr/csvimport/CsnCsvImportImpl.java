package sk.peterjurkovic.cpr.csvimport;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import au.com.bytecode.opencsv.CSVReader;
import sk.peterjurkovic.cpr.dto.CsvImportDto;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.CsnService;
import sk.peterjurkovic.cpr.utils.UserUtils;


@Component("csnCsvImport")
public class CsnCsvImportImpl implements CsnCsvImport {

	private final static char CSV_SEPARATOR = ';';
	private final static char QUOTE_CHARACTER = '"';
	private final static char SKIP_LINES = 1;
	
	@Autowired
	private CsnService csnService;
	
	private Logger logger = Logger.getLogger(getClass());
	
	
	
	@Override
	public CsvImportDto processImport(InputStream is) {
		long start = System.currentTimeMillis();
		logger.info("ZACIATOK CSV IMPORTU.");
		CsvImportDto infoLog = new CsvImportDto();
		Reader fileReader = new InputStreamReader(is);
		CSVReader csvReader = new CSVReader(fileReader, CSV_SEPARATOR, QUOTE_CHARACTER, SKIP_LINES);
		processReading(csvReader, infoLog);
		long end = (System.currentTimeMillis() - start) / 1000;
		logger.info("KONEC CSV IMPORTU. Import trval: " + end + "s");
		return infoLog;
	}
	
	
	
	
	@Transactional
	private void processReading(CSVReader reader, CsvImportDto into ){
		String [] line;
		User user = UserUtils.getLoggedUser();
		int i = 0;
		try{
			while ((line = reader.readNext()) != null) {
				i++;
			}
		}catch(IOException e){
			logger.warn("Line: "+ i +", pri citani nastala chyba: ", e);
		}
	}

}
