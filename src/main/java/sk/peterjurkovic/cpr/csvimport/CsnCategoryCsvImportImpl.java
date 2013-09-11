package sk.peterjurkovic.cpr.csvimport;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.entities.CsnCategory;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.CsnCategoryService;
import sk.peterjurkovic.cpr.utils.UserUtils;
import au.com.bytecode.opencsv.CSVReader;

@Component("csnCategoryCsvImport")
public class CsnCategoryCsvImportImpl implements CsnCategoryCsvImport{

	private final static char CSV_SEPARATOR = ';';
	
	@Autowired
	private CsnCategoryService csnCategoryService;
	
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Override
	public int processImport(InputStream is) {
		logger.info("Import Starting..");
		Reader fileReader = new InputStreamReader(is);
		return processReadFile( new CSVReader(fileReader, CSV_SEPARATOR) );
		
	}
	
	@Transactional
	private int processReadFile(CSVReader reader){
		String [] line;
		User user = UserUtils.getLoggedUser();
		CsnCategory parentCategory = null;
		
		int coutner = 0;
		
		try {
			while ((line = reader.readNext()) != null) {
			    
				if(line.length == 2){
			    	
					String[] codes = line[0].split(" ");
					
					if(codes.length == 2){
						String parentSearchCode = codes[0].trim();

						
						if(parentCategory == null || !parentCategory.getSearchCode().equals(parentSearchCode)){
							parentCategory = csnCategoryService.findBySearchCode(parentSearchCode);
						}
						if(parentCategory != null){
							String subId = codes[1].trim();
							String searchCode = parentSearchCode + subId;
							CsnCategory category = csnCategoryService.findBySearchCode(searchCode);
							if(category == null){
								category = cerateCategory(line[1].trim(), user);
								category.setSearchCode(searchCode);
								category.setCode(subId);
								category.setParent(parentCategory);
								csnCategoryService.createCategory(category);
								coutner++;
							}
						}
					}else{
						CsnCategory category = csnCategoryService.findBySearchCode(line[0].trim());
				    	if(category == null){
				    		category = cerateCategory(line[1].trim(), user);
				    		category.setCode(line[0].trim());
				    		category.setSearchCode(line[0].trim());
				    		csnCategoryService.createCategory(category);
				    		coutner++;
				    	}
					}
			    }
			}
			logger.info("IMPORT CSV uspesne dokonceny, importovany pocet kategorii: " + coutner);
		} catch (IOException e) {
			logger.warn("Import csv souboru zlyhal. Poradilo ca importovat pocet kategorii: " + coutner + " dovod: " , e);
		}
		return coutner;
	}
	
	private CsnCategory cerateCategory(String name, User user){
		CsnCategory category = new CsnCategory();
		category.setChanged(new DateTime());
		category.setCreated(new DateTime());
		category.setCreatedBy(user);
		category.setName(name);
		return category;
	}

}
