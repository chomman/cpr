package sk.peterjurkovic.cpr.parser.cpr;

import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import sk.peterjurkovic.cpr.entities.Mandate;
import sk.peterjurkovic.cpr.entities.StandardGroup;
import sk.peterjurkovic.cpr.test.AbstractTest;

public class StandardGroupParserTest extends AbstractTest {

	private List<StandardGroup> groups;
	
	@Test
	public void test(){
		StandardGroupParser parser = new StandardGroupParser();
		parser.parse("http://www.sgpstandard.cz/editor/files/unmz/nv190/skupiny.htm");
		groups = parser.getStandardGroups();
		Assert.assertEquals(35, groups.size());
		
		StandardGroup g = find(groups, "1");
		
		Assert.assertEquals(g.getCode(), "1");
		Assert.assertEquals(g.getCommissionDecision().getCzechLabel(), "99/94/ES");
		Assert.assertEquals(g.getCommissionDecision().getEnglishLabel(), "99/94/EC");
		Assert.assertEquals( find(g.getMandates(), "M/100").getMandateName() , "M/100");
		// zmeny mandatov
		Assert.assertNotNull( find(find(g.getMandates(), "M/100").getChanges(), "M/139") );
		Assert.assertNotNull( find(find(g.getMandates(), "M/100").getChanges(), "M/130") );
		Assert.assertNotNull( find(find(g.getMandates(), "M/100").getChanges(), "M/126") );

		
		g = find(groups, "4");
		Assert.assertEquals(g.getCommissionDecision().getCzechLabel(), "99/91/ES");
		// hlavne mandaty
		Assert.assertEquals( find(g.getMandates(), "M/103").getMandateName() , "M/103");
		Assert.assertEquals( find(g.getMandates(), "M/103_rev").getMandateName() , "M/103_rev");
		// zmeny mandatov
		Assert.assertNotNull( find(find(g.getMandates(), "M/103").getChanges(), "M/126") );
		Assert.assertNotNull( find(find(g.getMandates(), "M/103").getChanges(), "M/130") );
		Assert.assertNotNull( find(find(g.getMandates(), "M/103").getChanges(), "M/137") );
		Assert.assertNotNull( find(find(g.getMandates(), "M/103").getChanges(), "M/138") );
		Assert.assertNotNull( find(find(g.getMandates(), "M/103").getChanges(), "M/367") );
		Assert.assertEquals(g.getCommissionDecision().getDraftAmendmentLabel(), "Construct 09/847");
		g = find(groups, "5");
		Assert.assertEquals(g.getCommissionDecision().getCzechLabel(), "95/467/ES");
		g = find(groups, "29");
		Assert.assertNull(g.getCommissionDecision());
		g = find(groups, "33");
		Assert.assertNull(g.getCommissionDecision());
		
		g = find(groups, "35");
		Assert.assertEquals(g.getCommissionDecision().getCzechLabel(), "99/454/ES");
	}
	
	
	
	private StandardGroup find(List<StandardGroup> groups, String code){
		for(StandardGroup sg : groups){
			if(sg.getCode().equals(code)){
				return sg;
			}
		}
		return null;
	}
	
	
	private Mandate find(Set<Mandate> mandates, String code){
		for(Mandate m : mandates){
			if(m.getMandateName().equals(code)){
				return m;
			}
		}
		return null;
	}
	
}
