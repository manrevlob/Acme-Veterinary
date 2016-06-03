package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "registerMoment")})
public class Customer extends Actor {

	// Constructors -----------------------------------------------------------

	public Customer() {
		super();
	}

	// Identification ---------------------------------------------------------

	private Date registerMoment;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getRegisterMoment() {
		return registerMoment;
	}

	public void setRegisterMoment(Date registerMoment) {
		this.registerMoment = registerMoment;
	}

	// RelationsShips --------------------------------------------------------

	private Collection<Order> orders;
	private Collection<Voucher> vouchers;
	private ShoppingCart shoppingCart;
	private Collection<Pet> pets;
	private Collection<Comment> comments;

	@Valid
	@OneToMany(mappedBy = "customer")
	public Collection<Order> getOrders() {
		return orders;
	}

	public void setOrders(Collection<Order> orders) {
		this.orders = orders;
	}

	@Valid
	@ManyToMany
	@JoinTable(name = "customer_voucher", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "voucher_id"))
	public Collection<Voucher> getVouchers() {
		return vouchers;
	}

	public void setVouchers(Collection<Voucher> vouchers) {
		this.vouchers = vouchers;
	}

	@Valid
	@OneToOne(optional = true)
	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	@Valid
	@OneToMany(mappedBy = "customer")
	public Collection<Pet> getPets() {
		return pets;
	}

	public void setPets(Collection<Pet> pets) {
		this.pets = pets;
	}

	@Valid
	@OneToMany()
	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

}
