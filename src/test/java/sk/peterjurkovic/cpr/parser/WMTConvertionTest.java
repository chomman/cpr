package sk.peterjurkovic.cpr.parser;

import org.junit.Test;

import sk.peterjurkovic.cpr.image.WMTtoJPGConvertor;
import sk.peterjurkovic.cpr.test.AbstractTest;

public class WMTConvertionTest  extends AbstractTest {
	
	
	@Test
	public void test(){
		WMTtoJPGConvertor convert = new WMTtoJPGConvertor();
		convert.read();
	}
	
}
