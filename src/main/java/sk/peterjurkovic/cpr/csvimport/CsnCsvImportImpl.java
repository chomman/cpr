package sk.peterjurkovic.cpr.csvimport;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dto.CsvImportLogDto;
import sk.peterjurkovic.cpr.entities.Csn;
import sk.peterjurkovic.cpr.entities.CsnCategory;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.CsnCategoryService;
import sk.peterjurkovic.cpr.services.CsnService;
import sk.peterjurkovic.cpr.utils.CodeUtils;
import sk.peterjurkovic.cpr.utils.UserUtils;
import au.com.bytecode.opencsv.CSVReader;


@Component("csnCsvImport")
public class CsnCsvImportImpl implements CsnCsvImport {

	private final static char CSV_SEPARATOR = ';';
	private final static char QUOTE_CHARACTER = '"';

	private final static String SKIP_KEYWORD = "Katalog";
	
	@Autowired
	private CsnService csnService;
	@Autowired
	private CsnCategoryService csnCategoryService;
	
	private Logger logger = Logger.getLogger(getClass());
	
	
	
	@Override
	public CsvImportLogDto processImport(InputStream is) {
		long start = System.currentTimeMillis();
		logger.info("ZACIATOK CSV IMPORTU.");
		CsvImportLogDto log = new CsvImportLogDto();
		Reader fileReader = new InputStreamReader(is);
		CSVReader csvReader = new CSVReader(fileReader, CSV_SEPARATOR, QUOTE_CHARACTER);
		processReading(csvReader, log);
		long end = (System.currentTimeMillis() - start);
		logger.info("KONEC CSV IMPORTU. Import trval: " + end + "ms, pocet importovanych CSN:" + log.getSuccessCount());
		return log;
	}
	
	
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	private void processReading(CSVReader reader, CsvImportLogDto log ){
		String [] line;
		User user = UserUtils.getLoggedUser();
		int i = 0;
		try{
			while ((line = reader.readNext()) != null) {
				if(line.length == 7){
					if(i == 0 && line[0].equals(SKIP_KEYWORD)){
						continue;
					}
					i++;
					String cleandedCode = CodeUtils.toSeoUrl(line[1]);
					Csn persistedCsn = csnService.getByCode(cleandedCode);
					if(persistedCsn == null){
						createNewCsn(line, log, i, user);
					}else{
						log.appendInfo("Položka s označením: <b>" + line[1] + "</b> se již v databázy nachází, byla přeskočena.", i);
						log.incrementFailure();
					}
				}
				
			}
		}catch(IOException e){
			logger.warn("Line: "+ i +", pri citani nastala chyba: ", e);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	private void createNewCsn(String[] line, CsvImportLogDto log, int i, User user){
		Csn csn = new Csn();
		// 0 - katalogove cislo
		csn.setCsnOnlineId(line[0]);
		// 1 - oznaceni csn
		csn.setCsnId(line[1]);
		csn.setCode(CodeUtils.toSeoUrl(line[1]));
		// 2 - oznaceni zmeny
		csn.setChangeLabel(line[2]);
		// 3 -nazev
		csn.setCzechName(line[3]);
		// 4 -tridici znak
		csn.setClassificationSymbol(line[4]);
		
		CsnCategory category = findCsnCategory(line[4]);
		if(category != null){
			csn.setCategorySearchCode(category.getSearchCode());
			csn.setCsnCategory(category);
		}else{
			log.appendInfo("Pro položku s označením: <b>" + line[1] + "</b> neni v DB evidovaný žádný odbor, třídicí znak: " + line[4], i);
		}
		csn.setCreatedBy(user);
		csn.setCreated(new LocalDateTime());
		csn.setBulletin(line[6]);
		csnService.createCsn(csn);
		log.incrementSuccess();
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	private CsnCategory findCsnCategory(String classificationSymbol){
		CsnCategory category = null;
		if(StringUtils.isNotBlank(classificationSymbol) && classificationSymbol.length() > 3){
			String searchCode =  classificationSymbol.substring(0, 4);
			category = csnCategoryService.findBySearchCode(searchCode);
		}
		return category;
	}
	

}
