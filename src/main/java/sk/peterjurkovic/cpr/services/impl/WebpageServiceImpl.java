package sk.peterjurkovic.cpr.services.impl;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.WebpageDao;
import sk.peterjurkovic.cpr.dto.AutocompleteDto;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.entities.Webpage;
import sk.peterjurkovic.cpr.entities.WebpageContent;
import sk.peterjurkovic.cpr.enums.SystemLocale;
import sk.peterjurkovic.cpr.enums.WebpageModule;
import sk.peterjurkovic.cpr.services.FileService;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.services.WebpageService;
import sk.peterjurkovic.cpr.utils.CodeUtils;
import sk.peterjurkovic.cpr.utils.UserUtils;


@Service("webpageService")
@Transactional(propagation = Propagation.REQUIRED)
public class WebpageServiceImpl implements WebpageService{
	
	@Autowired
	private WebpageDao webpageDao;
	@Autowired
	private UserService userService;
	@Autowired
	private FileService fileService;
	
	private Logger logger = Logger.getLogger(getClass());
	
	
	
	@Override
	public void saveWebpage(Webpage webpage) {
		webpageDao.save(webpage);
	}
	
	@Override
	public void updateWebpage(Webpage webpage) {
		webpageDao.update(webpage);
	}
	
	@Override
	public void deleteWebpage(Webpage webpage) {
		webpageDao.remove(webpage);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Webpage getWebpageById(Long id) {
		return webpageDao.getByID(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Webpage getWebpageByCode(String code) {
		return webpageDao.getByCode(code);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Webpage> getAll() {
		return webpageDao.getAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public String getUniqeCode(String name, Long id){
		final String code =  StringUtils.trim( CodeUtils.toSeoUrl(name) );
		if(!isWebpageUrlUniqe(code, id)){
			return code + "-" + webpageDao.getNextIdValue();
		}
		return code;
	}
		
	@Override
	@Transactional(readOnly = true)
	public String getUniqeCode(String name){
		return getUniqeCode(name, Long.valueOf(-1));
	}
	
	@Override
	public void saveOrUpdate(Webpage webpage) {
		User user = null;
		if(UserUtils.getLoggedUser() != null){
			userService.getUserByUsername(UserUtils.getLoggedUser().getUsername());
		}
		if(webpage.getId() == null){
			webpage.setCreatedBy(user);
			webpage.setCreated(new LocalDateTime());
			webpageDao.save(webpage);
			
		}else{
			webpage.setChangedBy(user);
			webpage.setChanged(new LocalDateTime());
			webpageDao.update(webpage);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Webpage> getPublicSection(Long sectionId) {
		return webpageDao.getPublicSection(sectionId);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isWebpageUrlUniqe(final String code, final Long id) {
		if(StringUtils.isBlank(code)){
			return false;
		}
		Webpage persitedWebpage = getWebpageByCode(code);
		if(persitedWebpage == null || persitedWebpage.getId() == id){
			return true;
		}	
		return false;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Webpage> getAllOrderedWebpages() {
		return webpageDao.getAllOrderedWebpages();
	}

	@Override
	@Transactional(readOnly = true)
	public int getNextOrderValue(Long nodeId) {
		return webpageDao.getMaxOrderInNode(nodeId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Webpage> getTopLevelWepages() {
		return webpageDao.getTopLevelWepages(false);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Webpage> getTopLevelWepages(boolean enabledOnly) {
		return webpageDao.getTopLevelWepages(enabledOnly);
	}
	
	@Override
	public Long createNewWebpage(final Webpage form) {
		return createNewWebpage(form, 0l);
	}
	
	@Override
	public Long createNewWebpage(final Webpage form, final Long webpageNodeId) {
		Webpage parentWebpage = null;
		Webpage webpage = null;
		if(webpageNodeId != 0){
			parentWebpage = getWebpageById(webpageNodeId);
		}		
		if(parentWebpage != null){
			final int order = getNextOrderValue(parentWebpage.getId());
			webpage = new Webpage(parentWebpage);
			webpage.setOrder(order);
		}else{
			webpage = new Webpage();
			webpage.setOrder(getNextOrderValue( null ));
		}
		webpage.setWebpageType(form.getWebpageType());
		WebpageContent formContent = form.getDefaultWebpageContent();
		WebpageContent content = webpage.getDefaultWebpageContent();
		content.setName(StringUtils.trim(formContent.getName()));
		content.setTitle(formContent.getName());
		webpage.setCode( getUniqeCode( formContent.getName() ) );
		saveOrUpdate(webpage);
		return webpage.getId();
	}

	
	@Override
	public void createWebpageContent(final Long webpageId, final String langCode) {
		if(!SystemLocale.isAvaiable(langCode)){
			throw new IllegalArgumentException(String.format("Locale [%s] is not avaiable.", langCode));
		}
		Webpage webpage = getWebpageById(webpageId);
		Validate.notNull(webpage, String.format("Webpage [id=%s] was not found.", webpageId));
		Map<String, WebpageContent> localized = webpage.getLocalized();
		if(localized != null && !webpage.getLocalized().containsKey(langCode)){
			final String defaultLang = SystemLocale.getDefaultLanguage();
			WebpageContent newContent = new WebpageContent();
			newContent.setName(localized.get(defaultLang).getName());
			newContent.setTitle(localized.get(defaultLang).getTitle());
			webpage.getLocalized().put(langCode, newContent);
			saveOrUpdate(webpage);
		}
	}

	
	@Override
	@Transactional(readOnly = true)
	public List<AutocompleteDto> autocomplete(final String term) {
		if(StringUtils.isNotBlank(term)){
			return webpageDao.autocomplete(term);
		}
		return new ArrayList<AutocompleteDto>();
	}

	@Override
	public void deleteWebpageAvatar(Long webpageId) {
		Webpage webpage = getWebpageById(webpageId);
		if(webpage != null && StringUtils.isNotBlank(webpage.getAvatar())){
			if(fileService.removeAvatar(webpage.getAvatar())){
				webpage.setAvatar(null);
				saveOrUpdate(webpage);
			}
		}
	}

	
	@Override
	public void deleteWebpageWithAttachments(Long id) throws AccessDeniedException {
		final Webpage webpage = getWebpageById(id);
		if(webpage != null){
			
			if(webpage.getLockedRemove()){
				logger.warn(String.format("User [uid=%1$d][wid=%2$d] tried to remove locked webpage. ", 
							UserUtils.getLoggedUser().getId(), webpage.getId()));
				throw new AccessDeniedException("You have not right to remove webpage ID" + webpage.getId() );
			}
			
			Set<String> avatars = new HashSet<String>();
			if(webpage.getHasChildrens()){
				appendAllChildAvatars(webpage.getChildrens(), avatars) ;
			}
			
			deleteWebpage(webpage);
			for(String avatar : avatars){
				fileService.removeAvatar(avatar);
			}
		}
	}

	
	private void appendAllChildAvatars(Set<Webpage> childrens, Set<String> avatars){
		Validate.notNull(avatars);
		for(Webpage child : childrens){
			if(StringUtils.isNotBlank(child.getAvatar())){
				avatars.add(child.getAvatar());
			}
			if(child.getHasChildrens()){
				appendAllChildAvatars(child.getChildrens(), avatars);
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Webpage getHomePage() {
		return webpageDao.getHomePage();
	}

	@Override
	@Transactional(readOnly = true)
	public Webpage getWebpageByModule(WebpageModule webpageModule) {
		return webpageDao.getWebpageByModule(webpageModule);
	}

	@Override
	public Webpage getTopParentWebpage(final Webpage childrenNode) {
		Validate.notNull(childrenNode);
		if(childrenNode.getParent() == null){
			return childrenNode;
		}
		return webpageDao.getTopParentWebpage(childrenNode);
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Webpage> getChildrensOfNode(final Long id, final boolean publishedOnly){
		return webpageDao.getChildrensOfNode(id, publishedOnly);
	}
	
	
}
