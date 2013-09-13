package sk.peterjurkovic.cpr.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "csn_category")
public class CsnCategory extends AbstractEntity {

	
	private static final long serialVersionUID = 2903401268039302450L;
	
	
	private Long id;
	
	private String name;
	
	private String searchCode;
	
	private CsnCategory parent;

    private Set<CsnCategory> children;
	
	public CsnCategory(){
		children = new HashSet<CsnCategory>();
	}
	
    

	@ManyToOne(fetch=FetchType.LAZY) 
	@JoinColumn(name = "parent_id") 
	public CsnCategory getParent() {
		return parent;
	}

	public void setParent(CsnCategory csnCategory) {
		this.parent = csnCategory;
	}
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "parent_id")
	public Set<CsnCategory> getChildren() {
		return children;
	}

	public void setChildren(Set<CsnCategory> children) {
		this.children = children;
	}


	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getSearchCode() {
		return searchCode;
	}


	@Column(name = "search_code", length= 10)
	public void setSearchCode(String searchCode) {
		this.searchCode = searchCode;
	}

	
	
	
}
