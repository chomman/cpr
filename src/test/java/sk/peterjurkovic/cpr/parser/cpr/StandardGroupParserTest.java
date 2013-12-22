package sk.peterjurkovic.cpr.parser.cpr;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import sk.peterjurkovic.cpr.dao.AbstractTest;
import sk.peterjurkovic.cpr.entities.StandardGroup;

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
		//Assert.assertEquals(find(g.getStandardGroupMandates(), "M/100").getMandate().getMandateName() , "M/100");
		//Assert.assertEquals(find(g.getStandardGroupMandates(), "M/100").isComplement() , false);
		
		g = find(groups, "4");
		Assert.assertEquals(g.getCommissionDecision().getCzechLabel(), "99/91/ES");
		//Assert.assertEquals(find(g.getStandardGroupMandates(), "M/103_rev").getMandate().getMandateName() , "M/103_rev");
		//Assert.assertEquals(find(g.getStandardGroupMandates(), "M/103").getMandate().getMandateName() , "M/103");
		//Assert.assertEquals(find(g.getStandardGroupMandates(), "M/103_rev").isComplement() , false);
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
	
	
}
