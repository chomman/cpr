package sk.peterjurkovic.cpr.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import sk.peterjurkovic.cpr.dto.CsnTerminologyDto;
import sk.peterjurkovic.cpr.dto.PageDto;
import sk.peterjurkovic.cpr.entities.Csn;
import sk.peterjurkovic.cpr.entities.CsnTerminology;
import sk.peterjurkovic.cpr.enums.CsnTerminologyLanguage;

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
	
}
