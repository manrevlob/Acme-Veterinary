package services;

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
import domain.Item;
import domain.ShoppingCart;
import domain.ShoppingCartLine;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class ShoppingCartLineServiceTest extends AbstractTest {

	// Service under test ------------------------------------

	@Autowired
	private ShoppingCartLineService shoppingCartLineService;

	// Others services ---------------------------------------

	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private ItemService itemService;

	// Tests -------------------------------------------------

	// Comprobamos que podemos crear una linea del carro de la compra como cliente
	
	@Test
	public void testCreateAndSave() {
		int before;
		int after;
		Item item;
		Integer quantity;
		ShoppingCart shoppingCart;
		ShoppingCartLine shoppingCartLine;
		
		authenticate("customer1");

		before = shoppingCartLineService.findAll().size();

		// Obtenemos el Id del Item5
		item = itemService.findOne(18);
		quantity = 2;
		shoppingCart = shoppingCartService.findByCustomerPrincipal();

		shoppingCartLine = shoppingCartLineService.create(item, shoppingCart, quantity);
		shoppingCartLineService.save(shoppingCartLine);
		
		after = shoppingCartLineService.findAll().size();

		Assert.isTrue(before < after);

		unauthenticate();
	}
	
	// Test Negativos ----------------------------------------
	
	// Comprobamos que no podemos añadir lineas al carro con otro rol que no sea de cliente
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNeg1() {
		Item item;
		Integer quantity;
		ShoppingCart shoppingCart;

		authenticate("admin");

		// Obtenemos el Id del Item6
		item = itemService.findOne(19);
		quantity = 2;
		shoppingCart = shoppingCartService.findByCustomerPrincipal();

		shoppingCartLineService.create(item, shoppingCart, quantity);

		unauthenticate();
	}
	
}
