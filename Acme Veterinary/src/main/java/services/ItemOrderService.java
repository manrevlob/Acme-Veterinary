package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ItemOrderRepository;
import domain.ItemOrder;
import domain.Order;
import domain.ShoppingCart;
import domain.ShoppingCartLine;

@Service
@Transactional
public class ItemOrderService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ItemOrderRepository itemOrderRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService actorService;
	@Autowired
	private ShoppingCartLineService shoppingCartLineService;

	// Constructors -----------------------------------------------------------

	public ItemOrderService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public ItemOrder create() {
		ItemOrder result;
		result = new ItemOrder();
		return result;
	}

	public ItemOrder create(ShoppingCartLine shoppingCarLine, Order order) {
		Assert.isTrue(actorService.isCustomer());
		Assert.notNull(shoppingCarLine);
		Assert.notNull(order);

		ItemOrder result;
		result = new ItemOrder();
		result.setOrder(order);
		result.setName(shoppingCarLine.getItem().getName());
		result.setSku(shoppingCarLine.getItem().getSku());
		result.setPrice(shoppingCarLine.getItem().getPrice());
		result.setQuantity(shoppingCarLine.getQuantity());
		return result;
	}

	public ItemOrder findOne(int itemOrderId) {
		ItemOrder result;
		result = itemOrderRepository.findOne(itemOrderId);
		return result;
	}

	public Collection<ItemOrder> findAll() {
		Collection<ItemOrder> result;
		result = itemOrderRepository.findAll();
		return result;
	}

	public ItemOrder save(ItemOrder itemOrder) {
		Assert.notNull(itemOrder);
		return itemOrderRepository.save(itemOrder);
	}

	public void delete(ItemOrder itemOrder) {
		itemOrderRepository.delete(itemOrder);
	}

	// Other business methods -------------------------------------------------

	public Collection<ItemOrder> findByOrder(int orderId) {
		Assert.isTrue(actorService.isCustomer());
		Collection<ItemOrder> result;
		result = itemOrderRepository.findByOrder(orderId);
		return result;
	}

	// Change shoppingCartLines to ItemOrders and save
	public Collection<ItemOrder> importShoppingCartLines(
			ShoppingCart shoppingCart, Order order) {
		Assert.isTrue(actorService.isCustomer());
		Assert.notNull(shoppingCart);
		Assert.notNull(order);

		ItemOrder itemOrder;
		Collection<ItemOrder> result;
		result = new ArrayList<>();
		Collection<ShoppingCartLine> shoppingCartLines;

		shoppingCartLines = shoppingCartLineService
				.findByShoppingCart(shoppingCart);

		for (ShoppingCartLine shoppingCartLine : shoppingCartLines) {
			itemOrder = create(shoppingCartLine, order);
			result.add(itemOrder);
			save(itemOrder);
		}
		return result;
	}
}
