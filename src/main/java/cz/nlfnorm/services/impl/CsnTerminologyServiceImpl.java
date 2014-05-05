package cz.nlfnorm.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cz.nlfnorm.dao.CsnTerminologyDao;
import cz.nlfnorm.dto.CsnTerminologyDto;
import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.Csn;
import cz.nlfnorm.entities.CsnTerminology;
import cz.nlfnorm.entities.CsnTerminologyLog;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.enums.CsnTerminologyLanguage;
import cz.nlfnorm.parser.NewTerminologyParserImpl;
import cz.nlfnorm.parser.NoSectionTerminologyParser;
import cz.nlfnorm.parser.SingleSectionTerminologyParser;
import cz.nlfnorm.parser.TerminologyParser;
import cz.nlfnorm.parser.TikaProcessingContext;
import cz.nlfnorm.parser.WordDocumentParser;
import cz.nlfnorm.services.CsnService;
import cz.nlfnorm.services.CsnTerminologyLogService;
import cz.nlfnorm.services.CsnTerminologyService;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.utils.CodeUtils;
import cz.nlfnorm.utils.UserUtils;

/**
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 * @date Aug 7, 2013
 *
 */
@Service("csnTerminologyService")
@Transactional(propagation = Propagation.REQUIRED)
public class CsnTerminologyServiceImpl implements CsnTerminologyService{

	@Autowired
	private CsnTerminologyDao csnTerminologyDao;
	@Autowired
	private UserService userService;
	@Autowired
	private WordDocumentParser wordDocumentParser;
	@Autowired
	private CsnTerminologyLogService csnTerminologyLogService;
	@Autowired
	private CsnService csnService;
	
	@Override
	public void createCsnTerminology(CsnTerminology csnTerminology) {
		csnTerminologyDao.save(csnTerminology);
	}

	@Override
	public void updateCsnTerminology(CsnTerminology csnTerminology) {
		csnTerminologyDao.update(csnTerminology);
	}

	@Override
	public void deleteCsnTerminology(CsnTerminology csnTerminology) {
		csnTerminologyDao.remove(csnTerminology);
	}

