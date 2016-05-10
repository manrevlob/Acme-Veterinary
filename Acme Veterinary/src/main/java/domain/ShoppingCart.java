package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class ShoppingCart extends DomainEntity {

	// Constructors -----------------------------------------------------------
	public ShoppingCart() {
		super();
	}

	// Identification ---------------------------------------------------------

	private String comment;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	// Relationships ---------------------------------------------------------
	private Customer customer;
	private Collection<ItemOrder> itemOrders;

	@Valid
	@OneToOne(optional = false)
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Valid
	@OneToMany(mappedBy = "shoppingCart")
	public Collection<ItemOrder> getItemOrders() {
		return itemOrders;
	}

	public void setItemOrders(Collection<ItemOrder> itemOrders) {
		this.itemOrders = itemOrders;
	}

}
