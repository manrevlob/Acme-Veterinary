package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.OrderRepository;
import utilities.Utiles;
import domain.Customer;
import domain.Order;
import domain.ShoppingCart;

@Service
@Transactional
public class OrderService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private OrderRepository orderRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService actorService;
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private ItemOrderService itemOrderService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private MessageService messageService;

	// Constructors -----------------------------------------------------------

	public OrderService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Order create() {
		Assert.isTrue(actorService.isCustomer());
		Order result;
		result = new Order();
		ShoppingCart shoppingCart;
		shoppingCart = shoppingCartService.findByCustomerPrincipal();

		result.setComment(shoppingCart.getComment());
		result.setCustomer(customerService.findByPrincipal());
		result.setIsCanceled(false);
		result.setMoment(new Date(System.currentTimeMillis() - 100));
		result.setTicker(generateTicker());
		result.setTotalPrice(shoppingCartService.calculateTotalPrice());
		return result;
	}

	public Order findOne(int orderId) {
		Order result;
		result = orderRepository.findOne(orderId);
		return result;
	}

	public Collection<Order> findAll() {
		Collection<Order> result;
		result = orderRepository.findAll();
		return result;
	}

	public Order save(Order order) {
		Assert.notNull(order);
		return orderRepository.save(order);
	}

	public void delete(Order order) {
		orderRepository.delete(order);
	}

	// Other business methods -------------------------------------------------

	public Collection<Order> findAllByCustomer() {
		Assert.isTrue(actorService.isCustomer());
		Customer customer;
		Collection<Order> result;

		customer = customerService.findByPrincipal();
		result = orderRepository.findAllByCustomer(customer);

		return result;
	}

	public Collection<Order> findAllNotCanceled() {
		Assert.isTrue(actorService.isAdministrator());
		Collection<Order> result;
		result = orderRepository.findOrdersNotCanceled();
		return result;
	}

	public boolean canBeCanceled(Order order) {
		Assert.isTrue(actorService.isCustomer());

		long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;
		Date currentTime = new Date(System.currentTimeMillis());

		boolean result = Math.abs(order.getMoment().getTime()
				- currentTime.getTime()) < MILLIS_PER_DAY;

		return result;
	}

	public void cancelOrderCustomer(Order order) {
		Assert.isTrue(actorService.isCustomer());
		Assert.isTrue(canBeCanceled(order));

		order.setIsCanceled(true);
		orderRepository.save(order);
	}

	public void cancelOrderAdminstrator(Order order) {
		Assert.isTrue(actorService.isAdministrator());
		order.setIsCanceled(true);
		orderRepository.save(order);
		messageService.sendCancelOrderMessage(order);
	}

	// Create order and their ItemOrders and delete ShoppingCart
	public Order createShoppingCartAndPlaceOrder(Order order) {
		Assert.isTrue(actorService.isCustomer());
		Assert.notNull(order);
		Assert.notNull(order.getFullName());
		Assert.notNull(order.getAddress());
		Assert.notNull(order.getCreditCard());
		Utiles.checkCreditCard(order.getCreditCard());

		ShoppingCart shoppingCart = shoppingCartService
				.findByCustomerPrincipal();

		order.setTotalPrice(shoppingCartService.calculateTotalPrice());
		order = save(order);
		Customer customer = customerService.findByPrincipal();
		Collection<Order> orders = customer.getOrders();
		orders.add(order);
		customer.setOrders(orders);

		itemOrderService.importShoppingCartLines(shoppingCart, order);
		shoppingCartService.delete(shoppingCart);

		return order;
	}

	public String generateTicker() {
		String result;
		Random random = new Random();
		DateFormat date = new SimpleDateFormat("ddMMyy");
		Collection<String> orders;

		result = date.format(new Date()) + "-";
		orders = orderRepository.findAllTicker();

		for (int i = 0; i <= 100; i++) {
			char c = (char) random.nextInt(255);
			if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z'))
				result += c;
			if (result.length() == 11)
				break;
		}

		if (orders.contains(result)) {
			generateTicker();
		}

		return result;
	}
}
