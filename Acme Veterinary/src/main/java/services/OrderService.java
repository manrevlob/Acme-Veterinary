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
import domain.CreditCard;
import domain.Customer;
import domain.Money;
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

	// Constructors -----------------------------------------------------------

	public OrderService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Order create() {
		Order result;
		result = new Order();
		return result;
	}

	public Order create(Customer customer, CreditCard creditCard,
			String address, String fullName, Boolean isCanceled, String ticker,
			Money totalPrice, String comment) {
		Assert.isTrue(actorService.isCustomer());
		Order result;
		result = new Order();
		result.setCustomer(customer);
		result.setComment(comment);
		result.setCreditCard(creditCard);
		result.setAddress(address);
		result.setFullName(fullName);
		result.setIsCanceled(isCanceled);
		result.setMoment(new Date(System.currentTimeMillis() - 1000));
		result.setTicker(ticker);
		result.setTotalPrice(totalPrice);
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
		Customer customer;
		Collection<Order> result;

		customer = customerService.findByPrincipal();
		result = orderRepository.findAllByCustomer(customer);

		return result;
	}

	public boolean canBeCanceled(Order order) {
		Assert.isTrue(actorService.isCustomer()
				|| actorService.isAdministrator());

		long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;
		Date currentTime = new Date(System.currentTimeMillis());

		boolean result = Math.abs(order.getMoment().getTime()
				- currentTime.getTime()) < MILLIS_PER_DAY;

		return result;
	}

	public void cancelOrder(Order order) {
		Assert.isTrue(actorService.isCustomer()
				|| actorService.isAdministrator());
		Assert.isTrue(canBeCanceled(order));

		order.setIsCanceled(true);
		orderRepository.save(order);
	}

	// Create order and their ItemOrders and delete ShoppingCart
	public Order createShoppingCartAndPlaceOrder(ShoppingCart shoppingCart,
			String fullName, String address, CreditCard creditCard) {
		Assert.isTrue(actorService.isCustomer());
		Assert.notNull(shoppingCart);
		Assert.notNull(fullName);
		Assert.notNull(address);
		Assert.notNull(creditCard);
		Utiles.checkCreditCard(creditCard);

		String ticker;
		Order result;

		ticker = generateTicker();
		result = create(shoppingCart.getCustomer(), creditCard, address,
				fullName, false, ticker,
				shoppingCartService.calculateTotalPrice(),
				shoppingCart.getComment());
		result.setMoment(new Date(System.currentTimeMillis() - 100));
		result = save(result);

		itemOrderService.importShoppingCartLines(shoppingCart, result);
		shoppingCartService.delete(shoppingCart);

		return result;
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
