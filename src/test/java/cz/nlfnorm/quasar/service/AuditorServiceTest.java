package cz.nlfnorm.quasar.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import cz.nlfnorm.config.TestConfig;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.EducationLevel;
import cz.nlfnorm.quasar.services.AuditorService;
import cz.nlfnorm.quasar.services.EducationLevelService;
import cz.nlfnorm.quasar.services.ExperienceService;

public class AuditorServiceTest extends TestConfig{

	@Autowired
	protected AuditorService auditorService;
	@Autowired
	protected EducationLevelService educationLevelService;
	@Autowired
	protected ExperienceService experienceService;
	
	@Test
	@Rollback(value = true)
	public void auditorCreateTest(){
		Auditor auditor1 = create(999);
		auditorService.createAuditor(auditor1, auditor1.getPassword());
		Assert.assertEquals(1, auditorService.getAll().size());
		Assert.assertFalse(auditorService.isItcIdUniqe(auditor1.getItcId(), 0l));
	}
	
	protected Auditor createAndGet(int itcId){
		Auditor auditor1 = create(itcId);
		auditorService.createAuditor(auditor1, auditor1.getPassword());
		return auditor1;
	}
	
	protected Auditor create(int itcId){
		Auditor auditor = new Auditor();
		auditor.setFirstName("test");
		auditor.setLastName("test");
		auditor.setEmail(System.currentTimeMillis() +  "@nlfnorm.cz");
		auditor.setPassword("123456");
		auditor.setItcId(itcId);
		auditor.setAprovedForIso13485(true);
		auditor.setAprovedForIso9001(true);
		
		auditor.setCvDelivered(true);
		auditor.setEcrCardSigned(true);
		auditor.setConfidentialitySigned(true);
		
		final EducationLevel master = educationLevelService.getById((long)EducationLevel.MASTER);
		final EducationLevel prof = educationLevelService.getById((long)EducationLevel.PHD);
		
		auditor.getForActiveMedicalDevice().setEductionLevel(master);
		auditor.getForNonActiveMedicalDevice().setEductionLevel(prof);
		
		return auditor;
		
	}
	
	
	
}
