package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ShoppingCartLineRepository;
import domain.Item;
import domain.Money;
import domain.ShoppingCart;
import domain.ShoppingCartLine;

@Service
@Transactional
public class ShoppingCartLineService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ShoppingCartLineRepository shoppingCartLineRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------

	public ShoppingCartLineService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public ShoppingCartLine create() {
		Assert.isTrue(actorService.isCustomer());
		ShoppingCartLine result;
		result = new ShoppingCartLine();
		return result;
	}

	public ShoppingCartLine create(Item item, ShoppingCart shoppingCart,
			Integer quantity) {
		Assert.isTrue(actorService.isCustomer());
		ShoppingCartLine result;
		Double amount;
		Double price;

		result = create();
		result.setItem(item);
		result.setShoppingCart(shoppingCart);
		result.setQuantity(quantity);

		price = item.getPrice().getAmount();

		Money money = new Money();
		money.setCurrency(item.getPrice().getCurrency());

		// Price is (((tax / 100) * price) + price) * quantity
		amount = price * quantity;
		money.setAmount(amount);
		result.setPrice(money);
		return result;
	}

	public ShoppingCartLine save(ShoppingCartLine shoppingCartLine) {
		Assert.isTrue(actorService.isCustomer());
		Assert.notNull(shoppingCartLine);
		return shoppingCartLineRepository.save(shoppingCartLine);
	}

	public void delete(ShoppingCartLine shoppingCartLine) {
		Assert.notNull(shoppingCartLine);
		shoppingCartLineRepository.delete(shoppingCartLine);
	}

	public Collection<ShoppingCartLine> findAll() {
		Assert.isTrue(actorService.isCustomer());
		Collection<ShoppingCartLine> result;
		result = shoppingCartLineRepository.findAll();
		return result;
	}

	public ShoppingCartLine findOne(int shoppingCartLineId) {
		Assert.isTrue(actorService.isCustomer());
		Assert.isTrue(shoppingCartLineId != 0);
		ShoppingCartLine result;
		result = shoppingCartLineRepository.findOne(shoppingCartLineId);
		Assert.notNull(result);
		return result;
	}

	// Other business methods

	public ShoppingCartLine findByShoppingCartAndItem(
			ShoppingCart shoppingCart, Item item) {
		Assert.isTrue(actorService.isCustomer());
		Assert.notNull(shoppingCart);
		Assert.notNull(item);
		ShoppingCartLine result;
		result = shoppingCartLineRepository.findByShoppingCartAndItem(
				shoppingCart, item);
		return result;
	}

	public Collection<ShoppingCartLine> findByShoppingCart(
			ShoppingCart shoppingCart) {
		Collection<ShoppingCartLine> result;
		result = shoppingCartLineRepository.findByShoppingCart(shoppingCart);
		return result;
	}

	// Metodo que elimina un item de un shoppingCart si el administrador elimina un item de la tienda
	public void checkShoppingCart(Item item) {
		Assert.isTrue(actorService.isAdministrator());
		Collection<ShoppingCartLine> lines;
		lines = shoppingCartLineRepository.findAllByItem(item);
		for(ShoppingCartLine s: lines){
			delete(s);
		}
	}
}
