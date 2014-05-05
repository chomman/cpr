package cz.nlfnorm.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

import org.apache.commons.collections.CollectionUtils;

import cz.nlfnorm.dto.CsnCategoryJsonDto;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "csn_cateogry_id_seq", sequenceName = "csn_cateogry_id_seq", initialValue = 1, allocationSize =1)
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "csn_cateogry_id_seq")
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
	
	
	@Transient
	public List<CsnCategoryJsonDto> toJsonFormat(){
		List<CsnCategoryJsonDto> list = new ArrayList<CsnCategoryJsonDto>();
		if(CollectionUtils.isNotEmpty(children)){
			for(CsnCategory category: children){
				list.add(category.toJsonDto());
			}
		}
		return list;
	}
	
	@Transient
	public CsnCategoryJsonDto toJsonDto(){
		CsnCategoryJsonDto json = new CsnCategoryJsonDto();
		json.setName(getName());
		json.setId(getId());
		json.setSearchCode(getSearchCode());
		json.setCode(getCode());
		return json;
	}
	
	
	
}
