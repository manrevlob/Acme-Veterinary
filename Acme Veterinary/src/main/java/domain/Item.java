package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "sku"), @Index(columnList = "isDeleted") })
public class Item extends DomainEntity {

	// Constructors -----------------------------------------------------------
	public Item() {
		super();
	}

	// Identification ---------------------------------------------------------
	private String name;
	private String sku;
	private String description;
	private Money price;
	private String picture;
	private boolean isDeleted;

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

	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotNull
	public Money getPrice() {
		return price;
	}

	public void setPrice(Money price) {
		this.price = price;
	}

	@URL
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	// Relationships ---------------------------------------------------------
	private Category category;
	private Collection<ShoppingCartLine> shoppingCartLines;
	private Collection<Comment> comments;

	@Valid
	@ManyToOne(optional = false)
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Valid
	@OneToMany(mappedBy = "item")
	public Collection<ShoppingCartLine> getShoppingCartLines() {
		return shoppingCartLines;
	}

	public void setShoppingCartLines(
			Collection<ShoppingCartLine> shoppingCartLines) {
		this.shoppingCartLines = shoppingCartLines;
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