	@Override
	@Transactional(readOnly = true)
	public CsnTerminology getById(Long id) {
		return csnTerminologyDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public CsnTerminology getByCode(String code) {
		return csnTerminologyDao.getByCode(code);
	}
	
	@Override
	public Long saveOrUpdate(CsnTerminology csnTerminology){
		User user = userService.getUserByUsername(UserUtils.getLoggedUser().getUsername());
		if(csnTerminology.getId() == null){
			csnTerminology.setCreatedBy(user);
			csnTerminology.setCreated(new LocalDateTime());
			csnTerminologyDao.save(csnTerminology);
		}else{
			csnTerminology.setChangedBy(user);
			csnTerminology.setChanged(new LocalDateTime());
			csnTerminologyDao.update(csnTerminology);
		}
		return csnTerminology.getId();
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isTitleUniqe(Long csnId, Long terminologyId, String title) {
		return csnTerminologyDao.isTitleUniqe(csnId, terminologyId, CodeUtils.toSeoUrl(title));
	}

	@Override
	@Transactional(readOnly = true)
	public List<CsnTerminology> searchInTerminology(String term) {
		if(StringUtils.isBlank(term)){
			return new ArrayList<CsnTerminology>();
		}
		List<CsnTerminology> result = csnTerminologyDao.searchInTerminology(term.trim());
		if(result == null){
			result = new ArrayList<CsnTerminology>();
		}
		
		return result;
	}

	@Override
	public void saveTerminologies(CsnTerminologyDto terminologyDto) {
		Validate.notNull(terminologyDto);
		if(CollectionUtils.isNotEmpty(terminologyDto.getCzechTerminologies())){
			saveTerminologies(terminologyDto.getCzechTerminologies());
		}
		if(CollectionUtils.isNotEmpty(terminologyDto.getEnglishTerminologies())){
			saveTerminologies(terminologyDto.getEnglishTerminologies());
		}
		
		
	}
	
	@Override
	public void saveTerminologies(List<CsnTerminology> terminologies){
		User user = UserUtils.getLoggedUser();
		if(CollectionUtils.isNotEmpty(terminologies)){	
			for(CsnTerminology t : terminologies){
				t.setCreated(new LocalDateTime());
				t.setCreatedBy(user);
				csnTerminologyDao.save(t);
			}
		}
	}
	
	@Override
	public void deleteAll(Set<CsnTerminology> terminologies){
		if(CollectionUtils.isNotEmpty(terminologies)){	
			for(CsnTerminology t : terminologies){
				csnTerminologyDao.remove(t);
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PageDto getCsnTerminologyPage(int currentPage, Map<String, Object> criteria) {
		return csnTerminologyDao.getCsnTerminologyPage(currentPage, criteria);
	}

	@Override
	@Transactional(readOnly = true)
	public CsnTerminology getBySectionAndLang(Csn csn, String sectionCode, CsnTerminologyLanguage lang) {
		return csnTerminologyDao.getBySectionAndLang(csn, sectionCode, lang);
	}

	
	public CsnTerminologyLog processImport(MultipartFile file, Csn csn, String contextPath){
		Validate.notNull(file);
		Validate.notNull(csn);
		Validate.notNull(contextPath);
		String fileName = file.getOriginalFilename();
		if(StringUtils.isNotBlank(fileName) && FilenameUtils.isExtension(fileName, "doc")){
			TikaProcessingContext tikaProcessingContext = new TikaProcessingContext();
			CsnTerminologyLog log = tikaProcessingContext.getLog();
			log.setFileName(fileName);
			long start = System.currentTimeMillis();
			try{
					tikaProcessingContext.setCsnId(csn.getId());
					tikaProcessingContext.setContextPath(contextPath);
					log.setCsn(csn);
					String docAsHtml = wordDocumentParser.parse(file.getInputStream(), tikaProcessingContext);
					if(StringUtils.isNotBlank(docAsHtml)){
						tikaProcessingContext.logInfo("Začátek čtení termínů");
						TerminologyParser terminologyParser = new NewTerminologyParserImpl();
						CsnTerminologyDto terminologies = terminologyParser.parse(docAsHtml, tikaProcessingContext);
						
						if(terminologies != null){
							tikaProcessingContext.logInfo(String.format("Čtení dokončeno. Počet termínů CZ/EN: %d / %d", 
							terminologies.getCzechTerminologies().size(), 
							terminologies.getEnglishTerminologies().size()));
						}
						
						if(terminologies == null || terminologies.hasOnlyFew()){
							tikaProcessingContext.logInfo("Nenašel sa žýdný termín, Začátek čtení termínů bez čísel sekcí.");
							terminologyParser = new NoSectionTerminologyParser();
							terminologies = terminologyParser.parse(docAsHtml, tikaProcessingContext);
							terminologyParser = new SingleSectionTerminologyParser();							
							CsnTerminologyDto terminologies2 = terminologyParser.parse(docAsHtml, tikaProcessingContext);
							
							if(terminologies2 != null){
								terminologies = terminologies.compareAndGetRelevant(terminologies2);
							}
						}
						
						if(terminologies != null){
							
							terminologies.setCsn(csn);
							log.setCzCount(terminologies.getCzechTerminologies().size());
							log.setEnCount(terminologies.getEnglishTerminologies().size());
							
							saveTerminologies(terminologies);
							csnService.saveOrUpdate(csn);
							log.setDuration(System.currentTimeMillis() - start);
							log.setSuccess(true);
							csnTerminologyLogService.createWithUser(log);
						}
					}
				} catch (Exception  e) {
					log.logError(String.format("dokument %1$s se nepodařilo importovat, duvod: %2$s",  fileName, e.getMessage()));
					return log;
				}
			log.updateImportStatus();
			csnTerminologyLogService.createWithUser(log);
			return log;
		}
		return null;
	}
	

}
