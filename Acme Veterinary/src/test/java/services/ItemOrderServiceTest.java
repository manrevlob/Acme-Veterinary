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
import domain.ItemOrder;
import domain.Order;
import domain.ShoppingCart;
import domain.ShoppingCartLine;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class ItemOrderServiceTest extends AbstractTest {

	// Service Supported ------------------------------------

	@Autowired
	private ItemOrderService itemOrderService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ShoppingCartLineService shoppingCartLineService;
	@Autowired
	private ShoppingCartService shoppingCartService;

	// Tests -------------------------------------------------

	// Comprobamos que podemos crear una linea del pedido como customer
	@Test
	public void testCreateAndSave() {
		Collection<ItemOrder> before;
		Collection<ItemOrder> after;
		ItemOrder itemOrder;
		ShoppingCartLine shoppingCartLine;
		Order order;

		authenticate("customer1");

		// Obtenemos la Id de la shoppingCartLine1
		int shoppingCartLineId = 59;
		shoppingCartLine = shoppingCartLineService.findOne(shoppingCartLineId);

		// Obtenemos la Id de la Order
		int orderId = 77;
		order = orderService.findOne(orderId);

		before = itemOrderService.findAll();

		itemOrder = itemOrderService.create(shoppingCartLine, order);
		itemOrderService.save(itemOrder);

		after = itemOrderService.findAll();

		Assert.isTrue(before.size() < after.size());

		unauthenticate();
	}

	// Comprobamos que podemos importar todas las lineas del carro de la compra
	// de un customer a un pedido
	@Test
	public void testImportAllShoppingCartLines(){
		Collection<ItemOrder> before;
		Collection<ItemOrder> after;
		ShoppingCart shoppingCart;
		Order order;

		authenticate("customer1");

		// Obtenemos la Id de la shoppingCart1
		int shoppingCartId = 58;
		shoppingCart = shoppingCartService.findOne(shoppingCartId);

		// Obtenemos la Id de la Order
		int orderId = 77;
		order = orderService.findOne(orderId);

		before = itemOrderService.findAll();

		itemOrderService.importShoppingCartLines(shoppingCart, order);
			
		after = itemOrderService.findAll();
		
		Assert.isTrue(before.size() < after.size());

		unauthenticate();
	}

	// Test Negativos ----------------------------------------

	// Comprobamos que no podemos crear una linea de pedido con otro rol
	// distinto a customer
	
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNeg() {
		ItemOrder itemOrder;
		ShoppingCartLine shoppingCartLine;
		Order order;

		authenticate("admin");

		// Obtenemos la Id de la shoppingCartLine1
		int shoppingCartLineId = 59;
		shoppingCartLine = shoppingCartLineService.findOne(shoppingCartLineId);

		// Obtenemos la Id de la Order
		int orderId = 77;
		order = orderService.findOne(orderId);

		itemOrder = itemOrderService.create(shoppingCartLine, order);
		itemOrderService.save(itemOrder);

		unauthenticate();
	}
	
	// Comprobamos que no podemos importar todas las lineas de un carro con un
	// rol distinto a customer
	
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testImportAllShoppingCartLinesNeg(){
		ShoppingCart shoppingCart;
		Order order;

		authenticate("admin");

		// Obtenemos la Id de la shoppingCart1
		int shoppingCartId = 58;
		shoppingCart = shoppingCartService.findOne(shoppingCartId);

		// Obtenemos la Id de la Order
		int orderId = 77;
		order = orderService.findOne(orderId);

		itemOrderService.importShoppingCartLines(shoppingCart, order);

		unauthenticate();
	}

	// Comprobamos que no podemos importar todas las lineas de un carro de un
	// carro que no es propietario del mismo
	
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testImportAllShoppingCartLinesNeg2(){
		ShoppingCart shoppingCart;
		Order order;

		authenticate("customer2");

		// Obtenemos la Id de la shoppingCart1 perteneciente al customer1
		int shoppingCartId = 58;
		shoppingCart = shoppingCartService.findOne(shoppingCartId);

		// Obtenemos la Id de la Order
		int orderId = 77;
		order = orderService.findOne(orderId);

		itemOrderService.importShoppingCartLines(shoppingCart, order);

		unauthenticate();
	}

}