package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Message;
import domain.Order;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class OrderServiceTest extends AbstractTest {

	// Service Supported ------------------------------------

	@Autowired
	private OrderService orderService;
	@Autowired
	private MessageService messageService;

	// Tests -------------------------------------------------

	// Comprobamos que podemos crear un pedido estando logueados como customer
	@Test
	public void testCreateAndSave() {
		Collection<Order> before;
		Collection<Order> after;
		Order order;
		CreditCard creditCard;

		authenticate("customer1");

		before = orderService.findAll();

		order = orderService.create();
		order.setAddress("TEST");
		order.setFullName("TEST");
		// Create CreditCard
		creditCard = new CreditCard();
		creditCard.setBrand("test");
		creditCard.setCvv(111);
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2020);
		creditCard.setName("test");
		creditCard.setNumber("5207537760582875");
		order.setCreditCard(creditCard);
		orderService.createShoppingCartAndPlaceOrder(order);

		after = orderService.findAll();

		Assert.isTrue(before.size() < after.size());

		unauthenticate();
	}

	// Comprobamos que podemos cancelar un pedido como customer antes de 1 dia
	@Test
	public void testCancelCustomer() {
		Order order;
		CreditCard creditCard;

		authenticate("customer1");

		// Nos creamos la order porque todas las que estan en la BBDD tienen
		// mucho tiempo de creacion
		order = orderService.create();
		order.setAddress("TEST");
		order.setFullName("TEST");
		// Create CreditCard
		creditCard = new CreditCard();
		creditCard.setBrand("test");
		creditCard.setCvv(111);
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2020);
		creditCard.setName("test");
		creditCard.setNumber("5207537760582875");
		order.setCreditCard(creditCard);
		order = orderService.createShoppingCartAndPlaceOrder(order);

		orderService.cancelOrderCustomer(order);

		Assert.isTrue(order.getIsCanceled());

		unauthenticate();
	}

	// Comprobamos que podemos cancelar un pedido cuando se desee siendo
	// administrador y éste le enviara al customer una notificacion de
	// cancelacion
	@Test
	public void testCancelAdministrator() {
		Order order;
		Collection<Message> before;
		Collection<Message> after;

		// Obtenemos todos los mensajes del customer1
		authenticate("customer1");
		before = messageService.findAllMine();
		unauthenticate();

		authenticate("admin");
		// Obtenemos la Id de la order1
		int orderId = 77;
		order = orderService.findOne(orderId);

		orderService.cancelOrderAdminstrator(order);

		unauthenticate();

		// Comprobamos que la order se ha cancelado
		Assert.isTrue(order.getIsCanceled());

		// Comprobamos que el customer1 que es el dueño de la order1 ha recibido
		// un correo por la cancelacion
		authenticate("customer1");
		after = messageService.findAllMine();

		Assert.isTrue(before.size() < after.size());
		unauthenticate();
	}

	// Test Negativos ----------------------------------------

	// Comprobamos que no podemos crear un pedido con otro rol que no sea
	// customer
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNeg() {
		Order order;
		CreditCard creditCard;

		authenticate("admin");

		order = orderService.create();
		order.setAddress("TEST");
		order.setFullName("TEST");
		// Create CreditCard
		creditCard = new CreditCard();
		creditCard.setBrand("test");
		creditCard.setCvv(111);
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2020);
		creditCard.setName("test");
		creditCard.setNumber("5207537760582875");
		order.setCreditCard(creditCard);
		orderService.createShoppingCartAndPlaceOrder(order);

		unauthenticate();
	}

	// Comprobamos que no podemos cancelar un pedido con un rol que no sea
	// customer o administrator
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testCancelAdministratorNeg() {
		Order order;

		unauthenticate();
		// Obtenemos la Id de la order1
		int orderId = 77;
		order = orderService.findOne(orderId);

		orderService.cancelOrderAdminstrator(order);

	}

	// Comprobamos que no podemos cancelar un pedido como customer que supere un
	// dia
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testCancelCustomerNeg() {
		Order order;

		authenticate("customer1");

		// Obtenemos la Id de la order1
		int orderId = 77;
		order = orderService.findOne(orderId);

		orderService.cancelOrderCustomer(order);

		unauthenticate();
	}

}