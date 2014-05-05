package cz.nlfnorm.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import cz.nlfnorm.dto.CsnTerminologyDto;
import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.Csn;
import cz.nlfnorm.entities.CsnTerminology;
import cz.nlfnorm.entities.CsnTerminologyLog;
import cz.nlfnorm.enums.CsnTerminologyLanguage;

/**
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 * @date Aug 7, 2013
 *
 */
public interface CsnTerminologyService {

	void createCsnTerminology(CsnTerminology csnTerminology);
	
	void updateCsnTerminology(CsnTerminology csnTerminology);
	
	void deleteCsnTerminology(CsnTerminology csnTerminology);
	
	CsnTerminology getById(Long id);
	
	CsnTerminology getByCode(String code);
	
	Long saveOrUpdate(CsnTerminology csnTerminology);
	
	boolean isTitleUniqe(Long csnId, Long terminologyId, String title);
	
	List<CsnTerminology> searchInTerminology(String term);
	
	void saveTerminologies(CsnTerminologyDto terminologies);
	
	void saveTerminologies(List<CsnTerminology> terminologies);
	
	void deleteAll(Set<CsnTerminology> terminologies);
	
	PageDto getCsnTerminologyPage(int currentPage, Map<String, Object> criteria);
	
	CsnTerminology getBySectionAndLang(Csn csn, String sectionCode, CsnTerminologyLanguage lang);
	
	CsnTerminologyLog processImport(MultipartFile file, Csn csn, String contextPath);
	
}
