package cz.nlfnorm.service;

import java.util.List;

import junit.framework.Assert;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import cz.nlfnorm.AbstractTestConfig;
import cz.nlfnorm.entities.Standard;
import cz.nlfnorm.enums.StandardStatus;
import cz.nlfnorm.services.StandardService;


public class StandardServiceTest extends AbstractTestConfig{
	
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
		Assert.assertEquals(StandardStatus.CANCELED_HARMONIZED, standard.getReplaceStandard().getStandardStatus());
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
		Assert.assertEquals(StandardStatus.CANCELED_HARMONIZED, standard.getReplaceStandard().getStandardStatus());
	}
	
	@Test
	public void standardStatusCanceledtoNormalTest(){
		Standard standard = standardService.getStandardById(435l);
		standard.setStandardStatus(StandardStatus.CANCELED_HARMONIZED);
		Standard cancelatedStandard = standardService.getStandardById(434l);
		Assert.assertNotNull(standard);
		Assert.assertNotNull(cancelatedStandard);
		standard.setReplaceStandard(cancelatedStandard);
		standardService.updateReferencedStandard(standard);
		Assert.assertEquals(cancelatedStandard, standard.getReplaceStandard());
		Assert.assertEquals(StandardStatus.HARMONIZED, standard.getReplaceStandard().getStandardStatus());
	}
	
	@Test
	public void standardStatusConcurentTest(){
		Standard standard = standardService.getStandardById(435l);
		standard.setStandardStatus(StandardStatus.CONCURRENT);
		Standard cancelatedStandard = standardService.getStandardById(434l);
		Assert.assertNotNull(standard);
		Assert.assertNotNull(cancelatedStandard);
		standard.setReplaceStandard(cancelatedStandard);
		standardService.updateReferencedStandard(standard);
		Assert.assertEquals(cancelatedStandard, standard.getReplaceStandard());
		Assert.assertEquals(StandardStatus.CONCURRENT, standard.getReplaceStandard().getStandardStatus());
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
		Assert.assertEquals(StandardStatus.HARMONIZED, standard.getReplaceStandard().getStandardStatus());
	}
	
	@Test
	public void testIsCanceled(){
		Standard s1 = standardService.getStandardById(331l);
				
		s1.setStandardStatus(StandardStatus.CANCELED_HARMONIZED);
		Assert.assertTrue(s1.getIsCanceled());

		s1.setStandardStatus(null);
		Assert.assertFalse(s1.getIsCanceled());
		

		s1.setStandardStatus(StandardStatus.NON_HARMONIZED);
		Assert.assertFalse(s1.getIsCanceled());
		
		s1.setStandardStatus(StandardStatus.HARMONIZED);
		Assert.assertFalse(s1.getIsCanceled());
		
	}
	
	@Test
	@Rollback(value = true)
	public void testChangedStandards(){
		Standard standard = standardService.getStandardById(433l);
		LocalDate date = new LocalDate(2014, new LocalDate().getMonthOfYear(), 1);
		final int lastDayOfMonth = date.dayOfMonth().getMaximumValue();
		standard.setStatusDate(null);
		standardService.updateStandard(standard);
		List<Standard> standards = standardService.getChangedStanards(date, date.withDayOfMonth(lastDayOfMonth), false);
		final int size = standards.size();
		// first day
		standard.setStatusDate(date);
		standardService.updateStandard(standard);
		standards = standardService.getChangedStanards(date, date.withDayOfMonth(lastDayOfMonth), false);
		Assert.assertEquals((size + 1), standards.size());
		
		standard.setStatusDate(date.plusDays(-1));
		standardService.updateStandard(standard);
		standards = standardService.getChangedStanards(date, date.withDayOfMonth(lastDayOfMonth), false);
		Assert.assertEquals(size , standards.size());
	}
	
	


}
