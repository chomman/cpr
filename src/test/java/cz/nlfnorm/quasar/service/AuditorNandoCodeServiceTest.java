package cz.nlfnorm.quasar.service;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.EacCode;
import cz.nlfnorm.quasar.services.AuditorEacCodeService;
import cz.nlfnorm.quasar.services.EacCodeService;


public class AuditorNandoCodeServiceTest extends AuditorServiceTest {
	
	private final static int EAC_CODE_COUNT = 10;
	private final static int QS_AUDITOR_EAC_CODE_COUNT = 5;
	
	@Autowired
	private EacCodeService eacCodeService;
	@Autowired
	private AuditorEacCodeService auditorEacCodeService;
	
	
	@Test
	@Rollback(value = true)
	public void eacCodeCreateTest(){
		EacCode code = createEacCode("EAC 1", true);
		eacCodeService.createOrUpdate(code);
		Assert.assertEquals(1, eacCodeService.getAllForQsAuditor().size());
		Assert.assertNotNull( eacCodeService.getByCode(code.getCode()));
	}
	
	
	@Test
	public void testEacCodeSync(){
		createEacCodes(EAC_CODE_COUNT);
		Assert.assertEquals(EAC_CODE_COUNT, eacCodeService.getAll().size());
		Assert.assertEquals(QS_AUDITOR_EAC_CODE_COUNT, eacCodeService.getAllForQsAuditor().size());
		Auditor auditor2 = createAndGet(999);
		Assert.assertNotNull(auditor2);
		auditorEacCodeService.syncAuditorEacCodes(auditor2);
		Assert.assertEquals(QS_AUDITOR_EAC_CODE_COUNT, auditorEacCodeService.getAllAuditorEacCodes(auditor2).size());
		
		Auditor auditor1 = createAndGet(9999);
		Assert.assertNotNull(auditor1);
		auditorEacCodeService.syncEacCodes();
		Assert.assertEquals(QS_AUDITOR_EAC_CODE_COUNT, auditorEacCodeService.getAllAuditorEacCodes(auditor1).size());
		
	}
	
	
	
	private EacCode createEacCode(String code, boolean isForQsAuditor){
		EacCode eac1 = new EacCode();
		eac1.setCode(code);
		eac1.setForQsAuditor(isForQsAuditor);
		eac1.setEnabled(true);
		return eac1;
	}
	
	private void createEacCodes(int count){
		for(int i = 1; i < (count + 1); i++){
			EacCode code = createEacCode("EAC "+ i, i % 2 == 0);
			eacCodeService.createOrUpdate(code);
		}
	}

}
