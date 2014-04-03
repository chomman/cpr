package sk.peterjurkovic.cpr.entities;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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

import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import sk.peterjurkovic.cpr.enums.SystemLocale;
import sk.peterjurkovic.cpr.enums.WebpageType;

@Entity
@SequenceGenerator(name = "webpage_id_seq", sequenceName = "webpage_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "webpage")
public class Webpage extends AbstractEntity {
	
	private static final long serialVersionUID = 2815648808531067866L;

	private Webpage parent;
    private Set<Webpage> childrens;
	private int order;
	private WebpageType webpageType;
	private String avatar;
	private String redirectUrl;
	private Webpage redirectWebpage;
	private Map<String, WebpageContent> localized;
	private Boolean locked;
	private LocalDateTime publishedSince;
	
	public Webpage(){
		this( null );
	}
	
	public Webpage(final Webpage parent) {
        this.parent = parent;
        this.childrens = new HashSet<Webpage>();
		this.webpageType = WebpageType.ARTICLE;
		this.localized = new HashMap<String, WebpageContent>();
		this.localized.put(SystemLocale.getDefaultLanguage(), new WebpageContent());
		this.locked = Boolean.FALSE;
		setEnabled(Boolean.FALSE);
		if(parent != null){
			registerInParentsChilds();
		}
    }

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
		return  Collections.unmodifiableSet(childrens);
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
	
	@Column(name = "is_locked")
	public Boolean getLocked() {
		return locked;
	}
	
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	@Column(name = "published_since")
	public LocalDateTime getPublishedSince() {
		return publishedSince;
	}

	@Transient
	public String getCode() {
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
	public boolean getHasChildrens(){
		return this.childrens.size() > 0; 
	}
	
	
	

    /** 
     * Register this domain in the child list of its parent. 
     */
    private void registerInParentsChilds() {
        this.parent.childrens.add(this);
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
	
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}
	
	public void setPublishedSince(LocalDateTime publishedSince) {
		this.publishedSince = publishedSince;
	}


}
