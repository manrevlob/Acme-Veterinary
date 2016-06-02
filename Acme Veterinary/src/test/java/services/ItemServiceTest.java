package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;
import domain.Item;
import domain.Money;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class ItemServiceTest extends AbstractTest {

	// Service under test ------------------------------------

	@Autowired
	private ItemService itemService;
	@Autowired
	private CategoryService categoryService;

	// Tests -------------------------------------------------

	// Comprobamos que podemos crear un articulo siendo administrador

	@Test
	public void testCreateAndSave() {
		Collection<Item> before;
		Collection<Item> after;

		before = itemService.findAllNoDeleted();

		authenticate("admin");
		Item item = itemService.create();
		item.setName("Item7Name");
		item.setSku("07-AAAA");
		item.setDescription("Item7Description");

		Money price = new Money();
		price.setAmount(12.5);
		price.setCurrency("Euro");
		item.setPrice(price);

		// Obtenemos la Id de la categoria1
		Category category = categoryService.findOne(16);
		item.setCategory(category);

		item.setIsDeleted(false);

		itemService.save(item);

		after = itemService.findAllNoDeleted();

		Assert.isTrue(before.size() < after.size());

		unauthenticate();
	}

	// Con este metodo comprobamos que encontramos un item segun su ID y ademas
	// lo borramos.

	@Test
	public void testDelete() {
		Collection<Item> before;
		Collection<Item> after;

		before = itemService.findAllNoDeleted();

		authenticate("admin");

		// Obtenemos el Id del item1
		Item item = itemService.findOne(20);
		itemService.delete(item);

		after = itemService.findAllNoDeleted();

		Assert.isTrue(before.size() > after.size());

		unauthenticate();

	}

	// Comprobamos que con este metodo buscamos un item segun su SKU, nombre o
	// descripcion
	@Test
	public void testFindByKeyword() {
		unauthenticate();

		Collection<Item> items = itemService.findByKeyword("Item1");

		Assert.isTrue(items.size() == 1);
	}

	// Test Negativos ----------------------------------------

	// Comprobamos que no podemos crear un item con un SKU ya existente
	@Rollback(true)
	@Test(expected = DataIntegrityViolationException.class)
	public void testCreateAndSaveNeg1() {

		authenticate("admin");
		Item item = itemService.create();
		item.setName("Item7Name");
		item.setSku("01-ABCD");
		item.setDescription("Item7Description");

		Money price = new Money();
		price.setAmount(12.5);
		price.setCurrency("Euro");
		item.setPrice(price);

		// Obtenemos la Id de la categoria1
		Category category = categoryService.findOne(16);
		item.setCategory(category);

		item.setIsDeleted(false);

		itemService.save(item);

		unauthenticate();
	}

	// Comprobamos que no podemos crear un item con un precio igual a cero
	@Rollback(true)
	@Test(expected = NullPointerException.class)
	public void testCreateAndSaveNeg2() {

		authenticate("admin");
		Item item = itemService.create();
		item.setName("Item7Name");
		item.setSku("01-AAAA");
		item.setDescription("Item7Description");

		Money price = new Money();
		price.setAmount(0.0);
		price.setCurrency("Euro");
		item.setPrice(price);

		// Obtenemos la Id de la categoria1
		Category category = categoryService.findOne(16);
		item.setCategory(category);

		item.setIsDeleted(false);

		itemService.save(item);

		unauthenticate();
	}

	// Comprobamos que no podemos crear un item siendo un rol distinto a
	// administrador
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNeg3() {

		authenticate("customer1");
		Item item = itemService.create();
		item.setName("Item7Name");
		item.setSku("01-AAAA");
		item.setDescription("Item7Description");

		Money price = new Money();
		price.setAmount(12.5);
		price.setCurrency("Euro");
		item.setPrice(price);

		// Obtenemos la Id de la categoria1
		Category category = categoryService.findOne(10);
		item.setCategory(category);

		item.setIsDeleted(false);

		itemService.save(item);

		unauthenticate();
	}

	// Comprobamos que no podemos crear un item sin categoria
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNeg4() {

		authenticate("admin");
		Item item = itemService.create();
		item.setName("Item7Name");
		item.setSku("01-AAAA");
		item.setDescription("Item7Description");

		Money price = new Money();
		price.setAmount(12.5);
		price.setCurrency("Euro");
		item.setPrice(price);

		item.setIsDeleted(false);

		itemService.save(item);

		unauthenticate();
	}

	// Comprobamos que no podemos crear un item sin un tipo Money
	@Rollback(true)
	@Test(expected = NullPointerException.class)
	public void testCreateAndSaveNeg5() {

		authenticate("admin");
		Item item = itemService.create();
		item.setName("Item7Name");
		item.setSku("01-AAAA");
		item.setDescription("Item7Description");

		// Obtenemos la Id de la categoria1
		Category category = categoryService.findOne(16);
		item.setCategory(category);

		item.setIsDeleted(false);

		itemService.save(item);

		unauthenticate();
	}

	// Comprobamos que no podemos eliminar un item que ya ha sido eliminado
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteNeg1() {
		authenticate("admin");

		// Obtenemos el Id del item1
		Item item = itemService.findOne(14);
		itemService.delete(item);

		itemService.delete(item);

		unauthenticate();

	}

	// Comprobamos que no podemos eliminar un item siendo un rol distinto a
	// administrador
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteNeg2() {
		authenticate("customer1");

		// Obtenemos el Id del item1
		Item item = itemService.findOne(14);
		itemService.delete(item);

		unauthenticate();

	}

}
