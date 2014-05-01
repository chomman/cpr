package sk.peterjurkovic.cpr.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import sk.peterjurkovic.cpr.utils.PriceUtils;

@Entity
@SequenceGenerator(name = "porta_order_item_seq", sequenceName = "porta_order_item_seq", initialValue = 1, allocationSize =1)
@Table(name="portal_order_has_items")
public class PortalOrderItem {
	
	private Long id;
	private PortalOrder portalOrder;
	private PortalProduct portalProduct;
	private BigDecimal price;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "porta_order_item_seq")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portal_order_id", nullable = false)
	public PortalOrder getPortalOrder() {
		return portalOrder;
	}
	
	public void setPortalOrder(PortalOrder portalOrder) {
		this.portalOrder = portalOrder;
	}
	
	@NotNull(message = "{error.protalProduct.empty}")
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portal_product_id", nullable = false)	
	public PortalProduct getPortalProduct() {
		return portalProduct;
	}
	
	public void setPortalProduct(PortalProduct portalProduct) {
		this.portalProduct = portalProduct;
	}
	
	@Range(min = 0, max = 100000, message = "{error.portalService.price.range}")
	@NotNull(message = "{error.portalService.price}")
	@Column(name = "price", nullable = false, precision = 6, scale = 2)
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@Transient
	public BigDecimal getPriceWithVat(){
		return PriceUtils.getPriceWithVat(price, portalOrder.getVat());
	}
	
	
}
