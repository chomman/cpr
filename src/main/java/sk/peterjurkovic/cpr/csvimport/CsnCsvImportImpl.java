package sk.peterjurkovic.cpr.csvimport;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sk.peterjurkovic.cpr.dto.CsvImportLogDto;
import sk.peterjurkovic.cpr.entities.Csn;
import sk.peterjurkovic.cpr.services.CsnService;
import sk.peterjurkovic.cpr.utils.CodeUtils;
import au.com.bytecode.opencsv.CSVReader;


@Component("csnCsvImport")
public class CsnCsvImportImpl implements CsnCsvImport {

	private final static char CSV_SEPARATOR = ';';
	private final static char QUOTE_CHARACTER = '"';

	private final static String SKIP_KEYWORD = "Katalog";
	
	@Autowired
	private CsnService csnService;
	
	
	private Logger logger = Logger.getLogger(getClass());
	
	
	
	@Override
	public CsvImportLogDto processImport(InputStream is) {
		long start = System.currentTimeMillis();
		logger.info("ZACIATOK CSV IMPORTU.");
		Reader fileReader = new InputStreamReader(is);
		CSVReader csvReader = new CSVReader(fileReader, CSV_SEPARATOR, QUOTE_CHARACTER);
		List<Csn> csnList =  processReading(csvReader);
		CsvImportLogDto log =  csnService.saveList(csnList);
		long end = (System.currentTimeMillis() - start);
		logger.info("KONEC CSV IMPORTU. Import trval: " + end + "ms, pocet importovanych CSN:" + log.getSuccessCount());
		return log;
	}
	
	
	
	private List<Csn> processReading(CSVReader reader){
		List<Csn> csnList = new ArrayList<Csn>();
		String [] line;
		int i = 0;
		try{
			while ((line = reader.readNext()) != null) {
				if(line.length == 7){
					if(i == 0 && line[0].equals(SKIP_KEYWORD)){
						continue;
					}
					i++;
					Csn csn = createNewCsn(line);
					if(csn != null){
						csnList.add(csn);
					}
				}
				
			}
		}catch(IOException e){
			logger.warn("Line: "+ i +", pri citani nastala chyba: ", e);
		}
		return csnList;
	}
	

	private Csn createNewCsn(String[] line){
		Csn csn = new Csn();
		// 0 - katalogove cislo
		csn.setCatalogId(line[0]);
		// 1 - oznaceni csn
		csn.setCsnId(line[1]);
		csn.setCode(CodeUtils.toSeoUrl(line[1]));
		// 2 - oznaceni zmeny
		csn.setChangeLabel(line[2]);
		// 3 -nazev
		csn.setCzechName(line[3]);
		// 4 -tridici znak
		csn.setClassificationSymbol(line[4]);
		csn.setCreated(new LocalDateTime());
		csn.setBulletin(line[6]);
		return csn;
	}
	

}
