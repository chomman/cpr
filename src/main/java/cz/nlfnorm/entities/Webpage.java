package cz.nlfnorm.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import cz.nlfnorm.context.ContextHolder;
import cz.nlfnorm.enums.SystemLocale;
import cz.nlfnorm.enums.WebpageModule;
import cz.nlfnorm.enums.WebpageType;

@Entity
@SequenceGenerator(name = "webpage_id_seq", sequenceName = "webpage_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "webpage")
public class Webpage extends AbstractEntity {
	
	public static final String FOOTER_CODE = "paticka";
	public static final String REGISTATION_CODE = "registrace";
	public static final String TERMS_OF_CONDITIONS_CODE = "obchodni-podminky";
	
	private static final long serialVersionUID = 2815648808531067866L;

	private Webpage parent;
    private Set<Webpage> childrens;
	private int order;
	private WebpageType webpageType;
	private String avatar;
	private String redirectUrl;
	private Webpage redirectWebpage;
	private Map<String, WebpageContent> localized;
	private Boolean lockedCode;
	private Boolean lockedRemove;
	private LocalDateTime publishedSince;
	private WebpageModule webpageModule;
	private Boolean showThumbnail;
	private Boolean isOnlyForRegistrated;
	private Boolean fullWidth;
	private Long hit;
	
	
	public Webpage(){
		this( null );
	}
	
	public Webpage(final Webpage parent) {
        this.parent = parent;
        this.childrens = new HashSet<Webpage>();
		this.webpageType = WebpageType.ARTICLE;
		this.localized = new HashMap<String, WebpageContent>();
		this.localized.put(SystemLocale.getDefaultLanguage(), new WebpageContent());
		this.lockedCode = Boolean.FALSE;
		this.lockedRemove = Boolean.FALSE;
		this.showThumbnail = Boolean.TRUE;
		this.isOnlyForRegistrated = Boolean.FALSE;
		this.fullWidth = Boolean.FALSE;
		this.hit = 0l;
		setEnabled(Boolean.FALSE);
		if(parent != null){
			registerInParentsChilds();
		}
    }
	
	/* GETTERS ---------------------
	 * 
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "webpage_id_seq")
	public Long getId() {
		return super.getId();
	}
	
	@Column(name = "webpage_order")
	public int getOrder() {
		return order;
	}

	@ElementCollection
	@CollectionTable(name = "webpage_content")
	@MapKeyJoinColumn(name = "locale")
	public Map<String, WebpageContent> getLocalized() {
		return localized;
	}

	@ManyToOne(fetch=FetchType.LAZY) 
	@JoinColumn(name = "parent_id") 
	public Webpage getParent() {
		return parent;
	}
	
	@OrderBy(clause = "order" )
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<Webpage> getChildrens() {
		return  childrens;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "webpage_type", length = 25)
	public WebpageType getWebpageType() {
		return webpageType;
	}
	
	@Column(name = "avatar", length = 100)
	public String getAvatar() {
		return avatar;
	}
	
	@Column(name = "redirect_url")
	public String getRedirectUrl() {
		return redirectUrl;
	}
	
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "redirect_webpage_id")
	public Webpage getRedirectWebpage() {
		return redirectWebpage;
	}
	
	
	@Column(name = "is_locked_code")
	public Boolean getLockedCode() {
		return lockedCode;
	}

	@Column(name = "is_locked_remove")
	public Boolean getLockedRemove() {
		return lockedRemove;
	}
	
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	@Column(name = "published_since")
	public LocalDateTime getPublishedSince() {
		return publishedSince;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "webpage_modlue", length = 30)
	public WebpageModule getWebpageModule() {
		return webpageModule;
	}
	
	@Column(name = "show_thumb")
	public Boolean getShowThumbnail() {
		return showThumbnail;
	}
	
	@Column(name = "only_for_registraged", nullable = false)
	public Boolean getIsOnlyForRegistrated() {
		return isOnlyForRegistrated;
	}
	
	@Column(name = "full_page_width")
	public Boolean getFullWidth() {
		return fullWidth;
	}

	public Long getHit() {
		return hit;
	}
	
    /* SETTER ---------------------
	 * 
	 */

	public void setShowThumbnail(Boolean showThumbnail) {
		this.showThumbnail = showThumbnail;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public void setLocalized(Map<String, WebpageContent> localizedWebpage) {
		this.localized = localizedWebpage;
	}
	
	public void setParent(Webpage parent) {
		this.parent = parent;
	}
	
	public void setChildrens(Set<Webpage> childrens) {
		this.childrens = childrens;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public void setRedirectWebpage(Webpage redirectWebpage) {
		this.redirectWebpage = redirectWebpage;
	}
	
	public void setWebpageType(WebpageType webpageType) {
		this.webpageType = webpageType;
	}
	
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public void setLockedCode(Boolean lockedCode) {
		this.lockedCode = lockedCode;
	}
	public void setLockedRemove(Boolean lockedRemove) {
		this.lockedRemove = lockedRemove;
	}
	
	public void setPublishedSince(LocalDateTime publishedSince) {
		this.publishedSince = publishedSince;
	}
	public void setWebpageModule(WebpageModule webpageModule) {
		this.webpageModule = webpageModule;
	}
	
	public void setIsOnlyForRegistrated(Boolean isOnlyForRegistrated) {
		this.isOnlyForRegistrated = isOnlyForRegistrated;
	}
		
	public void setFullWidth(Boolean fullWidth) {
		this.fullWidth = fullWidth;
	}


	public void setHit(Long hit) {
		this.hit = hit;
	}

	@Transient
	public String getTitleInLang(){
		if(localized.containsKey(ContextHolder.getLang())){
			return localized.get(ContextHolder.getLang()).getTitle();
		}
		return null;
	}
	
	@Transient
	public String getDescriptionInLang(){
		if(localized.containsKey(ContextHolder.getLang())){
			return localized.get(ContextHolder.getLang()).getDescription();
		}
		return null;
	}
	
	@Transient
	public String getContentInLang(){
		if(localized.containsKey(ContextHolder.getLang())){
			return localized.get(ContextHolder.getLang()).getContent();
		}
		return null;
	}
	
	@Transient
	public String getDefaultName(){
		WebpageContent content = getDefaultWebpageContent();
		if( content != null){
			return content.getName();
		}
		return null;
	}
	
	@Transient
	public WebpageContent getDefaultWebpageContent(){
		return localized.get(SystemLocale.getDefaultLanguage());
	}
	
	@Transient
	public WebpageContent getWebpageContentInLang(String lang){
		if(localized.containsKey(lang)){
			return localized.get(lang);
		}
		return getDefaultWebpageContent();
	}

	@Transient
	public boolean getHasChildrens(){
		return this.childrens.size() > 0; 
	}
	
	@Transient
	public LocalDateTime getPublished(){
		if(isPublishedSet()){
			return publishedSince;
		}else if(getChanged() != null){
			return getChanged();
		}
		return getCreated();
	}
	
	@Transient
	public User getPublishedBy(){
		if(getChangedBy() != null){
			return getChangedBy();
		}
		return getCreatedBy();
	}
	
	@Transient
	public boolean getIsPublished(){
		if(getEnabled() && isPublishedSet()){
			LocalDateTime now  = new LocalDateTime();
			return now.isAfter(publishedSince);
		}
		return getEnabled();
	}
	
	@Transient
	private boolean isPublishedSet(){
		if(webpageType != null && webpageType.equals(WebpageType.NEWS) && publishedSince != null){
			return true;
		}
		return false;
	}
	
	@Transient
	public boolean isHomepage(){
		if(order == 0 && parent == null){
			return true;
		}
		return false;
	}
	
	
	@Transient
	public List<Webpage> getPublishedChildrens(){
		List<Webpage> webpageList = new ArrayList<Webpage>();
		if(CollectionUtils.isNotEmpty(childrens)){
			for(Webpage child : childrens){
				if(child.getIsPublished()){
					webpageList.add(child);
				}
			}
		}
		return webpageList;
	}
	
	
	@Transient
	public List<Webpage> getPublishedSections(){
		List<Webpage> webpageList = new ArrayList<Webpage>();
		if(CollectionUtils.isNotEmpty(childrens)){
			for(Webpage child : childrens){
				if(child.getIsPublished() && !WebpageType.NEWS.equals( child.getWebpageType()) ){
					webpageList.add(child);
				}
			}
		}
		return webpageList;
	}
	
	@Transient
	public boolean getIsHomepage(){
		return isHomepage();
	}
	
	@Transient
	public void changeParentWebpage(Webpage webpage){
		this.parent = webpage;
		if(parent != null){
			registerInParentsChilds();
		}
	}
	
    private void registerInParentsChilds() {
        this.parent.childrens.add(this);
    }
    
    @Transient
    public void incrementHit(){
    	hit++;
    }
    
}
