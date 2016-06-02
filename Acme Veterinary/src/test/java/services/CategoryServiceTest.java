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
import domain.Category;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class CategoryServiceTest extends AbstractTest {

	// Service under test ------------------------------------

	@Autowired
	private CategoryService categoryService;

	// Tests -------------------------------------------------

	// Comprobamos que podemos crear una categoria de un articulo como
	// administrador

	@Test
	public void testCreateAndSave() {
		Collection<Category> before;
		Collection<Category> after;

		authenticate("admin");

		before = categoryService.findAll();

		Category category;
		category = categoryService.create();
		category.setDescription("Test");
		category.setName("Test");
		categoryService.save(category);

		after = categoryService.findAll();

		Assert.isTrue(before.size() < after.size());

		unauthenticate();
	}

	// Comprobamos que no podemos eliminar una categoria si esta siendo usada en
	// algun articulo

	@Test
	public void testDelete() {
		authenticate("admin");
		Category category;
		
		// Obtenemos el Id de la categoria1
		category = categoryService.findOne(16);
		
		Assert.isTrue(!categoryService.delete(category));

		unauthenticate();
	}

	// Test Negativos ----------------------------------------

	// Comprobamos que no podemos crear una categoria estando logueado como
	// customer u otro rol que no sea admin

	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNegative() {

		authenticate("customer1");

		Category category;
		category = categoryService.create();
		category.setDescription("Test");
		category.setName("Test");
		categoryService.save(category);

		unauthenticate();

	}

}
