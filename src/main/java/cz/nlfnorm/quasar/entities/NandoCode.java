package cz.nlfnorm.quasar.entities;

import java.util.HashSet;
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

import org.hibernate.annotations.OrderBy;
import org.hibernate.validator.constraints.Length;

/**
 * 
 * Entity of Quality system assesmet reporting system, represents NANDO code
 *
 * @author Peter Jurkovic
 * @date Jun 6, 2014
 */
@Entity
@SequenceGenerator(name = "quasar_nando_code_id_seq", sequenceName = "quasar_nando_code_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "quasar_nando_code")
public class NandoCode extends BaseEntity{
	
	private static final long serialVersionUID = 1388976314109073881L;
	
	private String specificationCzech;
	private String specificationEnglish;
	private Set<NandoCode> children;
	private NandoCode parent;
	private Integer order;
	
	private boolean forProductAssesorA;
	private boolean forProductAssesorR;
	private boolean forProductSpecialist;
	
	public NandoCode(){
		this(null);
	}
	
	public NandoCode(NandoCode parent){
		this.parent = parent;
		this.children = new HashSet<>();
		this.order = 0;
		if(parent != null){
			registerInParentsChilds();
		}
	}
	
	@Id
	@Override
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quasar_nando_code_id_seq")
	public Long getId() {
		return super.getId();
	}
	
	@Length(max = 200, message = "{error.nandoCode.specification}")
	@Column( name = "specification_czech", length = 200 )
	public String getSpecificationCzech() {
		return specificationCzech;
	}
	public void setSpecificationCzech(String specificationCzech) {
		this.specificationCzech = specificationCzech;
	}
	
	@Length(max = 200, message = "{error.nandoCode.specification}")
	@Column( name = "specification_english", length = 200 )
	public String getSpecificationEnglish() {
		return specificationEnglish;
	}
	public void setSpecificationEnglish(String specificationEnglish) {
		this.specificationEnglish = specificationEnglish;
	}
	
	/**
	 * Second level NANDO code
	 * @return set of second level NANDO codes of this first level
	 */
	@OrderBy(clause = "order" )
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<NandoCode> getChildren() {
		return children;
	}
	public void setChildren(Set<NandoCode> children) {
		this.children = children;
	}
	
	@ManyToOne(fetch=FetchType.LAZY) 
	@JoinColumn(name = "parent_id") 
	public NandoCode getParent() {
		return parent;
	}
	public void setParent(NandoCode parent) {
		this.parent = parent;
	}
		
	@Column(name = "nando_order")
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
	
	@Column(name = "is_for_product_assesor_a")
	public boolean isForProductAssesorA() {
		return forProductAssesorA;
	}

	public void setForProductAssesorA(boolean forProductAssesorA) {
		this.forProductAssesorA = forProductAssesorA;
	}

	@Column(name = "is_for_product_assesor_r")
	public boolean isForProductAssesorR() {
		return forProductAssesorR;
	}

	public void setForProductAssesorR(boolean forProductAssesorR) {
		this.forProductAssesorR = forProductAssesorR;
	}

	@Column(name = "is_for_product_specialist")
	public boolean isForProductSpecialist() {
		return forProductSpecialist;
	}

	public void setForProductSpecialist(boolean forProductSpecialist) {
		this.forProductSpecialist = forProductSpecialist;
	}

	private void registerInParentsChilds() {
		this.parent.children.add(this);
	}

}
