package cz.nlfnorm.dao;

import java.util.List;
import java.util.Map;

import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.Csn;
import cz.nlfnorm.entities.CsnTerminology;
import cz.nlfnorm.enums.CsnTerminologyLanguage;

public interface CsnTerminologyDao extends BaseDao<CsnTerminology, Long>{
	
	
	boolean isTitleUniqe(Long csnId, Long terminologyId, String title);
	
	List<CsnTerminology> searchInTerminology(String term);
	
	PageDto getCsnTerminologyPage(int currentPage, Map<String, Object> criteria);
	
	CsnTerminology getBySectionAndLang(Csn csn, String sectionCode, CsnTerminologyLanguage lang);
}
