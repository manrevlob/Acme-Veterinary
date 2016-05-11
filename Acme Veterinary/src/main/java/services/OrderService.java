package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.OrderRepository;
import domain.Order;

@Service
@Transactional
public class OrderService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private OrderRepository orderRepository;

	// Supporting services ----------------------------------------------------

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
}
