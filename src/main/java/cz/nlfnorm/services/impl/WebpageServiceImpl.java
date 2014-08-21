package cz.nlfnorm.services.impl;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.dao.WebpageDao;
import cz.nlfnorm.dto.AutocompleteDto;
import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.entities.Webpage;
import cz.nlfnorm.entities.WebpageContent;
import cz.nlfnorm.enums.SystemLocale;
import cz.nlfnorm.enums.WebpageModule;
import cz.nlfnorm.enums.WebpageType;
import cz.nlfnorm.services.FileService;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.services.WebpageService;
import cz.nlfnorm.utils.CodeUtils;
import cz.nlfnorm.utils.UserUtils;


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
		if(isNewsCategory(parentWebpage)){
			webpage.setWebpageType(WebpageType.NEWS);
		}else{
			webpage.setWebpageType(form.getWebpageType());
		}
		WebpageContent formContent = form.getDefaultWebpageContent();
		WebpageContent content = webpage.getDefaultWebpageContent();
		content.setName(StringUtils.trim(formContent.getName()));
		content.setTitle(formContent.getName());
		webpage.setCode( getUniqeCode( formContent.getName() ) );
		saveOrUpdate(webpage);
		if(isNewsCategory(parentWebpage)){
			moveOldNewsToArchive(parentWebpage);
		}
		return webpage.getId();
	}
	
	private boolean isNewsCategory(final Webpage webpage){
		return (webpage == null || !webpage.getWebpageType().equals(WebpageType.NEWS_CATEGORY));
	}

	@Async
	@Override
	public void moveOldNewsToArchive(final Webpage node){
		List<Webpage> oldNewsList = getOldNonArchivedNewsInNode(node);
		if(CollectionUtils.isNotEmpty(oldNewsList)){
			Webpage archive = webpageDao.getWebpageByModule(WebpageModule.NEWS_ARCHIVE);
			if(archive != null){
				int order = webpageDao.getMaxOrderInNode(archive.getId());
				for(Webpage oldNews : oldNewsList){
					oldNews.setParent(archive);
					oldNews.setOrder(order);
					updateWebpage(oldNews);
					logger.debug("Webpage " +  oldNews.getId() + " is moved int archive.");
					order++;
				}
			}
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Webpage> getOldNonArchivedNewsInNode(Webpage node){
		return webpageDao.getOldNonArchivedNewsInNode(node);
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
	public List<AutocompleteDto> autocomplete(final String term, final Boolean enabledOnly) {
		return autocomplete(term, enabledOnly, null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AutocompleteDto> autocomplete(final String term, final Boolean enabledOnly, final Long excludeId) {
		if(StringUtils.isNotBlank(term)){
			return webpageDao.autocomplete(term, enabledOnly, excludeId);
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

	
	public void moveWebpage(Webpage webpage, int newOrder){
		Validate.notNull(webpage);
		int oldOrder = webpage.getOrder();
		if(newOrder < 0){
			throw new IllegalArgumentException("New order of webpage ["+webpage.getId()+"] can not be less than zero.");
		}
		if(newOrder != oldOrder){
			List<Webpage> webpageList  = null;
			if(webpage.getParent() == null){
				webpageList = getTopLevelWepages();
			}else{
				webpageList = getChildrensOfNode(webpage.getParent().getId(), false );
			}
			if(webpageList.size() < 1){
				throw new IllegalArgumentException("Children of webpage[id="+webpage.getId()+"] was not found.");
			}
			webpageList.remove(webpage);
			webpageList.add(newOrder, webpage);
			updateWebpageListOrder(webpageList);
		}
	}
	
	@Override
	public void moveWebpage(Webpage webpage, Long parentId, int newOrder) {
		Validate.notNull(webpage);
		Webpage oldParentWebpage = webpage.getParent();
		Webpage newParentWebpage = null;
		if(parentId != null){
			newParentWebpage = getWebpageById(parentId);
		}
		if((oldParentWebpage == null && newParentWebpage == null) ||
			(oldParentWebpage != null && newParentWebpage != null && newParentWebpage.equals(oldParentWebpage))
		){
			moveWebpage(webpage, newOrder);
		}else{
			webpage.changeParentWebpage(newParentWebpage);
			moveWebpage(webpage, newOrder);
			if(oldParentWebpage == null){
				updateWebpageListOrder( getTopLevelWepages()  );
			}else{
				updateWebpageListOrder( getChildrensOfNode(oldParentWebpage.getId(), false ));
			}
		}
	
	}
	
	private void updateWebpageListOrder(List<Webpage> webpageList){
		int i = 0;
		for(Webpage webpage : webpageList){
			webpage.setOrder( i );
			updateWebpage(webpage);
			i++;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Webpage> getLatestPublishedNews(int limit) {
		return webpageDao.getLatestPublishedNews(limit);
	}

	@Override
	public void incrementHit(final Webpage webpage) {
		webpage.incrementHit();
		updateWebpage(webpage);
	}

	@Override
	@Transactional(readOnly = true)
	public PageDto getSearchPage(final int pageNumber, String term, final Long parentNodeId) {
		if(StringUtils.isNotBlank(term)){
			term  =  term.trim().replaceAll(" +", " ");
			return webpageDao.search(pageNumber, term, parentNodeId);
		}
		return new PageDto();
	}


	@Async
	@Override
	public void updateTsVector(Webpage webpage) {
		webpageDao.updatetsVector(webpage);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Webpage> getFooterWebpages() {
		return webpageDao.getFooterWebpages();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Webpage> getSimilarNews(final Webpage webpage, final int limit) {
		return getSimilarWepages(webpage, limit, null );
	}

	@Override
	@Transactional(readOnly = true)
	public List<Webpage> getSimilarArticles(Webpage webpage, int limit) {
		return getSimilarWepages(webpage, limit, WebpageType.ARTICLE);
	}
	
	/**
	 * Return similar and enabled {@link Webpage}s, which has assigned at least one same tag
	 * 
	 * @param webpage webpage, which should be compared
	 * @param limit - limit of returned items
	 * @param type - if webpageType eq NULL, only news will be returned
	 * 
	 * @return similar webpages, which has equal one or more webpages tags.
	 */
	public List<Webpage> getSimilarWepages(Webpage webpage, int limit, WebpageType type) {
		if(CollectionUtils.isEmpty(webpage.getTags())){
			return new ArrayList<>();
		}
		return webpageDao.getSimilarWebpages(webpage, limit, type);
	}
	
	
}
