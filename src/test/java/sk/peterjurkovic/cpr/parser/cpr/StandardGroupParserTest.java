package sk.peterjurkovic.cpr.parser.cpr;

import org.junit.Test;

import sk.peterjurkovic.cpr.dao.AbstractTest;

public class StandardGroupParserTest extends AbstractTest {


	@Test
	public void test(){
		StandardGroupParser parser = new StandardGroupParser();
		parser.parse("http://www.sgpstandard.cz/editor/files/unmz/nv190/skupiny.htm");
	}
	
}
