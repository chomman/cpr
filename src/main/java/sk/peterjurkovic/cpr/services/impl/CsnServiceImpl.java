package sk.peterjurkovic.cpr.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.dao.CsnDao;
import sk.peterjurkovic.cpr.dto.CsvImportLogDto;
import sk.peterjurkovic.cpr.dto.PageDto;
import sk.peterjurkovic.cpr.entities.Csn;
import sk.peterjurkovic.cpr.entities.CsnCategory;
import sk.peterjurkovic.cpr.entities.CsnTerminology;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.CsnCategoryService;
import sk.peterjurkovic.cpr.services.CsnService;
import sk.peterjurkovic.cpr.services.FileService;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.utils.CodeUtils;
import sk.peterjurkovic.cpr.utils.ParseUtils;
import sk.peterjurkovic.cpr.utils.UserUtils;


@Service("csnService")
@Transactional(propagation = Propagation.REQUIRED)
public class CsnServiceImpl implements CsnService{
	
	
	@Autowired
	private CsnDao csnDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CsnCategoryService csnCategoryService;
	
	@Autowired
	private FileService fileService;
	
	
	@Override
	public void createCsn(Csn csn) {
		csnDao.save(csn);
	}

	@Override
	public void updateCsn(Csn csn) {
		csnDao.update(csn);
	}

	@Override
	public void deleteCsn(Csn csn) {
		fileService.removeDirectory(Constants.CSN_DIR_PREFIX + csn.getId());
		csnDao.remove(csn);
	}

	@Override
	@Transactional(readOnly = true)
	public Csn getById(Long id) {
		return csnDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Csn getByCode(String code) {
		return csnDao.getByCode(code);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Csn> getAll() {
		return csnDao.getAll();
	}

	@Override
	public void saveOrUpdate(Csn csn) {
		User user = userService.getUserByUsername(UserUtils.getLoggedUser().getUsername());
		csn.setCode(CodeUtils.toSeoUrl(csn.getCsnId()));
		if(csn.getId() == null){
			csn.setCreatedBy(user);
			csn.setCreated(new LocalDateTime());
			csnDao.save(csn);
			fileService.createDirectory(Constants.CSN_DIR_PREFIX + csn.getId());
		}else{
			csn.setChangedBy(user);
			csn.setChanged(new LocalDateTime());
			csnDao.update(csn);
		}
		
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isCsnIdUniqe(Long id, String csnId) {
		return csnDao.isCsnIdUniqe(id, csnId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Csn> getCsnByTerminology(String terminologyTitle) {
		return csnDao.getCsnByTerminology(terminologyTitle);
	}

	
	@Override
	@Transactional(readOnly =  true )
	public PageDto getCsnPage(int pageNumber,Map<String, Object> criteria) {
		return csnDao.getCsnPage(pageNumber, validateCriteria(criteria));
	}
	
	private Map<String, Object> validateCriteria(Map<String, Object> criteria){
		if(criteria.size() != 0){
			criteria.put("orderBy", ParseUtils.parseIntFromStringObject(criteria.get("orderBy")));
		}
		return criteria;
	}
	

	@Override
	@Transactional(readOnly =  true )
	public List<CsnTerminology> getTerminologyByCsnAndLang(Csn csn, String languageCode) {
		return csnDao.getTerminologyByCsnAndLang(csn, languageCode);
	}

	@Override
	public void deleteAllTerminology(Csn csn) {
		Validate.notNull(csn);
		csnDao.deleteAllTerminology(csn.getId());
	}

	@Override
	public void removeAll() {
		List<Csn> list = getAll();
		for(Csn csn : list){
			deleteCsn(csn);
		}
	}

	@Override
	@Transactional(readOnly =  true )
	public List<Csn> autocompleteByClassificationSymbol(String term) {
		if(StringUtils.isNotBlank(term)){
			return csnDao.autocompleteByClassificationSymbol(term);
		}
		return new ArrayList<Csn>();
	}

	@Override
	@Transactional(readOnly =  true )
	public List<Csn> autocompleteByCsnId(String term) {
		if(StringUtils.isNotBlank(term)){
			return  csnDao.autocompleteByCsnId(term);
		}
		return new ArrayList<Csn>();
	}

	@Override
	@Transactional(readOnly = true)
	public Csn getByClassificationSymbol(String cs) {
		return csnDao.getByClassificationSymbol(cs);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Csn> getCsnsByClassificationSymbol(String cs) {
		if(StringUtils.isNotBlank(cs)){
			return csnDao.getCsnsByClassificationSymbol(cs);
		}
		return new ArrayList<Csn>();
	}

	@Override
	public CsvImportLogDto saveList(List<Csn> csnList) {
		CsvImportLogDto log = new CsvImportLogDto();
		User user = UserUtils.getLoggedUser();
		if(CollectionUtils.isNotEmpty(csnList)){
			for(Csn csn : csnList){
				if(isCsnIdUniqe(0l, csn.getCsnId())){
					csn.setCreatedBy(user);
					CsnCategory category = csnCategoryService.findByClassificationSymbol(csn.getClassificationSymbol());
					if(category != null){
						csn.setCsnCategory(category);
					}else{
						log.appendInfo("Pro položku s označením: <b>" +csn.getCsnId() + "</b> neni v DB evidovaný žádný odbor, třídicí znak: "+  csn.getClassificationSymbol());
					}
					createCsn(csn);
					log.incrementSuccess();
				}else{
					Csn persistedCsn = getByCode(csn.getCode());
					if(persistedCsn != null){
						persistedCsn.setPublished(csn.getPublished());
						csnDao.update(csn);
					}
					log.appendInfo("Položka s označením: <b>" + csn.getCsnId() + "</b> se již v databázy nachází, byla přeskočena.");
					log.incrementFailure();
				}
			}
		}
		return log;
	}

	
	@Override
	@Transactional(readOnly = true)
	public Csn getByCatalogId(String catalogId) {
		if(StringUtils.isNotBlank(catalogId)){
			return csnDao.getByCatalogId(catalogId);
		}
		return null;
	}
	
}
