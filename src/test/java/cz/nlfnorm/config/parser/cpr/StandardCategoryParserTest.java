package cz.nlfnorm.config.parser.cpr;

import org.junit.Test;

import cz.nlfnorm.parser.cpr.StandardCategoryParser;

public class StandardCategoryParserTest {


	@Test
	public void test(){
		StandardCategoryParser parser = new StandardCategoryParser();
		//parser.parse("http://ec.europa.eu/enterprise/policies/european-standards/harmonised-standards/new-legislative-framework-and-emas/index_en.htm");
		parser.parse("http://ec.europa.eu/enterprise/policies/european-standards/harmonised-standards/low-voltage/index_en.htm");
	}
}
