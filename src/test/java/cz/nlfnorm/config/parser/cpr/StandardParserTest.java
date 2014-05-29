package cz.nlfnorm.config.parser.cpr;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import cz.nlfnorm.entities.Standard;
import cz.nlfnorm.entities.StandardChange;
import cz.nlfnorm.entities.StandardCsn;
import cz.nlfnorm.entities.StandardCsnChange;
import cz.nlfnorm.parser.cpr.StandardParser;

@Resource
public class StandardParserTest {
	
	@Test
	public void test(){
		StandardParser parser = new StandardParser();
		parser.parse("http://www.sgpstandard.cz/editor/files/unmz/nv190/nv_190.htm");
		List<Standard> list = parser.getStandards();
		
		Assert.assertNotNull(find("EN 1096-4:2004", list));
		Standard s = find("EN 54-2:1997", list);
		Assert.assertNotNull(find("A1:2006", s));
		
		Assert.assertNotNull(find("EN 295-4:2013", list));
		Assert.assertEquals("EN 295-10:2005", find("EN 295-4:2013", list).getReplaceStandard().getStandardId());
		
		Assert.assertNotNull(find("EN 450-1:2012", list));
		Assert.assertEquals("EN 450-1+A1:2007", find("EN 450-1:2012", list).getReplaceStandard().getStandardId());
		
		Assert.assertNotNull(find("EN 845-2:2013", list));
		Assert.assertEquals("EN 845-2:2003", find("EN 845-2:2013", list).getReplaceStandard().getStandardId());
		
		
		s = find("EN 13229:2001", list);
		Assert.assertEquals(4, s.getStandardChanges().size());
		Assert.assertEquals(1, s.getStandardCsns().size());
		
		Set<StandardCsnChange> csnChanges = s.getStandardCsns().iterator().next().getStandardCsnChanges();  
		Assert.assertNotNull(csnChanges);
		Assert.assertEquals(6, csnChanges.size());
		
		s = find("EN 13229:2001", list);
		Assert.assertNotNull(s);
		Assert.assertEquals(1, s.getStandardCsns().size());
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
