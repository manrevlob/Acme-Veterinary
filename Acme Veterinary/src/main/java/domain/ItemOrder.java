package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class ItemOrder extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public ItemOrder() {
		super();
	}

	// Attributes -----------------------------------------------------------

	private String name;
	private String sku;
	private Money price;
	private Integer quantity;
	private String status;

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Pattern(regexp = "^\\w{2}\\-\\w{4}?$")
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Money getPrice() {
		return price;
	}

	public void setPrice(Money price) {
		this.price = price;
	}

	@Min(0)
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Pattern(regexp = "^pending$|^accepted$|^rejected$")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// RelationsShips ---------------------------------------------------------

	private Order order;

	@Valid
	@ManyToOne(optional = false)
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}