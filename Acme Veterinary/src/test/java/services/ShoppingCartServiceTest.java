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
import domain.Customer;
import domain.Item;
import domain.ShoppingCart;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class ShoppingCartServiceTest extends AbstractTest {

	// Service under test ------------------------------------

	@Autowired
	private ShoppingCartService shoppingCartService;

	// Other services ------------------------------------

	@Autowired
	private CustomerService customerService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private ShoppingCartLineService shoppingCartLineService;

	// Tests -------------------------------------------------

	// Comprobamos que podemos crear un carro de la compra como customer y un
	// comentario

	@Test
	public void testCreateAndSave() {
		Collection<ShoppingCart> before;
		Collection<ShoppingCart> after;

		before = shoppingCartService.findAll();

		ShoppingCart shoppingCart;
		Customer customer;
		String comment;

		authenticate("customer2");

		customer = customerService.findByPrincipal();
		comment = "commentTest";
		shoppingCart = shoppingCartService.create(customer, comment);
		shoppingCart = shoppingCartService.save(shoppingCart);

		after = shoppingCartService.findAll();

		Assert.isTrue(before.size() < after.size());

		unauthenticate();
	}

	// Comprobamos que podemos añadir un item al carro de la compra siendo
	// cliente

	@Test
	public void testAddItemShoppingCart() {
		int before;
		int after;
		Item item;
		Integer quantity;

		authenticate("customer1");

		before = shoppingCartLineService.findAll().size();

		// Obtenemos el Id del Item6
		item = itemService.findOne(19);
		quantity = 2;
		shoppingCartService.addItem(item, quantity);

		after = shoppingCartLineService.findAll().size();

		Assert.isTrue(before < after);

		unauthenticate();
	}

	// Comprobamos que podemos modificar la cantidad de un articulo del carro de
	// la compra

	@Test
	public void testModifyQuantityOfItem() {
		Integer before;
		Integer after;
		Item item;
		Integer quantity;
		ShoppingCart shoppingCart;

		authenticate("customer1");

		// Obtenemos el item 2 que el cliente1 tiene ya articulos en el carrito
		item = itemService.findOne(15);
		quantity = 5;

		shoppingCart = shoppingCartService.findByCustomerPrincipal();
		before = shoppingCartLineService.findByShoppingCartAndItem(
				shoppingCart, item).getQuantity();

		shoppingCartService.modifyQuantityOfItem(item, quantity);

		after = shoppingCartLineService.findByShoppingCartAndItem(shoppingCart,
				item).getQuantity();

		Assert.isTrue(before < after);

	}

	// Comprobamos que podemos eliminar un articulo del carro de la compra
	// siendo el propietario del carro

	@Test
	public void testDeleteItem() {
		int before;
		int after;
		Item item;

		authenticate("customer1");

		before = shoppingCartLineService.findAll().size();

		// Obtenemos el item 2 que el cliente1 tiene ya articulos en el carrito
		item = itemService.findOne(15);

		shoppingCartService.deleteItem(item);

		after = shoppingCartLineService.findAll().size();

		Assert.isTrue(before > after);

		unauthenticate();
	}

	// Comprobamos que podemos añadir un comentario al carro de la compra siendo
	// el propietario

	@Test
	public void testAddComment() {
		String before;
		String after;
		String comment;

		authenticate("customer1");

		before = shoppingCartService.findByCustomerPrincipal().getComment();

		comment = "commentTest";
		shoppingCartService.addComment(comment);

		after = shoppingCartService.findByCustomerPrincipal().getComment();

		Assert.isTrue(before.compareTo(after) != 0);

		unauthenticate();
	}

	// Test Negativos ----------------------------------------

	// Comprobamos que no podemos crear un carro de la compra siendo otro rol
	// que no sea cliente
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNeg1() {
		ShoppingCart shoppingCart;
		Customer customer;
		String comment;

		authenticate("admin");

		customer = customerService.findByPrincipal();
		comment = "commentTest";
		shoppingCart = shoppingCartService.create(customer, comment);
		shoppingCart = shoppingCartService.save(shoppingCart);

		unauthenticate();
	}

	// Comprobamos que no podemos añadir un articulo en el carro de la compra
	// sino eres cliente
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testAddItemShoppingCartNeg1() {
		Item item;
		Integer quantity;

		authenticate("admin");

		// Obtenemos el Id del Item6
		item = itemService.findOne(19);
		quantity = 2;
		shoppingCartService.addItem(item, quantity);

		unauthenticate();
	}

	// Comprobamos que no podemos añadir un articulo en el carro de la compra
	// sin decirle que articulo introducir
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testAddItemShoppingCartNeg2() {
		Item item = null;
		Integer quantity = 100;

		authenticate("customer1");

		// Obtenemos el Id del Item6
		shoppingCartService.addItem(item, quantity);

		unauthenticate();
	}

	// Comprobamos que no podemos modificar una cantidad de un articulo del
	// carro de la compra sin decirle que articulo modificar
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testModifyQuantityOfItemNeg1() {
		Item item = null;
		Integer quantity = 10000;

		authenticate("customer1");

		shoppingCartService.modifyQuantityOfItem(item, quantity);

	}

	// Comprobamos que no podemos eliminar un articulo de un carro de la compra
	// sino eres propietario del carro
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteItemNeg() {
		Item item;

		authenticate("customer2");

		// Obtenemos el item 2 que el cliente1 tiene ya articulos en el carrito
		item = itemService.findOne(15);

		shoppingCartService.deleteItem(item);

		unauthenticate();
	}

	// Comprobamos que no podemos añadir un comentario sino eres cliente.
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testAddCommentNeg() {
		String comment;

		authenticate("admin");

		comment = "commentTest";
		shoppingCartService.addComment(comment);

		unauthenticate();
	}

}
