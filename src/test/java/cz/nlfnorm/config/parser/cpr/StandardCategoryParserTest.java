package cz.nlfnorm.config.parser.cpr;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cz.nlfnorm.config.TestConfig;
import cz.nlfnorm.entities.StandardCategory;
import cz.nlfnorm.parser.cpr.StandardCategoryParser;
import cz.nlfnorm.services.StandardCategoryService;
import cz.nlfnorm.services.StandardService;

public class StandardCategoryParserTest extends TestConfig{


	@Autowired
	private StandardService standardService;
	@Autowired
	private StandardCategoryService standardCategoryService;
	
	@Test
	public void test(){
		final List<StandardCategory> categoryList = standardCategoryService.getAll();
		for(final StandardCategory category : categoryList){
			if(!category.isCprCategory() && StringUtils.isNotBlank(category.getNandoUrl())){
				StandardCategoryParser parser = new StandardCategoryParser(standardService, category);
				parser.parse(category.getNandoUrl());
			}
		}
	
	}
}
