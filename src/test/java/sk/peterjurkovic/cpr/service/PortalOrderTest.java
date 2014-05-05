package sk.peterjurkovic.cpr.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import sk.peterjurkovic.cpr.test.AbstractTest;
import cz.nlfnorm.services.PortalUserService;


public class PortalOrderTest extends AbstractTest {

	@Autowired
	private PortalUserService portalUserService;

	@Test
	public void testRequest(){
		portalUserService.sendRequest();
	}
}
