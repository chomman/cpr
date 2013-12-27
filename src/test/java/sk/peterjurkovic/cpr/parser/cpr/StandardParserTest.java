package sk.peterjurkovic.cpr.parser.cpr;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import sk.peterjurkovic.cpr.dao.AbstractTest;
import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.entities.StandardChange;
import sk.peterjurkovic.cpr.entities.StandardCsn;

@Resource
public class StandardParserTest extends AbstractTest{
	
	@Test
	public void test(){
		StandardParser parser = new StandardParser();
		parser.parse("http://www.sgpstandard.cz/editor/files/unmz/nv190/nv_190.htm");
		List<Standard> list = parser.getStandards();
		
		Assert.assertNotNull(find("EN 1096-4: 2004", list));
		Standard s = find("EN 54-2: 1997", list);
		Assert.assertNotNull(find("A1: 2006", s));
		
		Assert.assertEquals("EN 295-10: 2005", find("EN 295-4:2013", list).getReplacedStandardId());
		
		Assert.assertEquals("EN 450-1+A1: 2007", find("EN 450-1:2012", list).getReplacedStandardId());
		
		Assert.assertEquals("EN 845-2: 2003", find("EN 845-2:2013", list).getReplacedStandardId());
		s = find("EN 13229: 2001", list);
		Assert.assertEquals(4, s.getStandardChanges().size());
		Assert.assertEquals(7, s.getStandardCsns().size());
		Assert.assertNotNull(find("Oprava 1-1.04", s.getStandardCsns()));
		Assert.assertEquals("2005-07-01", s.getStartValidity().toString(StandardParser.FORMATTER));
		Assert.assertEquals("2007-07-01", s.getStopValidity().toString(StandardParser.FORMATTER));
		int i = 0;
		for(StandardChange ch : s.getStandardChanges()){
			switch(i){
			case 0 :
				Assert.assertEquals("2006-06-01", ch.getStartValidity().toString(StandardParser.FORMATTER));
				Assert.assertEquals("2007-06-01", ch.getStopValidity().toString(StandardParser.FORMATTER));
			break;
			case 1 :
				Assert.assertEquals("2005-07-01", ch.getStartValidity().toString(StandardParser.FORMATTER));
				Assert.assertEquals("2007-07-01", ch.getStopValidity().toString(StandardParser.FORMATTER));
			break;
			case 2 :
				Assert.assertEquals("2007-07-01", ch.getStartValidity().toString(StandardParser.FORMATTER));
				Assert.assertEquals("2007-07-01", ch.getStopValidity().toString(StandardParser.FORMATTER));
			break;
			case 3 :
				Assert.assertEquals("2008-01-01", ch.getStartValidity().toString(StandardParser.FORMATTER));
				Assert.assertEquals("2008-01-01", ch.getStopValidity().toString(StandardParser.FORMATTER));
			break;
			}
			i++;
		}
		
		s = find("	", list);
		Assert.assertEquals(0, s.getStandardChanges().size());
		Assert.assertEquals(2, s.getStandardCsns().size());
		Assert.assertNotNull( find("ČSN EN 450-1:2013", s.getStandardCsns()) );
		Assert.assertEquals("(722064) Nahrazuje ČSN EN 450-1 + A1", find("ČSN EN 450-1:2013", s.getStandardCsns()).getNote() );
		
		Assert.assertEquals("2013-05-01", s.getStartValidity().toString(StandardParser.FORMATTER));
		Assert.assertEquals("2014-05-01", s.getStopValidity().toString(StandardParser.FORMATTER));
		
		Assert.assertEquals("(722064) Nahrazuje ČSN EN 450-1 + A1", find("ČSN EN 450-1:2013", s.getStandardCsns()).getNote() );
	}
	
	
	
	
	
	public Standard find(String standardId, List<Standard> list){
		for(Standard s : list){
			if(s.getStandardId().equals(standardId)){
				return s;
			}
		}
		return null;
	}
	
	
	public StandardChange find(String code, Standard standard){
		for(StandardChange ch : standard.getStandardChanges()){
			if(ch.getChangeCode().equals(code)){
				return ch;
			}
		}
		return null;
	}
	
	public StandardCsn find(String code, Set<StandardCsn> list){
		for(StandardCsn csn : list){
			if(csn.getCsnName().equals(code)){
				return csn;
			}
		}
		return null;
	}
	
	
}
