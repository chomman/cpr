package cz.nlfnorm.config.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cz.nlfnorm.config.TestConfig;
import cz.nlfnorm.services.SiteampGenerator;

public class SitemapGeneratorTest extends TestConfig{

	@Autowired
	private SiteampGenerator siteampGenerator;
	
	@Test
	public void sitemapGenerationTest(){
		siteampGenerator.generate();
	}
}
