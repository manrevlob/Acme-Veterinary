package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class ShoppingCartLine extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public ShoppingCartLine() {
		super();
	}

	// Identification ---------------------------------------------------------
	private Integer quantity;

	@Min(0)
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	// Relationships ---------------------------------------------------------

	private ShoppingCart shoppingCart;
	private Item item;

	@Valid
	@ManyToOne(optional = false)
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Valid
	@ManyToOne(optional = false)
	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

}
