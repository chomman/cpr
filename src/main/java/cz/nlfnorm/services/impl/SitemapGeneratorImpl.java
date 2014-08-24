package cz.nlfnorm.services.impl;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import cz.nlfnorm.entities.CsnTerminology;
import cz.nlfnorm.entities.Standard;
import cz.nlfnorm.entities.Webpage;
import cz.nlfnorm.export.sitemap.SitemapIndexGenerator;
import cz.nlfnorm.export.sitemap.WebSitemapGenerator;
import cz.nlfnorm.export.sitemap.WebSitemapUrl;
import cz.nlfnorm.services.CsnTerminologyService;
import cz.nlfnorm.services.SiteampGenerator;
import cz.nlfnorm.services.StandardService;
import cz.nlfnorm.services.WebpageService;
import cz.nlfnorm.utils.WebpageUtils;
import cz.nlfnorm.web.controllers.fontend.ModuleDetailController;

public class SitemapGeneratorImpl implements SiteampGenerator {

	private final Logger logger = Logger.getLogger(getClass());
	
	private final static String WEBPAGE_SITEMAP = "webpage-sitemap";
	private final static String STANDARD_SITEMAP = "standard-sitemap";
	private final static String CSN_TERMINOLOGY_SITEMAP = "csn-terminology-sitemap";
	
	@Autowired
	private WebpageService webpageService;
	@Autowired 
	private StandardService standardService;
	@Autowired
	private CsnTerminologyService csnTerminologyService;
	
	@Value("#{config['host']}")
	private String host;
	
	@Value("#{config['sitemap.location']}")
	private String saveDir;
	
	
	
	@Override
	@Scheduled(cron = "0 0 5 * * ?")
	public void generate() {
		final Long start = System.currentTimeMillis();
		logger.info("Starting sitemap generation.");
		try {
			WebSitemapGenerator wsg = WebSitemapGenerator.builder(host , getSaveDir())
					.gzip(true).fileNamePrefix(WEBPAGE_SITEMAP).build();
			addWebpages(wsg);
			wsg.write();
			wsg = WebSitemapGenerator.builder(host , getSaveDir())
					.gzip(true).fileNamePrefix(STANDARD_SITEMAP).build();
			addStandards(wsg);
			wsg.write();
			wsg = WebSitemapGenerator.builder(host , getSaveDir())
					.gzip(true).fileNamePrefix(CSN_TERMINOLOGY_SITEMAP).build();
			addTerminologies(wsg);
			wsg.write();
			createSitemapIndex();
			logger.info("Sitemap is successfully generated. Generation took: " + (System.currentTimeMillis() - start) + "ms");
		} catch (MalformedURLException e) {
			logger.error("Sietemap generation failed.", e);
		}
	}
	
	private void createSitemapIndex() throws MalformedURLException{
		final File sitemapIndexFile = new File(saveDir + "sitemap.xml");
		SitemapIndexGenerator smi = new SitemapIndexGenerator(host, sitemapIndexFile);
		smi.addUrls(new String[]{
				buildFileUrl(WEBPAGE_SITEMAP), 
				buildFileUrl(STANDARD_SITEMAP), 
				buildFileUrl(CSN_TERMINOLOGY_SITEMAP) 
				});
		smi.write();
	}
	
	private String buildFileUrl(final String name){
		return host + "/" + name + ".xml";
	}
	
	private void  addWebpages(final WebSitemapGenerator wsg) throws MalformedURLException{
		final List<Webpage> webpageList = webpageService.getWebpagesForSitemap();
		for(final Webpage webpage : webpageList){
			if(webpage.getChanged() != null){
				wsg.addUrl(getUrl(webpage.getChanged(), host + WebpageUtils.getUrlFor(webpage, "")));
			}else{
				wsg.addUrl(host +WebpageUtils.getUrlFor(webpage, ""));
			}
		}
	}
	
	private void  addStandards(final WebSitemapGenerator wsg) throws MalformedURLException{
		final List<Standard> standardList = standardService.getStandardsForSitemap();
		for(final Standard standard : standardList){
			if(standard.getChanged() != null){
				wsg.addUrl( getUrl(standard.getChanged(), host + ModuleDetailController.EHN_DETAIL_URL + standard.getId()) );
				wsg.addUrl( getUrl(standard.getChanged(), host + "/en/" + ModuleDetailController.EHN_DETAIL_URL + standard.getId()) );
			}else{
				wsg.addUrl(host + ModuleDetailController.EHN_DETAIL_URL + standard.getId());
				wsg.addUrl(host + "/en/" +ModuleDetailController.EHN_DETAIL_URL +"/"+ standard.getId());
			}
		}
	}
	
	private WebSitemapUrl getUrl(final LocalDateTime changed, final String url) throws MalformedURLException{
		return new WebSitemapUrl.Options( url).lastMod(changed.toDate()).build();
	}
	
	private void  addTerminologies(final WebSitemapGenerator wsg) throws MalformedURLException{
		final List<CsnTerminology> terminologyList = csnTerminologyService.getAll();
		for(final CsnTerminology terminology : terminologyList){		
			wsg.addUrl(host + ModuleDetailController.TERMINOLOGY_URL_MAPPING +"/"+ terminology.getId());
		}
	}

	private File getSaveDir(){
		final File file = new File(saveDir);
		if(file.exists()){
			return file;
		}
		logger.error("Save dir does not exists: " + saveDir);
		return null;
	}
	
}
