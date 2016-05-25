package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ShoppingCartRepository;
import domain.Customer;
import domain.Item;
import domain.Money;
import domain.ShoppingCart;
import domain.ShoppingCartLine;

@Service
@Transactional
public class ShoppingCartService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ShoppingCartRepository shoppingCartRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ShoppingCartLineService shoppingCartLineService;
	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------

	public ShoppingCartService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public ShoppingCart create() {
		Assert.isTrue(actorService.isCustomer());
		ShoppingCart result;
		result = new ShoppingCart();
		return result;
	}

	public ShoppingCart create(Customer customer, String comment) {
		Assert.isTrue(actorService.isCustomer());
		ShoppingCart result;
		result = new ShoppingCart();
		result.setCustomer(customer);
		result.setComment(comment);
		return result;
	}

	public ShoppingCart save(ShoppingCart shoppingCart) {
		Assert.isTrue(actorService.isCustomer());
		Assert.notNull(shoppingCart);
		return shoppingCartRepository.save(shoppingCart);
	}

	public void delete(ShoppingCart shoppingCart) {
		Assert.notNull(shoppingCart);
		Collection<ShoppingCartLine> shoppingCartLines = shoppingCartLineService
				.findByShoppingCart(shoppingCart);
		for (ShoppingCartLine shoppingCartLine : shoppingCartLines) {
			shoppingCartLineService.delete(shoppingCartLine);
		}
		shoppingCartRepository.delete(shoppingCart);
	}

	public Collection<ShoppingCart> findAll() {
		Collection<ShoppingCart> result;
		result = shoppingCartRepository.findAll();
		return result;
	}

	public ShoppingCart findOne(int shoppingCartId) {
		Assert.isTrue(shoppingCartId != 0);
		ShoppingCart result;
		result = shoppingCartRepository.findOne(shoppingCartId);
		Assert.notNull(result);
		Assert.isTrue(customerService.findByPrincipal().getId() == result.getCustomer().getId());
		return result;
	}

	// Other business methods
	public ShoppingCart findByCustomerPrincipal() {
		Assert.isTrue(actorService.isCustomer());
		Customer customer;
		customer = customerService.findByPrincipal();
		ShoppingCart result;
		result = shoppingCartRepository.findByCustomer(customer);
		if (result == null) {
			result = create(customer, "");
			result = save(result);
		}
		return result;
	}

	public ShoppingCartLine addItem(Item item, Integer quantity) {
		Assert.isTrue(actorService.isCustomer());
		Assert.notNull(item);
		Assert.notNull(quantity);
		ShoppingCart shoppingCart;
		ShoppingCartLine result;

		shoppingCart = findByCustomerPrincipal();
		result = shoppingCartLineService.findByShoppingCartAndItem(
				shoppingCart, item);

		if (result != null) {
			result.setQuantity(result.getQuantity() + quantity);
		} else {
			result = shoppingCartLineService.create(item, shoppingCart,
					quantity);
		}

		result = shoppingCartLineService.save(result);
		return result;
	}

	public ShoppingCartLine modifyQuantityOfItem(Item item, Integer quantity) {
		Assert.isTrue(actorService.isCustomer());
		Assert.notNull(item);
		Assert.notNull(quantity);
		ShoppingCartLine result;
		ShoppingCart shoppingCart;
		shoppingCart = findByCustomerPrincipal();
		result = shoppingCartLineService.findByShoppingCartAndItem(
				shoppingCart, item);
		Assert.notNull(result);
		result.setQuantity(quantity);
		result = shoppingCartLineService.save(result);
		return result;
	}

	public ShoppingCartLine deleteItem(Item item) {
		Assert.isTrue(actorService.isCustomer());
		Assert.notNull(item);
		ShoppingCartLine shoppingCartLine;
		ShoppingCart shoppingCart;
		shoppingCart = findByCustomerPrincipal();
		shoppingCartLine = shoppingCartLineService.findByShoppingCartAndItem(
				shoppingCart, item);
		Assert.notNull(shoppingCartLine);
		shoppingCartLineService.delete(shoppingCartLine);
		return shoppingCartLine;
	}

	public ShoppingCart addComment(String comment) {
		Assert.isTrue(actorService.isCustomer());
		Assert.notNull(comment);
		ShoppingCart result;
		result = findByCustomerPrincipal();
		result.setComment(comment);
		result = save(result);
		return result;
	}

	public ShoppingCart deleteComment() {
		Assert.isTrue(actorService.isCustomer());
		ShoppingCart result;
		result = findByCustomerPrincipal();
		result.setComment(null);
		result = save(result);
		return result;
	}

	public Money calculateTotalPrice() {
		Assert.isTrue(actorService.isCustomer());
		Money result = new Money();
		ShoppingCart shoppingCart;
		Double amount;
		shoppingCart = findByCustomerPrincipal();
		amount = shoppingCartRepository.calculateTotalPrice(shoppingCart);
		result.setAmount(amount);
		result.setCurrency("Euro");
		return result;
	}
}
