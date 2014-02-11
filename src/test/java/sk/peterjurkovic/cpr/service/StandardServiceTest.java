package sk.peterjurkovic.cpr.service;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.enums.StandardStatus;
import sk.peterjurkovic.cpr.services.StandardService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/application-testContext.xml")
@TransactionConfiguration
@Transactional
public class StandardServiceTest{
	
	@Autowired
	private StandardService standardService;
	
	@Before
	public void setup(){
		
	}
	
	@Test
	public void standardStatusNormalToCanceledTest(){
		Standard standard = standardService.getStandardById(441l);
		Standard cancelatedStandard = standardService.getStandardById(441l);
		Assert.assertNotNull(standard);
		Assert.assertNotNull(cancelatedStandard);
		
		standard.setReplaceStandard(cancelatedStandard);
		standardService.updateReferencedStandard(standard);
		Assert.assertEquals(cancelatedStandard, standard.getReplaceStandard());
		Assert.assertEquals(StandardStatus.CANCELED, standard.getReplaceStandard().getStandardStatus());
	}
	
	
	@Test
	public void standardStatusNonHarmonizedTest(){
		Standard standard = standardService.getStandardById(441l);
		standard.setStandardStatus(StandardStatus.NON_HARMONIZED);
		Standard cancelatedStandard = standardService.getStandardById(441l);
		Assert.assertNotNull(standard);
		Assert.assertNotNull(cancelatedStandard);
		standard.setReplaceStandard(cancelatedStandard);
		standardService.updateReferencedStandard(standard);
		Assert.assertEquals(cancelatedStandard, standard.getReplaceStandard());
		Assert.assertEquals(StandardStatus.CANCELED, standard.getReplaceStandard().getStandardStatus());
	}
	
	@Test
	public void standardStatusCanceledtoNormalTest(){
		Standard standard = standardService.getStandardById(435l);
		standard.setStandardStatus(StandardStatus.CANCELED);
		Standard cancelatedStandard = standardService.getStandardById(434l);
		Assert.assertNotNull(standard);
		Assert.assertNotNull(cancelatedStandard);
		standard.setReplaceStandard(cancelatedStandard);
		standardService.updateReferencedStandard(standard);
		Assert.assertEquals(cancelatedStandard, standard.getReplaceStandard());
		Assert.assertEquals(StandardStatus.NORMAL, standard.getReplaceStandard().getStandardStatus());
	}
	
	@Test
	public void standardStatusCanceledHarmonizedTest(){
		Standard standard = standardService.getStandardById(433l);
		standard.setStandardStatus(StandardStatus.CANCELED_HARMONIZED);
		Standard cancelatedStandard = standardService.getStandardById(432l);
		Assert.assertNotNull(standard);
		Assert.assertNotNull(cancelatedStandard);
		standard.setReplaceStandard(cancelatedStandard);
		standardService.updateReferencedStandard(standard);
		Assert.assertEquals(cancelatedStandard, standard.getReplaceStandard());
		Assert.assertEquals(StandardStatus.NORMAL, standard.getReplaceStandard().getStandardStatus());
	}
	
	@Test
	public void testIsCanceled(){
		Standard s1 = standardService.getStandardById(331l);
		s1.setStandardStatus(StandardStatus.CANCELED);
		Assert.assertTrue(s1.getIsCanceled());
		
		s1.setStandardStatus(StandardStatus.CANCELED_HARMONIZED);
		Assert.assertTrue(s1.getIsCanceled());

		s1.setStandardStatus(null);
		Assert.assertFalse(s1.getIsCanceled());
		

		s1.setStandardStatus(StandardStatus.NON_HARMONIZED);
		Assert.assertFalse(s1.getIsCanceled());
		
		s1.setStandardStatus(StandardStatus.NORMAL);
		Assert.assertFalse(s1.getIsCanceled());
		
	}


}
