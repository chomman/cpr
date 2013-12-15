package sk.peterjurkovic.cpr.parser.cpr;

import org.junit.Test;

import sk.peterjurkovic.cpr.dao.AbstractTest;

public class StandardParserTest extends AbstractTest{
	
	@Test
	public void test(){
		StandardParser parser = new StandardParser();
		parser.parse("http://www.sgpstandard.cz/editor/files/unmz/nv190/nv_190.htm");
	}
}
