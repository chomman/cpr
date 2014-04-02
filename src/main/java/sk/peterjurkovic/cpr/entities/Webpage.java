package sk.peterjurkovic.cpr.entities;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import sk.peterjurkovic.cpr.enums.WebpageType;
import sk.peterjurkovic.cpr.resolvers.LocaleResolver;

@Entity
@SequenceGenerator(name = "webpage_id_seq", sequenceName = "webpage_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "webpage")
public class Webpage extends AbstractEntity {
	
	private static final long serialVersionUID = 2815648808531067866L;
	
	
	private Webpage parent;
    private Set<Webpage> childrens;
	private int order;
	private Set<WebpageContent> webpageContents;
	private WebpageType webpageType;
	private String avatar;
	private String redirectUrl;
	private Webpage redirectWebpage;
	
	
	public Webpage(){
		this.childrens = new HashSet<Webpage>();
		this.webpageContents = new HashSet<WebpageContent>();
		this.webpageContents.add(
				new WebpageContent( LocaleResolver.getDefaultLocale() ) 
		);
		this.webpageType = WebpageType.ARTICLE;
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

	@OneToMany(mappedBy="webpage", cascade = { CascadeType.ALL }, orphanRemoval = true)
	public Set<WebpageContent> getWebpageContents() {
		return webpageContents;
	}
	
	@ManyToOne(fetch=FetchType.LAZY) 
	@JoinColumn(name = "parent_id") 
	public Webpage getParent() {
		return parent;
	}

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
	@Transient
	public String getCode() {
		return null;
	}


	

	public void setOrder(int order) {
		this.order = order;
	}

	public void setWebpageContents(Set<WebpageContent> webpageContents) {
		this.webpageContents = webpageContents;
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
    
}
