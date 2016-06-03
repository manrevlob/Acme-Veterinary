package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "code")})
public class Voucher extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Voucher() {
		super();
	}

	// Attributes ---------------------------------------------------------

	private String code;
	private Double value;
	private Date validUntil;
	private Double minimumOrder;
	private Boolean isDeleted;
	
	
	@NotBlank
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@NotNull
	@Min(0)
	@Digits(integer = 10, fraction = 2)
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getValidUntil() {
		return validUntil;
	}
	public void setValidUntil(Date validUntil) {
		this.validUntil = validUntil;
	}
	
	@NotNull
	@Min(0)
	@Digits(integer = 10, fraction = 2)
	public Double getMinimumOrder() {
		return minimumOrder;
	}
	public void setMinimumOrder(Double minimumOrder) {
		this.minimumOrder = minimumOrder;
	}

	
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	// Relationships ---------------------------------------------------------
	
	private Collection<Customer> customers;
	private Collection<Order> orders;

	@Valid
	@ManyToMany
	@JoinTable(name = "voucher_customer", joinColumns = @JoinColumn(name = "voucher_id"), inverseJoinColumns = @JoinColumn(name = "customer_id"))
	public Collection<Customer> getCustomers() {
		return customers;
	}
	public void setCustomers(Collection<Customer> customers) {
		this.customers = customers;
	}
	
	@Valid
	@OneToMany(mappedBy = "voucher")
	public Collection<Order> getOrders() {
		return orders;
	}
	public void setOrders(Collection<Order> orders) {
		this.orders = orders;
	}
	
	
	
}
